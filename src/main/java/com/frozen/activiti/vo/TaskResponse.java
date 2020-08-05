package com.frozen.activiti.vo;


import lombok.Data;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;

import java.util.Date;
import java.util.Map;

/**
 * <program> activiti </program>
 * <description>  </description>
 *
 * @author : wlin
 * @date : 2020-08-04 16:42
 **/
@Data
public class TaskResponse implements Task {

    protected String id;
    protected String name;
    protected String description;
    protected int priority;
    protected String owner;
    protected String assignee;
    protected Date dueDate;
    protected String category;
    protected String parentTaskId;
    protected String tenantId;
    protected String formKey;
    protected Date createTime;
    protected String taskDefinitionKey;
    protected String processInstanceId;
    protected String processDefinitionId;

    @Override
    public void setLocalizedName(String name) {

    }

    @Override
    public void setLocalizedDescription(String description) {

    }

    @Override
    public DelegationState getDelegationState() {
        return null;
    }

    @Override
    public void setDelegationState(DelegationState delegationState) {

    }

    @Override
    public void delegate(String userId) {

    }

    @Override
    public boolean isSuspended() {
        return false;
    }

    @Override
    public String getExecutionId() {
        return null;
    }

    @Override
    public Map<String, Object> getTaskLocalVariables() {
        return null;
    }

    @Override
    public Map<String, Object> getProcessVariables() {
        return null;
    }
}
