package com.o0u0o.flowable.part;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;
/**
 * <h1></h1>
 * @author o0u0o
 * @date 2023/3/15 9:59 PM
 */
public class Part04 {

    /**
     * <h2>流程的挂起与激活</h2>
     */
    @Test
    public void testSuspended(){
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = engine.getRepositoryService();

        //获取对应的流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId("holiday:1:4")
                .singleResult();

        //获取当前的流程定义的状态信息
        boolean suspended = processDefinition.isSuspended();
        if (suspended){
            //当前的流程被 挂起了
            //激活流程
            System.out.println("激活流程：" + processDefinition.getId() + processDefinition.getName());
            repositoryService.activateProcessDefinitionById("holiday:1:4");
        }

        else {
            //当前的流程是激活状态
            System.out.println("挂起流程：" + processDefinition.getId() + processDefinition.getName());
            //我们可以挂起当前的流程
            repositoryService.suspendProcessDefinitionByKey("holiday:1:4");
        }
    }

}
