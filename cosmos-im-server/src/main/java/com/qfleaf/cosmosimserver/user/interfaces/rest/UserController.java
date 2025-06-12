package com.qfleaf.cosmosimserver.user.interfaces.rest;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.qfleaf.cosmosimserver.log.annotations.Log;
import com.qfleaf.cosmosimserver.shared.web.ApiResponse;
import com.qfleaf.cosmosimserver.user.application.commands.ChangePasswordCommand;
import com.qfleaf.cosmosimserver.user.application.commands.ModifyProfileCommand;
import com.qfleaf.cosmosimserver.user.application.queries.UserQueryService;
import com.qfleaf.cosmosimserver.user.application.services.UserOpsService;
import com.qfleaf.cosmosimserver.user.infrastructure.dto.UserDetailDTO;
import com.qfleaf.cosmosimserver.user.interfaces.rest.request.EditProfileRequest;
import com.qfleaf.cosmosimserver.user.interfaces.rest.request.PasswordChangeRequest;
import com.qfleaf.cosmosimserver.user.interfaces.rest.response.UserDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@Validated
@RequiredArgsConstructor
@Tag(name = "用户接口")
public class UserController {
    private final UserQueryService queryService;
    private final UserOpsService opsService;

    @Operation(summary = "通过ID获取用户信息")
    @GetMapping("/{userId}")
    @SaCheckLogin
    public ApiResponse<UserDetailResponse> getUser(@PathVariable("userId") Long userId) {
        UserDetailDTO userDetail = queryService.getUserDetail(userId);
        return ApiResponse.success(userDetail.toVO());
    }

    @Operation(summary = "获取当前用户个人信息")
    @GetMapping("/me")
    @SaCheckLogin
    public ApiResponse<UserDetailResponse> getCurrentUser() {
        long loginId = StpUtil.getLoginIdAsLong();
        UserDetailDTO userDetail = queryService.getUserDetail(loginId);
        return ApiResponse.success(userDetail.toVO());
    }

    @Log(opsName = "用户修改密码")
    @Operation(summary = "修改密码")
    @PostMapping("/changePassword")
    public ApiResponse<Void> changePassword(@RequestBody PasswordChangeRequest request) {
        opsService.changePassword(
                new ChangePasswordCommand(request.getOriginalPassword(), request.getNewPassword())
        );
        return ApiResponse.success();
    }

    // 头像上传
    @Operation(summary = "上传自定义头像")
    @PostMapping("/avatar")
    @SaCheckLogin
    public ApiResponse<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        String url = opsService.uploadAvatar(file);
        return ApiResponse.success(url);
    }

    // 修改个人信息
    @Operation(summary = "修改个人信息")
    @PostMapping("/profile")
    @SaCheckLogin
    public ApiResponse<Void> modifyProfile(@RequestBody EditProfileRequest request) {
        opsService.modifyProfile(
                new ModifyProfileCommand(
                        request.getAvatar(),
                        request.getNickname()
                )
        );
        return ApiResponse.success();
    }
}
