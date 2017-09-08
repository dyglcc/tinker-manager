package com.dx168.patchserver.core.domain;

import java.util.Date;

public class ClientsFix {

    // 自增id
    private Integer id;
    // 补丁id
    private Integer patchId;

    // 设备id
    private String clientId;
    // 创建时间
    private Date createdAt;
    // 更新时间
    private Date updatedAt;

    public Integer getPatchId() {
        return patchId;
    }

    public void setPatchId(Integer patchId) {
        this.patchId = patchId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ClientFix{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", patchId='" + patchId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
