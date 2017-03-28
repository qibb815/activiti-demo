package test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.Test;


public class TestLeaveProcess {

	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void deleteProcess(){
		String deploymentId = "20001";
		processEngine.getRepositoryService().deleteDeployment(deploymentId, true);
		System.out.println("删除成功！");
	}
	
	@Test
	public void deployment(){
		Deployment deployment = 
				processEngine.getRepositoryService()
					.createDeployment()
					.name("请假流程")
					.addClasspathResource("diagrams/leave.bpmn")
					.addClasspathResource("diagrams/leave.png")
					.deploy();
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	@Test
	public void initProcess(){
		Map<String, Object> variables = new HashMap<>();
		variables.put("leader", "张三");
		String processDefinitionKey = "leaveProcess";
		ProcessInstance processInstance =
				processEngine.getRuntimeService()
							.startProcessInstanceByKey(processDefinitionKey, variables);
		
		System.out.println("流程实例id: "+processInstance.getId());
		System.out.println("流程定义id: "+processInstance.getProcessDefinitionId());
	}
	
	@Test
	public void viewPng() throws IOException{
		String deploymentId = "20001";
		List<String> list = processEngine.getRepositoryService().getDeploymentResourceNames(deploymentId);
		String resourceName = "";
		if (!list.isEmpty()) {
			for(String n:list){
				if (n.indexOf(".png")>=0)
					resourceName = n;
			}
		}
		
		InputStream in = processEngine.getRepositoryService().getResourceAsStream(deploymentId, resourceName);
		File file = new File("E:/"+resourceName);
		FileUtils.copyInputStreamToFile(in, file);
	}
	
	@Test
	public void selectTask(){
//		String leader = "张三";
		String leader = "李四";
		List<Task> list =
				processEngine.getTaskService().createTaskQuery()
									//.processInstanceBusinessKey(deploymentId)
									//.processVariableValueEquals("leader", leader)
									.taskAssignee(leader)
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
				System.out.println("父级task id："+task.getParentTaskId());
				System.out.println("############################################");
			});
		} else {
			System.out.println("没有任务！");
		}
	}
	
	@Test
	public void nextTo(){
		String taskId = "52505";
		Map<String, Object> variables = new HashMap<>();
		variables.put("pass", true);
		variables.put("middle", "李四");
		processEngine.getTaskService().complete(taskId, variables);
		System.out.println("项目经理审批完成！");
	}
}
