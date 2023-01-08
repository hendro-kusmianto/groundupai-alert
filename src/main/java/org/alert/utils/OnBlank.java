package org.alert.utils;

public class OnBlank {
    String str;

    private OnBlank(String str) {
        this.str = str;
    }

    public static OnBlank of(String str) {
        return new OnBlank(str);
    }

    public boolean isBlank() {
        return str == null || str.isBlank();
    }

    public String Null() {
        return isBlank() ? null : str;
    }

    public <T> T Return(T data) {
        return isBlank() ? null : data;
    }
}
