package com.qfleaf.cosmosimserver.user.domain.repositories;

import com.qfleaf.cosmosimserver.user.domain.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface UserReadRepository {
    @Select("""
            select *
            from users
            where id = #{userId}
            """)
    Optional<UserEntity> findDetailById(@Param("userId") Long userId);

    @Select("""
            select *
            from users
            where username = #{account}
            """)
    Optional<UserEntity> findUserByAccount(@Param("account") String account);
}
