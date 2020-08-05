package com.frozen.activiti.vo;

import lombok.Data;

/**
 * <program> activiti </program>
 * <description>  </description>
 *
 * @author : wlin
 * @date : 2020-08-03 14:24
 **/
@Data
public class ResponseDataResult<T> {
    private Integer respCode;
    private String respMsg;
    private T data;

    public ResponseDataResult() {
    }

    public ResponseDataResult(Integer respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    public ResponseDataResult(Integer respCode, String respMsg, T data) {
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.data = data;
    }
}
