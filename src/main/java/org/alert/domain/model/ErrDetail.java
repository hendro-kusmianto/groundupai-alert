package io.octagram.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ErrDetail {
    private String field;
    private String errCode;
    private String errMessage;


    public ErrDetail(String field, ErrCode errCode) {
        this.field = field;
        this.errCode = errCode.getCode();
        this.errMessage = errCode.getMessage();
    }

    public ErrDetail(String field, ErrCode errCode, String errMessage) {
        this.field = field;
        this.errCode = errCode.getCode();
        this.errMessage = errMessage;
    }

}
