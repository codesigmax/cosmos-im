-- 创建自动更新触发器函数
CREATE OR REPLACE FUNCTION update_updated_at()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 创建用户表
CREATE TABLE users
(
    id         BIGINT PRIMARY KEY,

    username   VARCHAR(64)  NOT NULL,
    password   VARCHAR(128) NOT NULL,
    email      VARCHAR(128),

    nickname   VARCHAR(64),
    avatar     VARCHAR(255),
    status     SMALLINT     NOT NULL DEFAULT 1,
    created_at TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_users_username UNIQUE (username),
    CONSTRAINT uk_users_email UNIQUE (email)
);

-- 添加表注释（PostgreSQL特有语法）
COMMENT ON TABLE users IS '用户基础信息表，ID使用雪花算法生成';

-- 添加字段注释
COMMENT ON COLUMN users.id IS '用户ID，使用雪花算法生成';
COMMENT ON COLUMN users.username IS '用户名，用于登录';
COMMENT ON COLUMN users.password IS '密码，加密存储';
COMMENT ON COLUMN users.email IS '电子邮箱';
COMMENT ON COLUMN users.nickname IS '用户昵称/显示名称';
COMMENT ON COLUMN users.avatar IS '头像URL';
COMMENT ON COLUMN users.status IS '账户状态：0-禁用,1-正常,2-锁定等（对应AccountStatus枚举）';
COMMENT ON COLUMN users.created_at IS '创建时间';
COMMENT ON COLUMN users.updated_at IS '更新时间';

-- 创建索引
CREATE INDEX idx_users_nickname ON users (nickname);
CREATE INDEX idx_users_status ON users (status);

-- 创建触发器
CREATE TRIGGER trigger_users_updated_at
    BEFORE UPDATE
    ON users
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at();

