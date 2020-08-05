package com.frozen.activiti.vo;

import lombok.Data;
import org.activiti.engine.repository.Deployment;

import java.util.Date;

@Data
public class DeploymentResponse {

  private String id;
  private String name;
  private Date deploymentTime;
  private String category;
  private String tenantId;
  
  public DeploymentResponse(Deployment deployment) {
    setId(deployment.getId());
    setName(deployment.getName());
    setDeploymentTime(deployment.getDeploymentTime());
    setCategory(deployment.getCategory());
    setTenantId(deployment.getTenantId());
  }
}