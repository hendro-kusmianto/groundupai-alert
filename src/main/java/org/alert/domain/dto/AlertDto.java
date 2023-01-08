package org.alert.domain.dto;

public record AlertDto(String id, String errId, String machine, String anomaly, String reason, String action, String comments
        , String sensor, Long timestamp, String clip) {
}
