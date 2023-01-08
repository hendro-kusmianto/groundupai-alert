package org.alert.domain.forms;

import lombok.Data;

@Data

public class AlertUpdateForm {
    private String reason;

    private String action;

    private String comments;

}
