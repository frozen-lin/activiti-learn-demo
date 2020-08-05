package com.frozen.activiti.aspect;


import com.frozen.activiti.exception.BaseException;
import com.frozen.activiti.util.ResponseUtil;
import com.frozen.activiti.vo.ResponseDataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * <program> shop-parent </program>
 * <description>  </description>
 *
 * @author : lw
 * @date : 2020-04-03 16:10
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 自定义业务异常
     *
     * @param e
     * @param httpServletRequest
     * @return ResponseDataResult
     */
    @ExceptionHandler(BaseException.class)
    public ResponseDataResult<Object> businessExceptionHandler(BaseException e, HttpServletRequest httpServletRequest) {
        String uri = httpServletRequest.getRequestURI();
        log.error("当前访问uri: [{}], 自定义业务异常：[{}]", uri, e);
        return ResponseUtil.getResponseError(e.getMessage(), null);
    }

    /**
     * 自定义业务异常
     *
     * @param e
     * @param httpServletRequest
     * @return ResponseDataResult
     */
    @ExceptionHandler(Exception.class)
    public ResponseDataResult<Object> businessExceptionHandler(Exception e, HttpServletRequest httpServletRequest) {
        String uri = httpServletRequest.getRequestURI();
        log.error("当前访问uri: [{}], 系统异常：[{}]", uri, e);
        return ResponseUtil.getResponseError("啊哦,出错啦"+e.getMessage(), null);
    }
}
