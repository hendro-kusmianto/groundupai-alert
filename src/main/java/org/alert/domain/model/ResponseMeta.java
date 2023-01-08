package org.alert.domain.model;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
public class ResponseMeta {
    private String code;
    private String message;
}
