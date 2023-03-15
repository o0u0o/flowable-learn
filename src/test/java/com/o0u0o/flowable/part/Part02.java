package com.o0u0o.flowable.part;

import com.o0u0o.flowable.FlowableHiApplicationTests;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>流程启动</h1>
 * @author o0u0o
 * @date 2023/3/11 11:01 AM
 */
public class Part02 extends FlowableHiApplicationTests {

    /**
     * <h2>启动你那个流程实例</h2>
     * 启动一个流程实例
     */
    @Test
    public void testRunProcess(){
        ProcessEngine processEngine = configuration.buildProcessEngine();

        //1. 我们需要启动 RuntimeService 来启动流程实例
        RuntimeService runtimeService = processEngine.getRuntimeService();

        //2.构建流程变量(业务中的表单数据)
        Map<String, Object> variables = new HashMap<>();
        variables.put("employee", "张三");
        variables.put("nrOfHolidays", 3);
        variables.put("description", "工作累了！出去玩玩！");
        ProcessInstance holidayRequest = runtimeService.startProcessInstanceByKey("holidayRequest", variables);
        //3.启动流程实例
        //ProcessInstance holidayRequest = runtimeService.startProcessInstanceById("3:7503", variables);

        System.out.println("holidayRequest.getDeploymentId:" + holidayRequest.getDeploymentId());
        System.out.println("holidayRequest.getName:" + holidayRequest.getName());
        System.out.println("holidayRequest.getActivityId:" + holidayRequest.getActivityId());
    }

    /**
     * <h2>测试任务查询</h2>
     */
    @Test
    public void testQueryTask(){
        ProcessEngine processEngine = configuration.buildProcessEngine();

        TaskService taskService = processEngine.getTaskService();
        //指定查询的流程编号
        List<Task> tasks = taskService.createTaskQuery()
                .processDefinitionKey("holidayRequest")
                .taskAssignee("zhangsan").list();

        for (Task task: tasks){
            System.out.println("task.getProcessDefinitionId:" + task.getProcessDefinitionId());
            System.out.println("task.getName:" + task.getName());
            System.out.println("task.getAssignee:" + task.getAssignee());
            System.out.println("task.getDescription() = " + task.getDescription());
            System.out.println("task.getId() = " + task.getId());
            System.out.println("task.getTaskDefinitionKey() = " + task.getTaskDefinitionKey());
        }

    }

    /**
     * <h2>处理完成任务</h2>
     */
    @Test
    public void testCompleteTask(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("holidayRequest")
                .taskAssignee("zhangsan")
                .singleResult();

        //创建流程变量
        Map<String, Object> variables = new HashMap<>();
        variables.put("approved", false);

        //完成任务
        taskService.complete(task.getId(), variables);
    }



}
