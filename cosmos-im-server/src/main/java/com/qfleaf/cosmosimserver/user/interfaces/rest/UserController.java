package com.qfleaf.cosmosimserver.user.interfaces.rest;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.qfleaf.cosmosimserver.shared.web.ApiResponse;
import com.qfleaf.cosmosimserver.user.application.commands.RegisterByAccountCommand;
import com.qfleaf.cosmosimserver.user.application.queries.UserQueryService;
import com.qfleaf.cosmosimserver.user.application.services.UserAuthAppService;
import com.qfleaf.cosmosimserver.user.infrastructure.dto.UserDetailDTO;
import com.qfleaf.cosmosimserver.user.interfaces.rest.request.RegisterRequest;
import com.qfleaf.cosmosimserver.user.interfaces.rest.response.UserDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Validated
@RequiredArgsConstructor
public class UserController {
    private final UserQueryService queryService;

    @GetMapping("/{userId}")
    @SaCheckLogin
    public ApiResponse<UserDetailResponse> getUser(@PathVariable("userId") Long userId) {
        UserDetailDTO userDetail = queryService.getUserDetail(userId);
        return ApiResponse.success(userDetail.toVO());
    }

    @GetMapping("/me")
    @SaCheckLogin
    public ApiResponse<UserDetailResponse> getCurrentUser() {
        long loginId = StpUtil.getLoginIdAsLong();
        UserDetailDTO userDetail = queryService.getUserDetail(loginId);
        return ApiResponse.success(userDetail.toVO());
    }
}
