package test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

public class ProcessDefine {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 部署流程定义
	 */
	@Test
	public void deploymentProcess(){
		Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署相关的service
					 .createDeployment()//创建部署对象
					 .name("流程1")
					 .addClasspathResource("diagrams/hello.bpmn")
					 .addClasspathResource("diagrams/hello.png")
					 .deploy();
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	
}
