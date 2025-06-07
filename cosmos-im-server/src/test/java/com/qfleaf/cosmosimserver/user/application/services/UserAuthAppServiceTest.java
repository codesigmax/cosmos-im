package com.qfleaf.cosmosimserver.user.application.services;

import com.qfleaf.cosmosimserver.user.application.commands.RegisterByAccountCommand;
import com.qfleaf.cosmosimserver.user.domain.services.UserDomainService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserAuthAppServiceTest {

    @Mock
    private UserDomainService userDomainService;

    @InjectMocks
    private UserAuthAppService userAuthAppService;

    @Test
    void registerByAccount() {
        RegisterByAccountCommand command = new RegisterByAccountCommand(
                "test",
                "123456",
                "test@qfleaf.com",
                "测试",
                null
        );
        userAuthAppService.registerByAccount(command);
    }
}