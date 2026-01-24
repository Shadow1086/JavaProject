-- Active: 1765086984256@@167.99.162.149@13306@ledger_system
USE ledger_system;

DROP TABLE IF EXISTS users_info;
DROP TABLE IF EXISTS account_details;

create table if not EXISTS user_info(
    user_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    user_name VARCHAR(20) NOT NULL COMMENT '用户名',
    user_password VARCHAR(20) NOT NULL UNIQUE COMMENT '账户密码',
    user_balance DOUBLE DEFAULT 0 COMMENT '用户余额'
);

CREATE TABLE IF NOT EXISTS transaction_records(
    detail_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '账户信息的ID',
    user_id INT COMMENT '用于查询的所属账号ID',
    transaction_type CHAR(10) NOT NULL COMMENT '账号的收支类型',
    amount DECIMAL(10,2) NOT NULL COMMENT '收支金额',
    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间'
)