package org.alert.domain.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class QueryForm {
    String keyword;
    @Builder.Default
    int page = 1;
    @Builder.Default
    int maxRow = 10;

    String sort;

    @Builder.Default
    Sort.Direction sortOrder = Sort.Direction.ASC;

    @Builder.Default
    List<QueryField> filters = new ArrayList<>();

    public static QueryForm of(Map<String, String> map) {
        QueryFormBuilder builder = QueryForm.builder()
                .keyword(map.get("keyword"))
                .page(MapUtils.getIntValue(map, "page", 1))
                .maxRow(MapUtils.getIntValue(map, "max_row", 10));

        String sort = map.get("sort");
        if (sort != null && !sort.isBlank()) {
            String[] split = sort.split("\\;");
            int length = split.length;
            String sortField = split[0];
            Sort.Direction sortOrder = length == 1 ? Sort.Direction.ASC : Sort.Direction.valueOf(split[1].toUpperCase());
            builder.sort(sortField).sortOrder(sortOrder);
        }

        return builder.build();
    }
}
