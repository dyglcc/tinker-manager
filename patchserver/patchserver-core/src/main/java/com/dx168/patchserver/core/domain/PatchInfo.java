package com.dx168.patchserver.core.domain;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by tong on 16/10/26.
 */
public class PatchInfo {
    /**
     * 未发布
     */
    public static final int STATUS_UNPUBLISHED = 0;
    /**
     * 已发布
     */
    public static final int STATUS_PUBLISHED = 1;
    /**
     * 已停止
     */
    public static final int STATUS_STOPPED = 2;
    /**
     * 灰度发布
     */
    public static final int PUBLISH_TYPE_GRAY = 0;
    /**
     * 正常发布
     */
    public static final int PUBLISH_TYPE_NORMAL = 1;

    private Integer id;
    private Integer userId;
    private String appUid;
    private String uid;
    private String versionName;
    private int patchVersion;
    private int publishVersion;
    private int status;//0 未发布 1 已发布 2已停止
    private int publishType;//0 灰度发布 1 正常发布
    private String tags;//灰度发布的tag用，分割
    private int publishForClients;//是否针对clients发布0，正常发布>0 针对clients发布
    private String storagePath;//存储路径
    private long patchSize;
    private long patchSizeJiagu;
    private String fileHash;
    private String fileHashJiagu;
    private String description;
    private String downloadUrl;
    private int applySuccessSize;
    private int applySize;

    private Date createdAt;
    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAppUid() {
        return appUid;
    }

    public void setAppUid(String appUid) {
        this.appUid = appUid;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getPatchVersion() {
        return patchVersion;
    }

    public void setPatchVersion(int patchVersion) {
        this.patchVersion = patchVersion;
    }

    public int getPublishVersion() {
        return publishVersion;
    }

    public void setPublishVersion(int publishVersion) {
        this.publishVersion = publishVersion;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPublishType() {
        return publishType;
    }

    public void setPublishType(int publishType) {
        this.publishType = publishType;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public long getPatchSize() {
        return patchSize;
    }

    public void setPatchSize(long patchSize) {
        this.patchSize = patchSize;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getApplySuccessSize() {
        return applySuccessSize;
    }

    public void setApplySuccessSize(int applySuccessSize) {
        this.applySuccessSize = applySuccessSize;
    }

    public int getApplySize() {
        return applySize;
    }

    public void setApplySize(int applySize) {
        this.applySize = applySize;
    }


    public String getFormatPatchSize() {
        long fileS = getPatchSize();
        if (fileS == 0) {
            return "0k";
        }
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public String getFormatApplyScale() {
        if (applySize == 0) {
            return "0%";
        }
        return (int) (applySuccessSize * 1.d / applySize * 1.d * 100) + "%";
    }

    public String getStatusDesc() {
        return getStatusDesc(getStatus());
    }

    public String getPublishTypeDesc() {
        return getPublishTypeDesc(getPublishType());
    }

    @Override
    public String toString() {
        return "PatchInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", appUid='" + appUid + '\'' +
                ", uid='" + uid + '\'' +
                ", versionName='" + versionName + '\'' +
                ", patchVersion=" + patchVersion +
                ", publishVersion=" + publishVersion +
                ", status=" + status +
                ", publishType=" + publishType +
                ", tags='" + tags + '\'' +
                ", storagePath='" + storagePath + '\'' +
                ", patchSize=" + patchSize +
                ", fileHash='" + fileHash + '\'' +
                ", description='" + description + '\'' +
                ", publish_for_clients='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }


    public static String getStatusDesc(int status) {
        if (status == PatchInfo.STATUS_UNPUBLISHED) {
            return "未发布";
        } else if (status == PatchInfo.STATUS_PUBLISHED) {
            return "已发布";
        } else if (status == PatchInfo.STATUS_STOPPED) {
            return "已停止";
        } else {
            throw new IllegalArgumentException("无法识别的状态: " + status);
        }
    }

    public static String getPublishTypeDesc(int publishType) {
        if (publishType == PUBLISH_TYPE_GRAY) {
            return "灰度发布";
        } else if (publishType == PUBLISH_TYPE_NORMAL) {
            return "全量发布";
        } else {
            throw new IllegalArgumentException("无法识别的发布类型: " + publishType);
        }
    }

    public int getPublishForClients() {
        return publishForClients;
    }

    public void setPublishForClients(int publishForClients) {
        this.publishForClients = publishForClients;
    }
}
