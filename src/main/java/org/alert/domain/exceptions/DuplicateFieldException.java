package org.alert.domain.exceptions;

import org.alert.domain.model.ErrCode;
import org.alert.domain.model.ErrDetail;

public class DuplicateFieldException extends AppException {

    public DuplicateFieldException() {
        this("Field sudah terdaftar");
    }

    public DuplicateFieldException(String message) {
        super(ErrCode.ERR_FIELD_CONFLICT, message);
    }

    public DuplicateFieldException(String field, String message) {
        super(ErrCode.ERR_FIELD_CONFLICT, message);
        this.errors.add(new ErrDetail(field, ErrCode.ERR_FIELD_CONFLICT));
    }

}
