package io.octagram.domain.model;

import io.octagram.domain.enums.Op;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class QueryField {
    String field;
    Op op;
    String value;
}
