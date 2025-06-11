package com.qfleaf.cosmosimserver.log.persistence;

import com.qfleaf.cosmosimserver.log.domain.OpsLogEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogWriteRepository {
    @Insert("""
            insert into ops_log(ip_address, ops_name, args, result, ops_time)
            values (#{ipAddress}, #{opsName}, #{args}, #{result, typeHandler=com.qfleaf.cosmosimserver.shared.persistence.JsonbTypeHandler}, #{opsTime})
            """)
    int insert(OpsLogEntity logEntity);
}
