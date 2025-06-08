package com.qfleaf.cosmosimserver.contact.interfaces.rest;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.qfleaf.cosmosimserver.contact.application.queries.ContactGroupQueryService;
import com.qfleaf.cosmosimserver.contact.domain.entities.ContactGroup;
import com.qfleaf.cosmosimserver.shared.web.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@Validated
@RequiredArgsConstructor
@Tag(name = "联系人分组管理接口")
public class ContactGroupController {
    private final ContactGroupQueryService contactGroupQueryService;

    @Operation(summary = "获取分组列表")
    @GetMapping("/list")
    @SaCheckLogin
    public ApiResponse<List<ContactGroup>> getAllContactGroups() {
        long currentUserId = StpUtil.getLoginIdAsLong();
        List<ContactGroup> groups = contactGroupQueryService.getAllContactGroups(currentUserId);
        return ApiResponse.success(groups);
    }
}
