package io.octagram.domain.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Builder
@Setter
@Getter
@Accessors(chain = true)
public class ResponseMeta {
    private String code;
    private String message;
}
