package io.shenjian.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;

public class ApiResponse {
    private Integer errorCode;
    private String reason;
    private String data;

    public Integer getErrorCode() {
        return errorCode;
    }

    @JSONField(name = "error_code")
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
