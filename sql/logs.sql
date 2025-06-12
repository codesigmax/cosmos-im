-- 创建操作日志表
CREATE TABLE ops_log (
  id BIGSERIAL PRIMARY KEY,
  ip_address VARCHAR(50) NOT NULL,
  ops_name VARCHAR(100) NOT NULL,
  args TEXT,
  result JSONB,
  ops_time TIMESTAMP WITH TIME ZONE NOT NULL
);

-- 添加表和字段注释
COMMENT ON TABLE ops_log IS '系统操作日志表';
COMMENT ON COLUMN ops_log.id IS '主键ID';
COMMENT ON COLUMN ops_log.ip_address IS '操作来源IP地址';
COMMENT ON COLUMN ops_log.ops_name IS '操作名称/类型';
COMMENT ON COLUMN ops_log.args IS '操作参数';
COMMENT ON COLUMN ops_log.result IS '操作结果(JSON格式)';
COMMENT ON COLUMN ops_log.ops_time IS '操作发生时间';