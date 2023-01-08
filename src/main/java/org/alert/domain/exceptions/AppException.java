package org.alert.domain.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.alert.domain.model.ErrCode;
import org.alert.domain.model.ErrDetail;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class AppException extends Exception {
    protected ErrCode err;

    protected List<ErrDetail> errors = new ArrayList<>();

    public AppException(String message) {
        super(message);
    }

    public AppException(ErrCode err) {
        super(err.getMessage());
        this.err = err;
    }

    public AppException(ErrCode err, String message) {
        super(message);
        this.err = err;
    }

    public void addError(String field, String errMessage) {
        var err = new ErrDetail(field, ErrCode.ERR_FIELD_INVALID, errMessage);
        this.errors.add(err);
    }

    public boolean hasError() {
        return !errors.isEmpty();
    }

    public void printErrors() {
        errors.forEach(err -> {
            System.err.println("%s -> %s".formatted(err.getField(), err.getErrMessage()));
        });
    }

}
