package test;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class HelloWorld {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 部署流程定义
	 */
	@Test
	public void deploymentProcess(){
		Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署相关的service
					 .createDeployment()//创建部署对象
					 .name("hello world流程")
					 .addClasspathResource("diagrams/hello.bpmn")
					 .addClasspathResource("diagrams/hello.png")
					 .deploy();
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	/**
	 * 启动流程实例
	 */
	@Test
	public void initProcess(){
		String processDefinitionKey = "helloworld";
		ProcessInstance processInstance = processEngine.getRuntimeService()
													.startProcessInstanceByKey(processDefinitionKey);//流程定义key == bpmn文件中id
		System.out.println("流程实例id"+processInstance.getId());
		System.out.println("流程定义id"+processInstance.getProcessDefinitionId());
	}
	
	@Test
	public void selectMyProcess(){
		String assignee = "王五";
		List<Task> list= processEngine.getTaskService().createTaskQuery().taskAssignee(assignee).list();
		if (!list.isEmpty()) {
			list.forEach(task -> {
				System.out.println("任务id："+task.getId());
				System.out.println("任务名称："+task.getName());
				System.out.println("任务创建时间："+task.getCreateTime());
				System.out.println("任务办理人："+task.getAssignee());
				System.out.println("实例流程id："+task.getProcessInstanceId());
				System.out.println("执行对象id："+task.getExecutionId());
				System.out.println("流程定义id："+task.getProcessDefinitionId());
				System.out.println("############################################");
			});
		} else {
			System.out.println("没有任务！");
		}
	}
	
	/**
	 * 完成任务
	 */
	@Test
	public void finishMyTask(){
		String taskId = "10002";
		processEngine.getTaskService().complete(taskId);
		System.out.println("完成任务："+ taskId);
	}
	
	/**
	 * 查看历史流程
	 */
	@Test
	public void selectHistroyProcess(){
		List<HistoricTaskInstance> list = processEngine.getHistoryService()
												.createHistoricTaskInstanceQuery()
												.list();
		if (!list.isEmpty()) {
			list.forEach(task -> {
				System.out.println("任务id："+task.getId());
				System.out.println("任务名称："+task.getName());
				System.out.println("任务创建时间："+task.getCreateTime());
				System.out.println("任务办理人："+task.getAssignee());
				System.out.println("实例流程id："+task.getProcessInstanceId());
				System.out.println("执行对象id："+task.getExecutionId());
				System.out.println("流程定义id："+task.getProcessDefinitionId());
				System.out.println("############################################");
			});
		} else {
			System.out.println("没有任务！");
		}
	}
}
