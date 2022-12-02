package com.github.lockoct.entity;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.interceptor.annotation.PrevInsert;
import org.nutz.dao.interceptor.annotation.PrevUpdate;

import java.util.Date;

public class BaseEntity {
    @Column
    @Comment("创建时间")
    @PrevInsert(now = true)
    @ColDefine(type = ColType.DATETIME)
    private Date createTime;

    @Column
    @Comment("修改时间")
    @PrevInsert(now = true)
    @PrevUpdate(now = true)
    @ColDefine(type = ColType.DATETIME)
    private Date updateTime;

    @Column
    @Comment("创建用户")
    @ColDefine(type = ColType.VARCHAR, width = 36)
    private String createUser;

    @Column
    @Comment("修改用户")
    @ColDefine(type = ColType.VARCHAR, width = 36)
    private String updateUser;

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
