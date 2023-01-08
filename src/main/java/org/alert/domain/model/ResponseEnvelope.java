package io.octagram.domain.dto;

import io.octagram.domain.model.ErrCode;
import io.octagram.domain.model.ErrDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ResponseEnvelope {
    private ResponseMeta meta;
    private Map<String, Object> data = new HashMap<>();

    private List<ErrDetail> errors = new ArrayList<>();

    public ResponseEnvelope() {
        var errSuccess = ErrCode.SUCCESS;
        this.meta = new ResponseMeta(errSuccess.getCode(), errSuccess.getMessage());
    }

    public ResponseEnvelope(ErrCode errCode, String errMessage) {
        this.meta = new ResponseMeta(errCode.getCode(), errMessage);
    }

    public ResponseEnvelope(ErrCode errCode) {
        this.meta = new ResponseMeta(errCode.getCode(), errCode.getMessage());
    }

    public void setErrCode(ErrCode err) {
        this.meta.setCode(err.getCode());
        this.meta.setMessage(err.getMessage());
    }

    public void put(String key, Object value) {
        data.put(key, value);

    }
}
