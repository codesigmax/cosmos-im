package com.qfleaf.cosmosimserver.chat.domain.repositories;

import com.qfleaf.cosmosimserver.chat.domain.entites.ChatMessageEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface ChatMessageWriteRepository {
    @Insert("""
            insert into chat_messages(sender_id, receiver_id, content)
            values (#{senderId}, #{receiverId}, #{content})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(ChatMessageEntity chatMessageEntity);
}
