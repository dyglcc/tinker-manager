/*
changelist
v1.0.2.1
增加表t_client_fix，用于针对设备id更新。
alter table t_patch_info add publish_for_clients int DEFAULT 0 COMMENT '0 正常发布 count 针对设备发布'
创建触发器，等增加和删除clientid是更新patch_info表的publish_for_clients
t_trigger_after_insert_t_client_fix
t_trigger_after_delete_t_client_fix
v1.0.2
alter table t_patch_info add `apply_success_size` int COMMENT '被应用成功的次数'
alter table t_patch_info add `apply_size` int COMMENT '被应用次数'

v1.0.5
alter table t_app_info add `package_name` varchar(64) DEFAULT NULL COMMENT 'android的包名 iOS的bundle_id'

v1.1.0
增加t_full_update_info、t_childuser_app表
*/

/*用户表*/
CREATE TABLE `t_user` (
  `id`           INT         NOT NULL AUTO_INCREMENT
  COMMENT 'ID',
  `parent_id`    INT                  DEFAULT NULL
  COMMENT '父账号id',
  `username`     VARCHAR(32) NOT NULL
  COMMENT '用户名',
  `mobile`       VARCHAR(16)          DEFAULT NULL
  COMMENT '手机号',
  `email`        VARCHAR(32)          DEFAULT NULL
  COMMENT '邮箱',
  `password`     VARCHAR(32) NOT NULL
  COMMENT '登录密码',
  `avatar`       VARCHAR(128)         DEFAULT NULL
  COMMENT '头像',
  `account_type` INT                  DEFAULT NULL
  COMMENT '账户类型 0: admin 1: 开发人员 1: 测试人员',
  `created_at`   DATETIME             DEFAULT NULL
  COMMENT '创建时间',
  `updated_at`   DATETIME             DEFAULT NULL
  COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `MOBILE` (`mobile`),
  UNIQUE KEY `EMAIL` (`email`),
  UNIQUE KEY `USERNAME` (`username`),
  FOREIGN KEY (parent_id) REFERENCES t_user (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

/*应用信息表*/
CREATE TABLE `t_app_info` (
  `id`           INT         NOT NULL AUTO_INCREMENT
  COMMENT 'ID',
  `user_id`      INT COMMENT '用户id',
  `appname`      VARCHAR(32) NOT NULL
  COMMENT '应用名字',
  `platform`     VARCHAR(8)  NOT NULL
  COMMENT '平台',
  `uid`          VARCHAR(32)          DEFAULT NULL
  COMMENT '应用的唯一标示',
  `description`  VARCHAR(32) NOT NULL
  COMMENT '应用描述',
  `secret`       VARCHAR(32) NOT NULL
  COMMENT '应用秘钥',
  `public_key`   VARCHAR(64)          DEFAULT NULL
  COMMENT '公钥',
  `private_key`  VARCHAR(64)          DEFAULT NULL
  COMMENT '私钥',
  `package_name` VARCHAR(64)          DEFAULT NULL
  COMMENT 'android的包名 IOS的bundle_id',
  `created_at`   DATETIME             DEFAULT NULL
  COMMENT '创建时间',
  `updated_at`   DATETIME             DEFAULT NULL
  COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UID` (`uid`),
  FOREIGN KEY (user_id) REFERENCES t_user (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

/*版本信息表*/
CREATE TABLE `t_version_info` (
  `id`           INT         NOT NULL AUTO_INCREMENT
  COMMENT 'ID',
  `user_id`      INT COMMENT '用户id',
  `app_uid`      VARCHAR(64) NOT NULL
  COMMENT '应用id',
  `version_name` VARCHAR(32) NOT NULL
  COMMENT '版本名字',
  `created_at`   DATETIME             DEFAULT NULL
  COMMENT '创建时间',
  `updated_at`   DATETIME             DEFAULT NULL
  COMMENT '修改时间',
  PRIMARY KEY (`id`),
  FOREIGN KEY (user_id) REFERENCES t_user (id),
  FOREIGN KEY (app_uid) REFERENCES t_app_info (uid)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

/*补丁信息表*/
CREATE TABLE `t_patch_info` (
  `id`                  INT          NOT NULL AUTO_INCREMENT
  COMMENT 'ID',
  `user_id`             INT COMMENT '用户id',
  `app_uid`             VARCHAR(32)  NOT NULL
  COMMENT '应用id',
  `uid`                 VARCHAR(32)  NOT NULL
  COMMENT 'uid',
  `version_name`        VARCHAR(32)  NOT NULL
  COMMENT '版本名字',
  `patch_version`       INT COMMENT '补丁版本',
  `publish_version`     INT COMMENT '发布版本',
  `status`              INT COMMENT '0 未发布 1 已发布',
  `publish_type`        INT COMMENT '0 灰度发布 1 正常发布',
  `publish_for_clients` INT                   DEFAULT 0
  COMMENT '0 正常发布 count针对设备发布',
  `patch_size`          LONG COMMENT '补丁大小',
  `patch_size_jiagu`    LONG COMMENT '给加固apk用的补丁的补丁大小',
  `file_hash`           VARCHAR(64)  NOT NULL
  COMMENT '文件的hash值',
  `file_hash_jiagu`     VARCHAR(64)           DEFAULT NULL
  COMMENT '给加固apk用的补丁的文件的hash值',
  `description`         VARCHAR(32)  NOT NULL
  COMMENT '补丁描述',
  `tags`                VARCHAR(256)          DEFAULT NULL
  COMMENT '灰度发布的tag用，分割',
  `storage_path`        VARCHAR(256) NOT NULL
  COMMENT '存储路径',
  `download_url`        VARCHAR(256)          DEFAULT NULL
  COMMENT '下载地址',
  `apply_success_size`  INT COMMENT '被应用成功的次数',
  `apply_size`          INT COMMENT '被应用的次数',
  `created_at`          DATETIME              DEFAULT NULL
  COMMENT '创建时间',
  `updated_at`          DATETIME              DEFAULT NULL
  COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UUID` (`uid`),
  FOREIGN KEY (user_id) REFERENCES t_user (id),
  FOREIGN KEY (app_uid) REFERENCES t_app_info (uid)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

/*测试人员表(与账号和应用向关联)*/
CREATE TABLE `t_tester` (
  `id`          INT         NOT NULL AUTO_INCREMENT
  COMMENT 'ID',
  `user_id`     INT COMMENT '用户id',
  `app_uid`     VARCHAR(32) NOT NULL
  COMMENT '应用id',
  `tag`         VARCHAR(32)          DEFAULT NULL
  COMMENT '标记值',
  `email`       VARCHAR(32)          DEFAULT NULL
  COMMENT '邮箱',
  `description` VARCHAR(32) NOT NULL
  COMMENT '描述',
  `created_at`  DATETIME             DEFAULT NULL
  COMMENT '创建时间',
  `updated_at`  DATETIME             DEFAULT NULL
  COMMENT '修改时间',
  PRIMARY KEY (`id`),
  FOREIGN KEY (user_id) REFERENCES t_user (id),
  FOREIGN KEY (app_uid) REFERENCES t_app_info (uid)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

/*机型黑名单*/
CREATE TABLE `t_model_blacklist` (
  `id`          INT         NOT NULL AUTO_INCREMENT
  COMMENT 'ID',
  `user_id`     INT COMMENT '用户id',
  `regular_exp` VARCHAR(64) NOT NULL
  COMMENT '正则表达式',
  `description` VARCHAR(32) NOT NULL
  COMMENT '描述',
  `created_at`  DATETIME             DEFAULT NULL
  COMMENT '创建时间',
  `updated_at`  DATETIME             DEFAULT NULL
  COMMENT '修改时间',
  PRIMARY KEY (`id`),
  FOREIGN KEY (user_id) REFERENCES t_user (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

/*渠道*/
CREATE TABLE `t_channel` (
  `id`           INT         NOT NULL AUTO_INCREMENT
  COMMENT 'ID',
  `user_id`      INT COMMENT '用户id',
  `channel_name` VARCHAR(64) NOT NULL
  COMMENT '渠道的名字',
  `description`  VARCHAR(32)          DEFAULT NULL
  COMMENT '描述',
  `created_at`   DATETIME             DEFAULT NULL
  COMMENT '创建时间',
  `updated_at`   DATETIME             DEFAULT NULL
  COMMENT '修改时间',
  PRIMARY KEY (`id`),
  FOREIGN KEY (user_id) REFERENCES t_user (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

/*全量更新信息*/
CREATE TABLE `t_full_update_info` (
  `id`                     INT          NOT NULL AUTO_INCREMENT
  COMMENT 'ID',
  `app_uid`                VARCHAR(64)  NOT NULL
  COMMENT '应用id',
  `latest_version`         VARCHAR(32)  NOT NULL
  COMMENT '最新版本的versionName',
  `title`                  VARCHAR(32)           DEFAULT NULL
  COMMENT '弹出更新提示的标题',
  `description`            VARCHAR(32)  NOT NULL
  COMMENT '更新说明',
  `lowest_support_version` VARCHAR(32)           DEFAULT NULL
  COMMENT '低于这个版本的都强制更新',
  `default_url`            VARCHAR(256) NOT NULL
  COMMENT '默认的下载地址(没有传渠道号)',
  `channel_url`            VARCHAR(256) NOT NULL
  COMMENT '渠道下载地址',
  `file_size`              VARCHAR(32)           DEFAULT NULL
  COMMENT '文件大小',
  `network_type`           VARCHAR(32)           DEFAULT NULL
  COMMENT '2G|3G|4G|WIFI',
  `status`                 INT COMMENT '当前状态  0暂停 1已开启',
  `created_at`             DATETIME              DEFAULT NULL
  COMMENT '创建时间',
  `updated_at`             DATETIME              DEFAULT NULL
  COMMENT '修改时间',
  PRIMARY KEY (`id`),
  FOREIGN KEY (app_uid) REFERENCES t_app_info (uid),
  UNIQUE KEY `APP_UID` (`app_uid`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

/*子账号和应用关联表*/
CREATE TABLE `t_childuser_app` (
  `id`         INT         NOT NULL AUTO_INCREMENT
  COMMENT 'ID',
  `user_id`    INT COMMENT '用户id',
  `app_uid`    VARCHAR(32) NOT NULL
  COMMENT 'app的uid',
  `appname`    VARCHAR(32) NOT NULL
  COMMENT '应用名字(冗余字段)',
  `created_at` DATETIME             DEFAULT NULL
  COMMENT '创建时间',
  `updated_at` DATETIME             DEFAULT NULL
  COMMENT '修改时间',
  PRIMARY KEY (`id`),
  FOREIGN KEY (user_id) REFERENCES t_user (id),
  FOREIGN KEY (app_uid) REFERENCES t_app_info (uid),
  UNIQUE KEY `APP_UID` (`app_uid`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

/*针对设备更新的设备表*/
CREATE TABLE `t_client_fix` (
  `id`         INT NOT NULL AUTO_INCREMENT
  COMMENT 'ID',
  `client_id`  VARCHAR(32) COMMENT '被更新设备id',
  `patch_id`   INT COMMENT '补丁的id',
  `apply`      INT          DEFAULT 0
  COMMENT '执行状态',
  `created_at` DATETIME     DEFAULT NULL
  COMMENT '创建时间',
  `updated_at` DATETIME     DEFAULT NULL
  COMMENT '修改时间',
  PRIMARY KEY (`id`),
  FOREIGN KEY (patch_id) REFERENCES t_patch_info (id)
    ON DELETE CASCADE,
  UNIQUE KEY (`patch_id`, `client_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

/*创建trigger当插入t_client_fix表行数据时更新t_patch_info字段publish_for_clients*/

DROP TRIGGER IF EXISTS t_trigger_after_insert_t_client_fix;
CREATE TRIGGER t_trigger_after_insert_t_client_fix
AFTER INSERT ON t_client_fix
FOR EACH ROW
  UPDATE t_patch_info
  SET publish_for_clients = (SELECT count(*)
                             FROM t_client_fix
                             WHERE patch_id = NEW.patch_id)
  WHERE id = NEW.patch_id;

/*创建trigger当删除t_client_fix表行数据时更新t_patch_info字段publish_for_clients*/

DROP TRIGGER IF EXISTS t_trigger_after_delete_t_client_fix;
CREATE TRIGGER t_trigger_after_delete_t_client_fix
AFTER DELETE ON t_client_fix
FOR EACH ROW
  UPDATE t_patch_info
  SET publish_for_clients = (SELECT count(*)
                             FROM t_client_fix
                             WHERE patch_id = OLD.patch_id)
  WHERE id = OLD.patch_id;

/*创建t_client_fix表的分页存储过程，stored procedure proce_page_client_fix*/

DELIMITER $$

DROP PROCEDURE IF EXISTS proce_page_client_fix;
$$
CREATE PROCEDURE proce_page_client_fix(
  IN curPage  INT,
  IN pageSize INT,
  IN patchId  INT
)
  BEGIN
    IF curPage <= 0
    THEN
      SET curPage = 1;
    END IF;
    IF curPage IS NULL
    THEN
      SET curPage = 1;
    END IF;
    IF pageSize <= 0
    THEN
      SET pageSize = 10;
    END IF;
    IF pageSize IS NULL
    THEN
      SET pageSize = 10;
    END IF;
    SET @patchId = patchId;
    SET @pageSize = pageSize;
    SET @curPage = (curPage - 1) * pageSize;
    PREPARE s1 FROM "
    select sql_calc_found_rows * from t_client_fix where patch_id = ? limit ?,?";
    EXECUTE s1
    USING @patchId, @curPage, @pageSize;
    DEALLOCATE PREPARE s1;
    SELECT found_rows() AS count;
  END


    /*创建t_version_info表的分页存储过程，stored procedure proc_page_versions*/

    DELIMITER $$

DROP PROCEDURE IF EXISTS proc_page_versions;
$$
CREATE PROCEDURE proc_page_versions(
  IN curPage  INT,
  IN pageSize INT,
  IN appUid   VARCHAR(64)
)
  BEGIN
    IF curPage <= 0
    THEN
      SET curPage = 1;
    END IF;
    IF curPage IS NULL
    THEN
      SET curPage = 1;
    END IF;
    IF pageSize <= 0
    THEN
      SET pageSize = 10;
    END IF;
    IF pageSize IS NULL
    THEN
      SET pageSize = 10;
    END IF;
    SET @appUid = appUid;
    SET @pageSize = pageSize;
    SET @curPage = (curPage - 1) * pageSize;
    PREPARE s2 FROM "
    SELECT sql_calc_found_rows id,user_id, app_uid, version_name, created_at, updated_at
    FROM t_version_info where app_uid = ?
    order by created_at desc limit ?,? ";
    EXECUTE s2
    USING @appUid, @curPage, @pageSize;
    DEALLOCATE PREPARE s2;
    SELECT found_rows() AS count;
  END


    /*创建t_version_info表的分页存储过程，stored procedure proc_page_patch*/

    DELIMITER $$

DROP PROCEDURE IF EXISTS proc_page_patch;
$$
CREATE PROCEDURE proc_page_patch(
  IN appUid      VARCHAR(64),
  IN versionName VARCHAR(64),
  IN curPage     INT,
  IN pageSize    INT
)
  BEGIN
    IF curPage <= 0
    THEN
      SET curPage = 1;
    END IF;
    IF curPage IS NULL
    THEN
      SET curPage = 1;
    END IF;
    IF pageSize <= 0
    THEN
      SET pageSize = 10;
    END IF;
    IF pageSize IS NULL
    THEN
      SET pageSize = 10;
    END IF;
    SET @appUid = appUid;
    SET @versionName = versionName;
    SET @pageSize = pageSize;
    SET @curPage = (curPage - 1) * pageSize;
    PREPARE spatchs FROM "
    SELECT sql_calc_found_rows id,user_id, app_uid,uid,
      version_name,patch_version,publish_version,status,publish_type,tags,storage_path,patch_size,file_hash,description,download_url,apply_success_size,apply_size,
      publish_for_clients,created_at, updated_at
    FROM t_patch_info
    where app_uid = ? and version_name = ?
    order by created_at desc limit ?,? ";
    EXECUTE spatchs
    USING @appUid, @versionName, @curPage, @pageSize;
    DEALLOCATE PREPARE spatchs;
    SELECT found_rows() AS count;
  END
