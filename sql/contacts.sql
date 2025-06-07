CREATE TABLE contact_groups (
  id BIGSERIAL PRIMARY KEY,
  owner_id BIGINT NOT NULL,
  group_name VARCHAR(255) NOT NULL
);
COMMENT ON TABLE contact_groups IS '联系人分组表';
COMMENT ON COLUMN contact_groups.id IS '分组ID';
COMMENT ON COLUMN contact_groups.owner_id IS '分组所有者用户ID';
COMMENT ON COLUMN contact_groups.group_name IS '分组名称';
CREATE INDEX idx_contact_group_owner_id ON contact_groups (owner_id);

CREATE TABLE contacts (
  id BIGSERIAL PRIMARY KEY,
  owner_id BIGINT NOT NULL,
  contact_id BIGINT NOT NULL,
  relation VARCHAR(50) NOT NULL,
  remark_name VARCHAR(255),
  group_id BIGINT,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE contacts IS '联系人表';
COMMENT ON COLUMN contacts.id IS '联系人记录ID';
COMMENT ON COLUMN contacts.owner_id IS '联系人所有者用户ID';
COMMENT ON COLUMN contacts.contact_id IS '联系人用户ID';
COMMENT ON COLUMN contacts.relation IS '联系人关系类型';
COMMENT ON COLUMN contacts.remark_name IS '备注名称';
COMMENT ON COLUMN contacts.group_id IS '所属分组ID';
COMMENT ON COLUMN contacts.created_at IS '创建时间';
COMMENT ON COLUMN contacts.updated_at IS '更新时间';

CREATE INDEX idx_contact_owner_id ON contacts (owner_id);
CREATE INDEX idx_contact_contact_id ON contacts (contact_id);
CREATE INDEX idx_contact_group_id ON contacts (group_id);

-- 创建触发器
CREATE TRIGGER trigger_contacts_updated_at
    BEFORE UPDATE
    ON contacts
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at();