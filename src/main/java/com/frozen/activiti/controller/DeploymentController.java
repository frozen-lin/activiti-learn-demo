package com.frozen.activiti.controller;

import com.frozen.activiti.common.PageConstants;
import com.frozen.activiti.util.ResponseUtil;
import com.frozen.activiti.util.WebUtil;
import com.frozen.activiti.vo.DeploymentResponse;
import com.frozen.activiti.vo.PaginationData;
import com.frozen.activiti.vo.ResponseDataResult;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 发布流程
 */
@RestController
@RequestMapping("deployments")
public class DeploymentController {
    @Autowired
    RepositoryService repositoryService;

    @GetMapping
    public ResponseDataResult<PaginationData<DeploymentResponse>> getList() {
        return getList(1, PageConstants.DEFAULT_PAGE_SIZE);
    }

    @GetMapping("/{page}/{pageSize}")
    public ResponseDataResult<PaginationData<DeploymentResponse>> getList(@PathVariable Integer page, @PathVariable Integer pageSize) {
        List<Deployment> deployments = repositoryService.createDeploymentQuery()
                .listPage(pageSize * (page - 1), pageSize);
        long totalCount = repositoryService.createDeploymentQuery().count();
        List<DeploymentResponse> deploymentResponses = new ArrayList<>();
        deployments.forEach(deployment -> deploymentResponses.add(new DeploymentResponse(deployment)));
        PaginationData<DeploymentResponse> paginationData = WebUtil.getPaginationData(page, deploymentResponses, pageSize, (int) totalCount);
        return ResponseUtil.getResponseSuccess(paginationData);
    }

    @DeleteMapping("/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseDataResult<Object> deleteOne(@PathVariable("id") String id) {
        repositoryService.deleteDeployment(id);
        return ResponseUtil.getResponseSuccess();
    }
}
