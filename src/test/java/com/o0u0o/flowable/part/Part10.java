package com.o0u0o.flowable.part;
import org.flowable.engine.*;import org.flowable.engine.repository.Deployment;import org.flowable.engine.runtime.ProcessInstance;import org.flowable.task.api.Task;import org.junit.Test;import java.util.HashMap;import java.util.Map; /**
 * <h1>排他网关</h1>
 * ClassName: Part10
 * Description:
 * @Author o0u0o
 * @Date 2023/3/24 9:59 AM
 */
public class Part10 {

    @Test
    public void deploy(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("bpmn/请假流程-排他网关.bpmn20.xml")
                .name("请假流程-排他网关")
                .deploy();

        System.out.println("deploy.getId() = " + deploy.getId());
        System.out.println("deploy.getName() = " + deploy.getName());
    }

    /**
     * <h1>启动流程实例</h1>
     */
    @Test
    public void runProcess(){
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = engine.getRuntimeService();


        //给流程定义中的uel表达式赋值
        Map<String, Object> variables = new HashMap<>();
        //请假天数量
        variables.put("num", 4);

        ProcessInstance processInstance = runtimeService.startProcessInstanceById("holiday-exclusive:1:4", variables);

        System.out.println("processInstance = " + processInstance);
        System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId());
    }

    @Test
    public void completeTask(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processDefinitionId("holiday-exclusive:1:4")
                //张三去处理这个任务
//                .taskAssignee("zhangsan")
                .taskAssignee("zhaoliu")
                .singleResult();

        if (task != null){
            //获取当前流程实例所有的 已有的流程变量
            taskService.complete(task.getId());
            System.out.println("完成了任务:" + task.getName());
        }

    }
}
