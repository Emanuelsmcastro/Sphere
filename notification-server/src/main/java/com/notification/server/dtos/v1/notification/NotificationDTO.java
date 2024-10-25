package com.notification.server.dtos.v1.notification;

import com.notification.server.entities.enums.NotificationType;

public record NotificationDTO(NotificationType type, Object content) {

}
