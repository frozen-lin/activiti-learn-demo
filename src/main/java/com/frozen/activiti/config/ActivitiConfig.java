package com.frozen.activiti.config;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * <program> activiti </program>
 * <description>  </description>
 *
 * @author : wlin
 * @date : 2020-08-03 10:58
 **/
@Configuration
public class ActivitiConfig implements ProcessEngineConfigurationConfigurer {

    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        /**
         * 解决流程图中文乱码问题
         */
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");
    }
}
