package com.highgeupsik.backend.api.controller.notification;

import com.highgeupsik.backend.api.controller.ApiResult;
import com.highgeupsik.backend.api.controller.ApiUtils;
import com.highgeupsik.backend.api.resolver.LoginUser;
import com.highgeupsik.backend.api.service.notification.NotificationQueryService;
import com.highgeupsik.backend.api.service.notification.NotificationQueryService.NotificationResult;
import com.highgeupsik.backend.api.service.notification.NotificationService;
import com.highgeupsik.backend.api.utils.PagingUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationQueryService notificationQueryService;

    @GetMapping("/notifications")
    public ApiResult<NotificationResult> notifications(@LoginUser Long userId,
        @RequestParam(value = "page", defaultValue = PagingUtils.DEFAULT_PAGE_NUMBER) Integer pageNum) {
        return ApiUtils.success(notificationQueryService.findAllByUserId(userId, pageNum));
    }

    @PutMapping("/notifications/{notificationId}")
    public void readNotification(@PathVariable Long notificationId) {
        notificationService.readNotification(notificationId);
    }
}
