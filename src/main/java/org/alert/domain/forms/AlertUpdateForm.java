package org.alert.domain.forms;

import lombok.Data;
import org.alert.domain.enums.Anomaly;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data

public class AlertForm {
    @NotBlank(message = "Machine name can't be empty")
    private String machine;

    @Pattern(regexp = "mild|moderate|severe", message = "Invalid anomaly")
    @NotBlank(message = "Anomaly can't be empty")
    private String anomaly;

    @NotBlank(message = "Sensor name can't be empty")
    private String sensor;

    @NotBlank(message = "Sound clip name can't be empty")
    private String clip;

    @NotNull(message = "Timestamp name can't be empty")
    @Positive(message = "Timestamp must be positive number")
    private Long timestamp;

    public Anomaly getStatus() {
        return Anomaly.valueOf(anomaly);
    }

}
