package com.qfleaf.cosmosimserver.contact.domain.repositories;

import com.qfleaf.cosmosimserver.contact.domain.entities.ContactGroup;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContactGroupRepository {
    @Insert("""
            insert into contact_groups(owner_id, group_name)
            values (#{ownerId}, #{groupName})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(ContactGroup contactGroup);

    @Select("""
            select *
            from contact_groups
            where owner_id = #{userId}
            """)
    List<ContactGroup> selectAllByOwnerId(@Param("userId") Long userId);
}
