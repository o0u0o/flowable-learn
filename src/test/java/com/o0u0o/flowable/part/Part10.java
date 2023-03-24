package com.o0u0o.flowable.part;
import org.flowable.engine.ProcessEngine;import org.flowable.engine.ProcessEngines;import org.flowable.engine.RepositoryService;import org.flowable.engine.RuntimeService;import org.flowable.engine.repository.Deployment;import org.flowable.engine.runtime.ProcessInstance;import org.junit.Test;import java.util.HashMap;import java.util.Map; /**
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
        variables.put("num", 2);

        ProcessInstance processInstance = runtimeService.startProcessInstanceById("holiday-exclusive:1:17504", variables);

        System.out.println("processInstance = " + processInstance);
        System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId());

    }
}
