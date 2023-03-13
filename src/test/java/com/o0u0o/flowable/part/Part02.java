package com.o0u0o.flowable.part;

import com.o0u0o.flowable.FlowableHiApplicationTests;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>流程启动</h1>
 * @author o0u0o
 * @date 2023/3/11 11:01 AM
 */
public class Part02 extends FlowableHiApplicationTests {

    /**
     * <h2>启动你那个流程实例</h2>
     */
    @Test
    public void testRunProcess(){
        ProcessEngine processEngine = configuration.buildProcessEngine();

        //1. 我们需要启动RuntimeService来启动流程实例
        RuntimeService runtimeService = processEngine.getRuntimeService();

        //2.构建流程变量
        Map<String, Object> variables = new HashMap<>();
        variables.put("employee", "张三");
        variables.put("nrOfHolidays", 3);
        variables.put("description", "工作累了！出去玩玩！");
        ProcessInstance holidayRequest = runtimeService.startProcessInstanceByKey("请假流程", variables);

        System.out.println("holidayRequest.getDeploymentId:" + holidayRequest.getDeploymentId());


        //
    }
}
