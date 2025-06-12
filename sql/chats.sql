-- Active: 1749311208688@@127.0.0.1@5432@cosmos_im
CREATE TABLE IF NOT EXISTS chat_messages (
  id BIGSERIAL PRIMARY KEY,
  sender_id BIGINT NOT NULL,
  receiver_id BIGINT NOT NULL,
  content TEXT NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  
  -- Adding foreign key constraints assuming users table exists
  CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES users(id),
  CONSTRAINT fk_receiver FOREIGN KEY (receiver_id) REFERENCES users(id)
);

-- Create index for better query performance
CREATE INDEX idx_chat_messages_sender ON chat_messages(sender_id);
CREATE INDEX idx_chat_messages_receiver ON chat_messages(receiver_id);
CREATE INDEX idx_chat_messages_created_at ON chat_messages(created_at);

-- Add table and column comments
COMMENT ON TABLE chat_messages IS '聊天消息表';
COMMENT ON COLUMN chat_messages.id IS '消息ID';
COMMENT ON COLUMN chat_messages.sender_id IS '发送者ID';
COMMENT ON COLUMN chat_messages.receiver_id IS '接收者ID';
COMMENT ON COLUMN chat_messages.content IS '消息内容';
COMMENT ON COLUMN chat_messages.created_at IS '创建时间';