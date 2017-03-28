package com.hello.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;

public class HelloWorld {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 部署流程定义
	 */
	public void deploymentProcess(){
		processEngine.getRepositoryService()//与流程定义和部署相关的service
					 .createDeployment()//创建部署对象
					 .addClasspathResource("diagrams/hello.bpmn")
					 .addClasspathResource("diagrams/hello.png")
					 .deploy();
	}
}
