package com.qfleaf.cosmosimserver.chat.domain.repositories;

import com.qfleaf.cosmosimserver.chat.interfaces.rest.response.MessageQueryResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChatMessageReadRepository {
    @Select("""
            select *
            from chat_messages
            where receiver_id = #{userId} or sender_id = #{userId}
            order by created_at
            """)
    List<MessageQueryResponse> getAll(@Param("userId") long loginIdAsLong);
}
