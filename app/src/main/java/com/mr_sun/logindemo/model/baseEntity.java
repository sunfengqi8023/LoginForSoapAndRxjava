package com.mr_sun.logindemo.model;

/**
 * Copyright (c) 2016. 东华博育云有限公司 Inc. All rights reserved.
 * com.mr_sun.logindemo.model
 * 作者：Created by sfq on 2016/12/2.
 * 联系方式：
 * 功能描述：
 * 修改：无
 */
public abstract class baseEntity {
    private String message;
    private String statuscode;

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
