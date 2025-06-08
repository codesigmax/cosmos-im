package com.qfleaf.cosmosimserver.contact.interfaces.rest;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.qfleaf.cosmosimserver.contact.application.commands.ContactAcceptCommand;
import com.qfleaf.cosmosimserver.contact.application.commands.ContactAddCommand;
import com.qfleaf.cosmosimserver.contact.application.services.ContactOpsService;
import com.qfleaf.cosmosimserver.contact.interfaces.rest.request.AcceptContactRequest;
import com.qfleaf.cosmosimserver.contact.interfaces.rest.request.AddContactRequest;
import com.qfleaf.cosmosimserver.shared.web.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
@Validated
@RequiredArgsConstructor
@Tag(name = "联系人接口")
public class ContactController {
    private final ContactOpsService contactOpsService;

    @Operation(summary = "添加联系人")
    @PostMapping("/add")
    @SaCheckLogin
    public ApiResponse<Void> addContact(@RequestBody AddContactRequest request) {
        contactOpsService.addContact(
                new ContactAddCommand(
                        StpUtil.getLoginIdAsLong(),
                        request.getContactId(),
                        request.getGroupId()
                )
        );
        return ApiResponse.success();
    }

    @Operation(summary = "通过添加请求")
    @PostMapping("/acceptAdd")
    @SaCheckLogin
    public ApiResponse<Void> acceptAdd(@RequestBody AcceptContactRequest request) {
        contactOpsService.acceptContact(
                new ContactAcceptCommand(
                        request.getGroupId(),
                        request.getEventId()
                )
        );
        return ApiResponse.success();
    }
}
