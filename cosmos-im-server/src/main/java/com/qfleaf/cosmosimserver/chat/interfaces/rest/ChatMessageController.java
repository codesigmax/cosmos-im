package com.qfleaf.cosmosimserver.chat.interfaces.rest;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.qfleaf.cosmosimserver.chat.application.commands.SendMessageCommand;
import com.qfleaf.cosmosimserver.chat.application.queries.ChatMessageQueryService;
import com.qfleaf.cosmosimserver.chat.application.services.ChatOpsService;
import com.qfleaf.cosmosimserver.chat.interfaces.rest.request.SendMessageRequest;
import com.qfleaf.cosmosimserver.chat.interfaces.rest.response.MessageQueryResponse;
import com.qfleaf.cosmosimserver.shared.web.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatMessages")
@Validated
@RequiredArgsConstructor
@Tag(name = "聊天消息接口")
public class ChatMessageController {
    private final ChatOpsService chatOpsService;
    private final ChatMessageQueryService chatQueryService;

    @Operation(summary = "同步远端消息")
    @GetMapping("/sync")
    @SaCheckLogin
    public ApiResponse<List<MessageQueryResponse>> sync() {
        List<MessageQueryResponse> messages = chatQueryService.getAll(StpUtil.getLoginIdAsLong());
        return ApiResponse.success(messages);
    }

    @Operation(summary = "发送消息")
    @PostMapping("/send")
    @SaCheckLogin
    public void send(@RequestBody SendMessageRequest request) {
        chatOpsService.sendMessage(
                new SendMessageCommand(
                        StpUtil.getLoginIdAsLong(),
                        request.getReceiverId(),
                        request.getContent()
                )
        );
    }
}
