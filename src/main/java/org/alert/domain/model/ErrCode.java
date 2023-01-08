package io.octagram.domain.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum ErrCode {
    SERVER_ERROR("500", "Server Error"),
    SUCCESS("2000", "Sukses"),
    ERR_UNKNOWN("2001", "Unknown error"),
    ERR_FORBIDDEN("2002", "Operasi tidak diijinkan"),
    ERR_NOT_FOUND("2003", "Resource not found"),
    ERR_BAD_REQUEST("2004", "Bad Request"),
    ERR_DATABASE("2010", "Operasi database gagal"),
    ERR_DATABASE_RECORD_LOCKING("2011", "Database locking failed"),

    ERR_TOKEN_INVALID("3000", "Token invalid"),
    ERR_TOKEN_EXPIRED("3001", "Token expired"),

    ERR_FIELD_EMPTY("4000", "Field kosong"),
    ERR_FIELD_CONTAIN_WHITESPACE("4001", "Field mengandung whitespace"),
    ERR_FIELD_CONFLICT("4002", "Field sudah terdaftar"),
    ERR_FIELD_INVALID("4003", "Field tidak valid"),
    ERR_FIELD_NOT_EXIST("4004", "Field tidak terdaftar"),

    ERR_BAD_CREDENTIAL("4010", "Bad Credentials"),
    ERR_USER_SUSPENDED("4011", "User suspended"),
    ERR_USER_LOCKED("4012", "User locked"),
    ERR_USER_EXPIRED("4013", "User expired"),
    ERR_USER_DISABLED("4014", "User disabled"),
    ERR_USER_NOT_EXIST("4015", "User tidak terdaftar"),
    ERR_USER_NOT_VERIFIED("4016", "User belum terverifikasi"),

    ERR_SERVICE_CLOSED("4021", "Service closed");

    @NonNull
    private String code;
    @NonNull
    private String message;

    public static ErrCode toError(String errCode) {
        ErrCode errCodeMapping = ERR_UNKNOWN;
        ErrCode[] values = ErrCode.values();
        for (ErrCode err : values) {
            if (err.getCode().equals(errCode)) {
                errCodeMapping = err;
                break;
            }
        }

        return errCodeMapping;
    }
}
