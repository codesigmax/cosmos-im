package com.qfleaf.cosmosimserver.user.interfaces.rest;

import cn.dev33.satoken.stp.SaTokenInfo;
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
}
