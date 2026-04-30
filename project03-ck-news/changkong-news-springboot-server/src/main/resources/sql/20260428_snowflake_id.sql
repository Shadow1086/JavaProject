-- 将 news_user、news_headline 中实体类为 Long 的 ID 字段切换为 MyBatis-Plus 雪花 ID 使用的 BIGINT。
-- tid/type 在实体类中仍是 Integer，保持 INT，不参与雪花 ID 迁移。
-- 执行前请先备份数据库；本文件只提供迁移语句，不会自动执行。

UPDATE news_headline
SET is_deleted = 0
WHERE is_deleted IS NULL;

ALTER TABLE news_user
    MODIFY uid BIGINT NOT NULL COMMENT '用户id';

ALTER TABLE news_headline
    MODIFY hid BIGINT NOT NULL COMMENT '头条id',
    MODIFY publisher BIGINT NOT NULL COMMENT '头条发布用户id',
    MODIFY is_deleted INT NOT NULL DEFAULT 0 COMMENT '头条是否被删除 1 删除 0 未删除';

-- 如果库里还有旧的 INT 自增演示数据，可执行下面三段迁移为长整型 ID，并保持用户和文章的关联关系。
-- 已经是雪花 ID 的数据不会被修改。
UPDATE news_headline
SET publisher = 2049366413163573000 + publisher
WHERE publisher > 0
  AND publisher < 1000000000000000000;

UPDATE news_user
SET uid = 2049366413163573000 + uid
WHERE uid > 0
  AND uid < 1000000000000000000;

UPDATE news_headline
SET hid = 2049366500000000000 + hid
WHERE hid > 0
  AND hid < 1000000000000000000;
