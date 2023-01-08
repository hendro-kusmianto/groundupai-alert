package org.alert.domain.exceptions;

import org.alert.domain.model.ErrCode;
import org.alert.domain.model.ErrDetail;

public class InvalidFieldException extends AppException {

    public InvalidFieldException() {
        this("Field tidak valid");
    }

    public InvalidFieldException(String message) {
        super(ErrCode.ERR_FIELD_INVALID, message);
    }

    public InvalidFieldException(String field, String message) {
        super(ErrCode.ERR_FIELD_INVALID);
        ErrDetail errDetail = new ErrDetail(field, ErrCode.ERR_FIELD_INVALID, message);
        this.errors.add(errDetail);
    }


}
