package org.alert.domain.constants;

import java.util.Locale;

public interface AppConstant {
    Locale locale = Locale.ENGLISH;

    String default_date_pattern = "yyyy-MM-dd";

    String default_date_time_pattern = "yyyy-MM-dd kk:mm:ss";
    String app_collation = "{locale:'en', strength:2}";
}
