package com.o0u0o.flowable.part;
import com.o0u0o.flowable.FlowableHiApplicationTests;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.junit.Test;

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
                .addClasspathResource("bpmn/请假流程.bpmn20.xml")
                .name("请假流程")
                //部署流程
                .deploy();

        System.out.println("deploy.getId:" + deploy.getId());
        System.out.println("deploy.getName:" + deploy.getName());

    }
}
