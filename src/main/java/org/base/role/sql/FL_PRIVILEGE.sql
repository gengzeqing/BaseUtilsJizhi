-- 功能对应权限码
CREATE TABLE "FL_PRIVILEGE" (
  "ID" NUMBER(11,0) VISIBLE DEFAULT "ISEQ$$_83791".nextval NOT NULL,
  "CODE" NVARCHAR2(200) VISIBLE,
  "URI" NVARCHAR2(500) VISIBLE DEFAULT '',
  "REMARK" NVARCHAR2(100) VISIBLE,
  "FUNC_ID" NUMBER(11,0) VISIBLE,
  "STATUS" NUMBER(11,0) VISIBLE DEFAULT 1,
  "QUERY_TYPE" NUMBER(11,0) VISIBLE DEFAULT 1,
  "HAVE" NUMBER(11,0) VISIBLE DEFAULT 1
);
COMMENT ON COLUMN "FL_PRIVILEGE"."CODE" IS '权限编码';
COMMENT ON COLUMN "FL_PRIVILEGE"."URI" IS '功能url';
COMMENT ON COLUMN "FL_PRIVILEGE"."REMARK" IS '描述';
COMMENT ON COLUMN "FL_PRIVILEGE"."FUNC_ID" IS '功能Id';
COMMENT ON COLUMN "FL_PRIVILEGE"."STATUS" IS '状态';
COMMENT ON COLUMN "FL_PRIVILEGE"."HAVE" IS '拥有者 1瑞福德 2合作商 3共有';

ALTER TABLE "FL_PRIVILEGE" ADD CONSTRAINT "SYS_C009295" PRIMARY KEY ("ID");
ALTER TABLE "FL_PRIVILEGE" ADD CONSTRAINT "SYS_C008158" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "FL_PRIVILEGE" ADD CONSTRAINT "SYS_C009289" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;