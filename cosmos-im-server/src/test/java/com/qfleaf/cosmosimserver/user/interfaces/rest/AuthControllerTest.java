package com.qfleaf.cosmosimserver.user.interfaces.rest;

import com.qfleaf.cosmosimserver.user.application.commands.RegisterByAccountCommand;
import com.qfleaf.cosmosimserver.user.application.services.UserAuthAppService;
import com.qfleaf.cosmosimserver.user.interfaces.rest.request.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs // 启用REST Docs
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // 用于JSON序列化

    @MockBean
    private UserAuthAppService authService; // 模拟Service

    @Test
    public void testRegisterSuccess() throws Exception {
        // 1. 准备测试数据
        RegisterRequest request = RegisterRequest.builder()
                .username("testuser")
                .password("P@ssw0rd")
                .email("test@example.com")
                .nickname("Test User")
                .avatar("https://example.com/avatar.jpg")
                .build();

        // 2. 模拟Service行为（假设register方法无返回值）
        doNothing().when(authService).registerByAccount(any(RegisterByAccountCommand.class));

        // 3. 发起请求并验证
        mockMvc.perform(post("/auths/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()) // 验证HTTP 200
                .andDo(document("auth-register", // 文档目录名
                        requestFields( // 描述请求字段
                                fieldWithPath("username").type(JsonFieldType.STRING)
                                        .description("用户名，需唯一").optional(),
                                fieldWithPath("password").type(JsonFieldType.STRING)
                                        .description("密码"),
                                fieldWithPath("email").type(JsonFieldType.STRING)
                                        .description("邮箱地址").optional(),
                                fieldWithPath("nickname").type(JsonFieldType.STRING)
                                        .description("昵称").optional(),
                                fieldWithPath("avatar").type(JsonFieldType.STRING)
                                        .description("头像URL").optional()
                        ),
                        responseFields( // 描述响应字段
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("响应状态码"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("响应消息"),
                                fieldWithPath("data").type(JsonFieldType.NULL)
                                        .description("响应数据（注册成功无返回）")
                        )));
    }
}
