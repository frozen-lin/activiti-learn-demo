package com.frozen.activiti.listener;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * <program> activiti </program>
 * <description>  </description>
 *
 * @author : wlin
 * @date : 2020-08-05 10:30
 **/
@Slf4j
public class MyExecutionListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        log.warn("定时事件完成---监听事件:{},流程实例Id:{}-----", execution.getEventName(), execution.getProcessInstanceId());
    }
}
