package io.octagram.domain.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PatternNotMatchedException extends AppException {

    public PatternNotMatchedException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Pola tidak cocok";
    }
}
