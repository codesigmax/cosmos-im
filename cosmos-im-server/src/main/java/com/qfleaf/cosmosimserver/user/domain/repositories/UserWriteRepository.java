package com.qfleaf.cosmosimserver.user.domain.repositories;

import com.qfleaf.cosmosimserver.user.domain.entities.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserWriteRepository {
    @Insert("""
            insert into users
            values (#{id}, #{username}, #{password}, #{email}, #{nickname}, #{avatar}, #{status})
            """)
    int insert(UserEntity user);

    @Update("""
            update users
            set username = #{username},
            password = #{password},
            email = #{email},
            nickname = #{nickname},
            avatar = #{avatar},
            status = #{status}
            where id = #{id}
            """)
    int update(UserEntity user);
}
