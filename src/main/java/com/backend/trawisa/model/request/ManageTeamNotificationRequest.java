package com.backend.trawisa.model.request;

import lombok.Data;

@Data
public class ManageTeamNotificationRequest {
    private Boolean isNotificationEnable;
    private Integer teamId;
}
