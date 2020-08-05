package com.frozen.activiti.util;


import com.frozen.activiti.vo.ResponseDataResult;
import org.springframework.http.HttpStatus;

/**
 * <description> 接口返回实体工具类 </description>
 *
 * @author : lw
 * @date : 2020-03-25 14:06
 **/
public class ResponseUtil {
    /**
     * <description> 设置并获取ResponseDataEntity </description>
     *
     * @param resCode : 响应码
     * @param resMsg  : 响应信息
     * @param data    :  响应数据实体类
     * @return : com.frozen.activiti.vo.ResponseDataResult<T>
     * @author : lw
     */
    public static <T> ResponseDataResult<T> getResponse(Integer resCode, String resMsg, T data) {
        return new ResponseDataResult<>(resCode, resMsg, data);
    }

    /**
     * <description> 获取通用成功ResponseDataEntity,resCode:200,resMsg:success,data:null </description>
     *
     * @return : com.frozen.activiti.vo.ResponseDataResult<java.lang.Object>
     * @author : lw
     */
    public static ResponseDataResult<Object> getResponseSuccess() {
        return getResponseSuccess(null);
    }

    /**
     * <description> 获取通用成功ResponseDataEntity,resCode:200,resMsg:success,自定义实体类 </description>
     *
     * @param data :响应数据实体类
     * @return : com.frozen.activiti.vo.ResponseDataResult<T>
     * @author : lw
     * @date : 2020/3/25 14:16
     */
    public static <T> ResponseDataResult<T> getResponseSuccess(T data) {
        return getResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    /**
     * <description> 获取通用成功ResponseDataEntity,resCode:200,自定义信息和实体类 </description>
     *
     * @param respMsg :响应信息
     * @param data   :响应数据实体类
     * @return : com.frozen.activiti.vo.ResponseDataResult<T>
     * @author : lw
     * @date : 2020/3/25 14:16
     */
    public static <T> ResponseDataResult<T> getResponseSuccess(String respMsg, T data) {
        return getResponse(HttpStatus.OK.value(), respMsg, data);
    }

    /**
     * <description> 获取通用成功ResponseDataEntity,respCode:500,respMsg:Internal Server Error,data:null </description>
     *
     * @return : com.frozen.activiti.vo.ResponseDataResult<java.lang.Object>
     * @author : lw
     * @date : 2020/3/25 14:15
     */
    public static ResponseDataResult<Object> getResponseError() {
        return getResponseError(null);
    }

    /**
     * <description> 获取通用成功ResponseDataResult,respCode:500,respMsg:Internal Server Error,自定义实体类 </description>
     *
     * @param data :响应数据实体类
     * @return : com.frozen.activiti.vo.ResponseDataResult<T>
     * @author : lw
     * @date : 2020/3/25 14:16
     */
    public static <T> ResponseDataResult<T> getResponseError(T data) {
        return getResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), data);
    }

    /**
     * <description> 获取通用失败ResponseDataResult,respCode:500,自定义信息和实体类 </description>
     *
     * @param resMsg :响应信息
     * @param data   :响应数据实体类
     * @return : com.frozen.activiti.vo.ResponseDataResult<T>
     * @author : lw
     * @date : 2020/3/25 14:16
     */
    public static <T> ResponseDataResult<T> getResponseError(String resMsg, T data) {
        return getResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), resMsg, data);
    }


}
