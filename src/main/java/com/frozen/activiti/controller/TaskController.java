package com.frozen.activiti.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frozen.activiti.common.PageConstants;
import com.frozen.activiti.util.ResponseUtil;
import com.frozen.activiti.util.WebUtil;
import com.frozen.activiti.vo.PaginationData;
import com.frozen.activiti.vo.ResponseDataResult;
import com.frozen.activiti.vo.TaskResponse;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <program> activiti </program>
 * <description>  </description>
 *
 * @author : wlin
 * @date : 2020-08-04 09:22
 **/
@RestController
@RequestMapping("tasks")
@Slf4j
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    ObjectMapper objectMapper;

    @GetMapping
    public ResponseDataResult<PaginationData<Task>> getList() {
        return getList(1, PageConstants.DEFAULT_PAGE_SIZE);
    }

    @GetMapping("/{page}/{pageSize}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseDataResult<PaginationData<Task>> getList(@PathVariable Integer page, @PathVariable Integer pageSize) {
        List<Task> taskList = taskService.createTaskQuery().listPage(pageSize * (page - 1), pageSize);
        long totalCount = taskService.createTaskQuery().count();
        List<Task> responseTaskList = new ArrayList<>(taskList.size());
        taskList.forEach(task -> {
            Task newTask = new TaskResponse();
            BeanUtils.copyProperties(task, newTask);
            responseTaskList.add(newTask);
        });
        PaginationData<Task> paginationData = WebUtil.getPaginationData(page, responseTaskList, pageSize, (int) totalCount);
        return ResponseUtil.getResponseSuccess(paginationData);
    }

    @PostMapping("/commit/{taskId}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseDataResult<Object> commit(@PathVariable String taskId, @RequestBody(required = false) Map<String, Object> params) {
        taskService.complete(taskId, params);
        return ResponseUtil.getResponseSuccess();
    }
}
