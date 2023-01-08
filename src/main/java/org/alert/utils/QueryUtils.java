package org.alert.utils;

import org.alert.domain.enums.Op;
import org.alert.domain.exceptions.InvalidFieldException;
import org.alert.domain.forms.QueryField;
import org.alert.domain.forms.QueryForm;
import org.springframework.data.mongodb.core.query.Criteria;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryUtils {
    static Map<String, String> skipped = new HashMap();

    static {
        skipped.put("page", "");
        skipped.put("max_row", "");
        skipped.put("sort", "");
    }

    public static Mono<QueryForm> toQueryFields(Map<String, String> map) {
        var exceptions = new InvalidFieldException();

        return Flux.fromIterable(map.keySet())
                .filter(key -> !skipped.containsKey(key))
                .filter(field -> map.get(field) != null && !map.get(field).isBlank())
                .map(parameterName -> {
                    String parameterValue = map.get(parameterName);
                    String[] split = parameterValue.split("\\;", 3);
                    int length = split.length;

                    String field = switch (length){
                        case 3 -> split[0];
                        default -> parameterName;
                    };
                    String op = switch (length){
                        case 2 -> split[0];
                        case 3 -> split[1];
                        default -> Op.eq.name();
                    };

                    String val = split[length - 1];

                    return new String[]{field, op, val};
                })
                .filter(arr -> !arr[2].isBlank())
                .map(arr -> {
                    String field = arr[0];
                    try {
                        String op = arr[1];
                        String val = arr[2];
                        return new QueryField(field, Op.valueOf(op), val);
                    } catch (IllegalArgumentException e) {
                        exceptions.addError(field, "Operator tidak valid");
                        throw Exceptions.propagate(exceptions);
                    }
                })
                .collectList()
                .map(filters -> {
                    return QueryForm.of(map).toBuilder()
                            .filters(filters)
                            .build();
                });

    }


    public static Criteria toCriteria(List<QueryField> filters) {
        Criteria criteria = new Criteria();
        if (filters != null && !filters.isEmpty()) {
            List<Criteria> allCriteria = new ArrayList<>();
            filters.forEach(f -> {
                String field = f.getField();
                Op op = f.getOp();
                Object value = f.getValue();
                switch (op) {
                    case eq: {
                        Criteria eq = new Criteria(field).is(value);
                        allCriteria.add(eq);
                        break;
                    }
                    case like: {
                        Criteria like = new Criteria(field).regex(value.toString());
                        allCriteria.add(like);
                        break;
                    }
                    case isNull: {
                        Criteria isNull = new Criteria(field).isNull();
                        allCriteria.add(isNull);
                        break;
                    }
                    case gt: {
                        Double n = Double.parseDouble(value+"");
                        Criteria gt = new Criteria(field).gt(n);
                        allCriteria.add(gt);
                        break;
                    }
                    case gte: {
                        Double n = Double.parseDouble(value+"");
                        Criteria gte = new Criteria(field).gte(n);
                        allCriteria.add(gte);
                        break;
                    }
                    case lt: {
                        Double n = Double.parseDouble(value+"");
                        Criteria lt = new Criteria(field).lt(n);
                        allCriteria.add(lt);
                        break;
                    }
                    case lte: {
                        Double n = Double.parseDouble(value+"");
                        Criteria lte = new Criteria(field).lte(n);
                        allCriteria.add(lte);
                        break;
                    }
                }

            });

            criteria.andOperator(allCriteria);
        }

        return criteria;
    }
}
