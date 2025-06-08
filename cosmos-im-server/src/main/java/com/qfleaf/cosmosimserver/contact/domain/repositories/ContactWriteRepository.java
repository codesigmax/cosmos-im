package com.qfleaf.cosmosimserver.contact.domain.repositories;

import com.qfleaf.cosmosimserver.contact.domain.entities.ContactEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContactWriteRepository {
    @Insert("""
            insert into contacts(owner_id, contact_id, relation, remark_name, group_id)
            values (#{ownerId}, #{contactId}, #{relation}, #{remarkName}, #{groupId})
            """)
    int insert(ContactEntity entity);
}
