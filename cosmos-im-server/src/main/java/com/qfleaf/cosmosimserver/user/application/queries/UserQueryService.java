package com.qfleaf.cosmosimserver.user.application.queries;

import com.qfleaf.cosmosimserver.user.domain.exceptions.UserNotFoundException;
import com.qfleaf.cosmosimserver.user.domain.repositories.UserReadRepository;
import com.qfleaf.cosmosimserver.user.infrastructure.cache.UserCacheRepository;
import com.qfleaf.cosmosimserver.user.infrastructure.dto.UserDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService {
    private final UserReadRepository readRepo; // 单独优化的查询仓储
    private final UserCacheRepository cacheRepo;

    public UserDetailDTO getUserDetail(Long userId) {
        // 缓存-数据库两级查询
        return cacheRepo.findById(userId)
                .orElseGet(() -> {
                    UserDetailDTO detail = readRepo.findDetailById(userId)
                            .map(UserDetailDTO::new)
                            .orElseThrow(UserNotFoundException::new);
                    cacheRepo.cacheUser(detail);
                    return detail;
                });
    }
}
