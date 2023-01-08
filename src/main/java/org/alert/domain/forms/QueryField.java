package org.alert.domain.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.alert.domain.enums.Op;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class QueryField {
    String field;
    Op op;
    Object value;


}
