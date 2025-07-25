package com.qfleaf.cosmosimserver.user.interfaces.rest;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.qfleaf.cosmosimserver.log.annotations.Log;
import com.qfleaf.cosmosimserver.shared.web.ApiResponse;
import com.qfleaf.cosmosimserver.user.application.commands.LoginByAccountCommand;
import com.qfleaf.cosmosimserver.user.application.commands.RegisterByAccountCommand;
import com.qfleaf.cosmosimserver.user.application.services.UserAuthService;
import com.qfleaf.cosmosimserver.user.interfaces.rest.request.LoginRequest;
import com.qfleaf.cosmosimserver.user.interfaces.rest.request.RegisterRequest;
import com.qfleaf.cosmosimserver.user.interfaces.rest.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auths")
@Validated
@RequiredArgsConstructor
@Tag(name = "认证接口")
public class AuthController {
    private final UserAuthService authService;

    @Log(opsName = "用户注册")
    @Operation(summary = "注册")
    @PostMapping("/register")
    public ApiResponse<Void> register(@RequestBody RegisterRequest request) {
        authService.registerByAccount(
                new RegisterByAccountCommand(
                        request.getUsername(),
                        request.getPassword(),
                        request.getEmail(),
                        request.getNickname(),
                        request.getAvatar()
                )
        );
        return ApiResponse.success();
    }

    @Log(opsName = "用户登陆")
    @Operation(summary = "登陆")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        SaTokenInfo saTokenInfo = authService.loginByAccount(
                new LoginByAccountCommand(
                        request.getAccount(),
                        request.getPassword()
                )
        );
        return ApiResponse.success(
                LoginResponse.builder()
                        .tokenName(saTokenInfo.getTokenName())
                        .tokenValue(saTokenInfo.getTokenValue())
                        .build()
        );
    }

    @Log(opsName = "用户登出")
    @Operation(summary = "登出")
    @SaCheckLogin
    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        StpUtil.logout();
        return ApiResponse.success();
    }
}
