package io.octagram.utils;

import io.octagram.domain.enums.Op;
import io.octagram.domain.exceptions.InvalidFieldException;
import io.octagram.domain.form.QueryForm;
import io.octagram.domain.model.QueryField;
import org.springframework.data.mongodb.core.query.Criteria;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
                .map(field -> {
                    String parameterValue = map.get(field);
                    String[] split = parameterValue.split("\\;", 2);
                    int length = split.length;
                    String op = length == 1 ? Op.eq.name() : split[0];
                    String val = length == 1 ? split[0] : split[1];

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
            filters.forEach(f -> {
                String field = f.getField();
                Op op = f.getOp();
                String value = f.getValue();
                switch (op) {
                    case eq: {
                        criteria.and(field).is(value);
                        break;
                    }
                    case like: {
                        criteria.and(field).regex(value);
                        break;
                    }
                    case isNull: {
                        criteria.and(field).isNull();
                        break;
                    }
                }

            });
        }

        return criteria;
    }
}
