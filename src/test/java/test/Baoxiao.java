package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class Baoxiao {

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
					.name("报销流程")
					.addClasspathResource("diagrams/baoxiao.bpmn")
					.addClasspathResource("diagrams/baoxiao.png")
					.deploy();
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	@Test
	public void initProcess(){
		String processDefinitionKey = "baoxiaoProcess";
		ProcessInstance processInstance =
				processEngine.getRuntimeService()
							.startProcessInstanceByKey(processDefinitionKey);
		
		System.out.println("流程实例id: "+processInstance.getId());
		System.out.println("流程定义id: "+processInstance.getProcessDefinitionId());
	}
	
	
	@Test
	public void selectTask(){
		String leader = "张三";
		String deploymentId = "57501";
		String processDefinitionKeyLike = "baoxiaoProcess";
		List<Task> list =
				processEngine.getTaskService().createTaskQuery()
									//.processInstanceBusinessKey(deploymentId)
//									.deploymentId(deploymentId)
									.processDefinitionKeyLike(processDefinitionKeyLike)
									//.taskAssignee(leader)
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
		String taskId = "60004";
		Map<String, Object> variables = new HashMap<>();
		variables.put("many", 50);
		processEngine.getTaskService().complete(taskId, variables);
		System.out.println("项目经理审批完成！");
	}

	@Test
	public void nextTo2(){
		String taskId = "62503";
		Map<String, Object> variables = new HashMap<>();
		variables.put("many", 50);
		processEngine.getTaskService().complete(taskId);
		System.out.println("经理1审批完成！");
	}
	
	@Test
	public void nextTo3(){
		String taskId = "65002";
		Map<String, Object> variables = new HashMap<>();
		variables.put("pass", false);
		processEngine.getTaskService().complete(taskId, variables);
		System.out.println("经理3审批完成！");
	}
}
