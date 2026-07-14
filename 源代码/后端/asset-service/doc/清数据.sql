CREATE OR REPLACE PROCEDURE "PLATFORM_ASSET"."clean_data"()
              AS
              DECLARE
              -- 声明变量
              tableName VARCHAR(255);
cur CURSOR FOR SELECT table_name
               FROM ALL_TABLES
               WHERE OWNER = 'PLATFORM_ASSET'
                 AND table_name NOT IN ('undo_log','sys_authority','sys_data_authority','sys_dictionary','sys_dictionary_item','sys_menu','sys_org','sys_org_ext','sys_region','sys_role','sys_tenant','sys_user_role','sys_user_tenant','sys_version','sys_version_menu'); -- 排除表名
DM_sql VARCHAR(1000);
BEGIN
    -- 开始循环遍历查询结果
    OPEN cur;
LOOP
        FETCH cur INTO tableName;
EXIT WHEN cur%NOTFOUND;

        -- 构造 UPDATE 语句并执行
DM_sql = CONCAT('DELETE FROM PLATFORM_ASSET.', tableName, ' WHERE id != 222179475947501;');

EXECUTE IMMEDIATE DM_sql;
END LOOP;
    -- 关闭游标
CLOSE cur;
END