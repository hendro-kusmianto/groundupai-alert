package io.octagram.domain.exceptions;

import io.octagram.domain.model.ErrCode;
import io.octagram.domain.model.ErrDetail;

public class NonExistentEntityException extends AppException {

    public NonExistentEntityException() {
        this("Data tidak tersedia");
    }

    public NonExistentEntityException(String message) {
        super(ErrCode.ERR_NOT_FOUND, message);
    }

    public NonExistentEntityException(String field, String message) {
        super(ErrCode.ERR_NOT_FOUND, message);
        this.errors.add(new ErrDetail(field, ErrCode.ERR_NOT_FOUND));
    }

}
