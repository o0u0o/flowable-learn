package com.o0u0o.flowable.part;
import com.o0u0o.flowable.FlowableHiApplicationTests;
import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;import org.flowable.task.api.Task;import org.junit.Test;import java.util.HashMap;import java.util.Map;import java.util.Set;

/**
 * <h1>EL表达式候选人</h1>
 * @author o0u0o
 * @date 2023/3/18 10:31 AM
 */
public class Part05 extends FlowableHiApplicationTests {
    /**
     * <h2>测试部署</h2>
     * 将流程模型部署到数据库
     */
    @Test
    public void testDeploy(){
        //1.获取 processEngine 对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
        //2.获取 RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3.关联要部署的文件 并且完成流程的部署操作
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("bpmn/holiday-task.bpmn20.xml")
                .name("请假流程新")
                //部署流程
                .deploy();

        System.out.println("deploy.getId:" + deploy.getId());
        System.out.println("deploy.getName:" + deploy.getName());
    }

    /**
     * <h1>启动流程实例</h1>
     */
    @Test
    public void testProcess(){
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = engine.getRuntimeService();

//        Map<String, Object> variables = new HashMap<>();
//        variables.put("assignee0", "张三");
//        variables.put("assignee1", "李四");
        String processDefinitionId = "holiday:3:12504";

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId);
        //ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, variables);
        System.out.println("processInstance = " + processInstance);

    }

    @Test
    public void testCompleteTask(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processInstanceId("7504")
                .taskAssignee("小李")
                .singleResult();

        //获取当前的流程实例绑定流程变量
        Map<String,Object> processVariables = task.getProcessVariables();
        Set<String> keys = processVariables.keySet();

        //完成任务
        taskService.complete(task.getId());
    }

}
