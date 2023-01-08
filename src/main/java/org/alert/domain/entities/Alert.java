package org.alert.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.alert.domain.constants.AppConstant;
import org.alert.domain.dto.AlertDto;
import org.alert.utils.DateUtils;
import org.alert.utils.Utils;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Document(collation = AppConstant.app_collation)
@AllArgsConstructor
@Getter
@Setter
@Accessors(fluent = true)
@ToString(callSuper = true)
public class Alert extends BaseEntity{

    @Indexed(unique = true)
    private String errId;

    @Indexed
    private String machine;
    private String sensor;

    private String anomaly;
    private String reason;
    private String action;

    private String comments;

    private String clip;

    @Indexed
    private Long timestamp;

    public Alert(){
        super();
        this.errId = Utils.randomNumeric(8);
    }

    @Override
    public AlertDto toDto() {
        return new AlertDto(id, errId, machine, anomaly, reason, action, comments, sensor, timestamp, clip);
    }

    @Override
    public Alert created() {
        return ((Alert) super.created());
    }
}
