package com.qfleaf.cosmosimserver.contact.interfaces.rest;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.qfleaf.cosmosimserver.contact.application.commands.ContactGroupCreateCommand;
import com.qfleaf.cosmosimserver.contact.application.queries.ContactGroupQueryService;
import com.qfleaf.cosmosimserver.contact.application.services.ContactGroupOpsService;
import com.qfleaf.cosmosimserver.contact.domain.entities.ContactGroup;
import com.qfleaf.cosmosimserver.contact.interfaces.rest.request.CreateContactGroupRequest;
import com.qfleaf.cosmosimserver.shared.web.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contactGroups")
@Validated
@RequiredArgsConstructor
@Tag(name = "联系人分组管理接口")
public class ContactGroupController {
    private final ContactGroupQueryService contactGroupQueryService;
    private final ContactGroupOpsService contactGroupOpsService;

    @Operation(summary = "获取分组列表")
    @GetMapping("/list")
    @SaCheckLogin
    public ApiResponse<List<ContactGroup>> getAllContactGroups() {
        long currentUserId = StpUtil.getLoginIdAsLong();
        List<ContactGroup> groups = contactGroupQueryService.getAllContactGroups(currentUserId);
        return ApiResponse.success(groups);
    }

    @Operation(summary = "新增分组")
    @PostMapping("/newGroup")
    @SaCheckLogin
    public ApiResponse<Void> createContactGroup(@Validated @RequestBody CreateContactGroupRequest request) {
        contactGroupOpsService.create(
                new ContactGroupCreateCommand(
                        StpUtil.getLoginIdAsLong(),
                        request.getGroupName()
                )
        );
        return ApiResponse.success();
    }
}
