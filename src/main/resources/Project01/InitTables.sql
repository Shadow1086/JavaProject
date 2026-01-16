USE ledger_system;

DROP TABLE IF EXISTS users_info;
DROP TABLE IF EXISTS account_info;

create table if not EXISTS users_info(
    user_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    user_name VARCHAR(20) NOT NULL COMMENT '用户名',
    user_password VARCHAR(20) NOT NULL UNIQUE COMMENT '账户密码',
    user_balance INT NOT NULL COMMENT '用户余额'
)

CREATE TABLE IF NOT EXISTS account_details(
    account_id INT PRIMARY KEY COMMENT '账户信息的ID',
    user_id INT COMMENT '用于查询的所属账号ID',
    user_detail CHAR(6) NOT NULL COMMENT '账号的收支类型',
    user_detail_balance DOUBLE NOT NULL COMMENT '收支金额',
    update_time TIMESTAMP DEFAULT ON CURRENT_TIMESTAMP COMMENT '操作时间'
)