package com.o0u0o.flowable.part;
import com.o0u0o.flowable.FlowableHiApplicationTests;
import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
/**
 * <h1>流程变量</h1>
 * @author o0u0o
 * @date 2023/3/20 10:47 AM
 */
public class Part06 extends FlowableHiApplicationTests {
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
                .addClasspathResource("bpmn/出差申请单.bpmn20.xml")
                .name("出差申流程")
                //部署流程
                .deploy();

        System.out.println("deploy.getId(流程部署ID):" + deploy.getId());
        System.out.println("deploy.getName((流程部署名):" + deploy.getName());
    }

    /**
     * <h1>启动流程实例</h1>
     */
    @Test
    public void runProcess(){
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = engine.getRuntimeService();

        //流程定义ID
        String processDefinitionId = "evection:1:4";
        //在启动流程实例的时候创建了流程变量（定义了执行人是谁）
        Map<String, Object> variables = new HashMap<>();
        variables.put("assignee0", "张三");
        variables.put("assignee1", "李四");
        variables.put("assignee2", "王五");
        variables.put("assignee3", "赵会计");

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, variables);
        System.out.println("processInstance = " + processInstance);
        System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId());

    }

    @Test
    public void completeTask(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processInstanceId("2501")
                //张三去处理这个任务
                .taskAssignee("张三")
                .singleResult();

        //获取当前流程实例所有的 已有的流程变量
        Map<String,Object> processVariables = task.getProcessVariables();
        //新设置一个流程变量
        processVariables.put("num", 2);
        taskService.complete(task.getId(), processVariables);
    }

    /**
     * <h2>根据Task编号来更新流程变量</h2>
     * 举例：一开始我申请出差2天，但是因为疫情原因，需要多出差2天
     */
    @Test
    public void updateVariables(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        //现在已经流转到李四这边了
        Task task = taskService.createTaskQuery()
                .processInstanceId("2501")
                .taskAssignee("李四")
                .singleResult();
        //在局部变量和全局变量都有的情况下，这里取出来的就是局部变量
        Map<String,Object> processVariables = task.getProcessVariables();
        processVariables.put("num", 8);
        //设置了本地（局部）变量
        //taskService.setVariablesLocal(task.getId(), processVariables);

        //设置更新全局变量
        taskService.setVariables(task.getId(), processVariables);

    }

    @Test
    public void completeTask1(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processInstanceId("2501")
                //张三去处理这个任务
                .taskAssignee("李四")
                .singleResult();

        Map<String,Object> processVariables = task.getProcessVariables();
        Object num = processVariables.get("num");
//        类似于
//        if (num >= 3){
//            //......
//        } else {
//            //......
//        }

        taskService.complete(task.getId());
    }
}
