package com.qfleaf.cosmosimserver.shared.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MyBatis-PostgreSQL jsonb类型处理器
 * 作用：实现Java对象与PostgreSQL jsonb类型的双向转换
 *
 * @param <T> 要处理的Java类型
 */
@MappedTypes({Object.class}) // 声明处理的Java类型
@MappedJdbcTypes(JdbcType.OTHER) // 声明处理的JDBC类型(PostgreSQL的jsonb类型会映射为OTHER)
public class JsonbTypeHandler<T> extends BaseTypeHandler<T> {

    private final Class<T> type;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 构造函数
     *
     * @param type 要转换的Java类型Class对象
     */
    public JsonbTypeHandler(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    /**
     * 设置非空参数
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType)
            throws SQLException {
        PGobject jsonObject = new PGobject();
        jsonObject.setType("jsonb"); // 设置为jsonb类型
        try {
            jsonObject.setValue(objectMapper.writeValueAsString(parameter)); // 转换为JSON字符串
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ps.setObject(i, jsonObject);
    }

    /**
     * 获取非空结果(根据列名)
     */
    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseJsonb(rs.getString(columnName));
    }

    /**
     * 获取非空结果(根据列索引)
     */
    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseJsonb(rs.getString(columnIndex));
    }

    /**
     * 获取非空结果(CallableStatement)
     */
    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseJsonb(cs.getString(columnIndex));
    }

    /**
     * 将jsonb字符串解析为Java对象
     *
     * @param jsonStr jsonb字符串
     * @return 目标Java对象
     */
    private T parseJsonb(String jsonStr) {
        try {
            if (jsonStr == null) {
                return null;
            }

            return objectMapper.readValue(jsonStr, type);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing jsonb string: " + jsonStr, e);
        }
    }
}
