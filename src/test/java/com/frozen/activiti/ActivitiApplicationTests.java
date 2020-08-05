package com.frozen.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ActivitiApplication.class})
@Slf4j
class ActivitiApplicationTests {

    @Autowired
    RuntimeService runtimeService;

    @Test
    void completeMessage() {
        String messageName = "message";
        List<Execution> executions = runtimeService.createExecutionQuery()
                .messageEventSubscriptionName(messageName)
                .list();
        for (Execution e : executions) {
            runtimeService.messageEventReceived(messageName, e.getId());
            log.warn(e.getProcessInstanceId());
        }
        log.warn("消息事件完成");
    }

    @Test
    void completeSignal() {
        String signalName = "signal";
        List<Execution> executions = runtimeService.createExecutionQuery()
                .signalEventSubscriptionName(signalName)
                .list();
        for (Execution e : executions) {
            runtimeService.signalEventReceived(signalName, e.getId());
            log.warn(e.getProcessInstanceId());
        }
        log.warn("信号事件完成");
    }
}
