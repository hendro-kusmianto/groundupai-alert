package io.octagram.domain.exceptions;

import io.octagram.domain.model.ErrCode;
import io.octagram.domain.model.ErrDetail;

public class ForbiddenOperationException extends AppException {

    public ForbiddenOperationException() {
        this("Operasi tidak diijinkan");
    }

    public ForbiddenOperationException(String message) {
        super(ErrCode.ERR_FORBIDDEN, message);
    }

    public ForbiddenOperationException(String field, String message) {
        super(ErrCode.ERR_FORBIDDEN, message);
        this.errors.add(new ErrDetail(field, ErrCode.ERR_FORBIDDEN));
    }

}
