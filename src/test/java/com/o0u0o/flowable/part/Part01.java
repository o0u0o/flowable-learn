package com.o0u0o.flowable.part;

import com.o0u0o.flowable.FlowableHiApplicationTests;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.junit.Before;
import org.junit.Test;

/**
 * <h1>Part01</h1>
 * @author o0u0o
 * @date 2023/3/10 8:13 PM
 */
public class Part01 extends FlowableHiApplicationTests {

    /**
     * <h2>获取流程引擎对象</h2>
     */
    @Test
    public void testProcessEngine(){
        //获取 ProcessEngineConfiguration 对象
        ProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();

        //配置相关的数据库连接信息(后期放到属性文件去)
        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("Mysql8.0");
        configuration.setJdbcUrl("jdbc:mysql://localhost:3306/flowable-learn?serverTimezone=UTC&nullCatalogMeansCurrent=TRUE");
        //如果数据库中标结构不存在，就新建
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);


        //通过ProcessEngineConfiguration构建我们需要的processEngine 对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
    }

    /**
     * <h2>测试部署</h2>
     */

    @Test
    public void testDeploy(){
        //1.获取 processEngine 对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
        //2.获取 RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3.关联要部署的文件 并且完成流程的部署操作
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("holiday-request.bpmn20.xml")
                .name("请假流程")
                //部署流程
                .deploy();

        System.out.println("deploy.getId:" + deploy.getId());
        System.out.println("deploy.getName:" + deploy.getName());
    }

    /**
     * <h2>查询流程定义的信息</h2>
     */
    @Test
    public void testDeployQuery(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        ProcessDefinition processDefinition = processDefinitionQuery.deploymentId("10001").singleResult();
        System.out.println("DefId:" + processDefinition.getId());
        System.out.println("DefName:" + processDefinition.getName());
        System.out.println("DefKey：" + processDefinition.getKey());
    }

    /**
     * <h2>删除流程定义</h2>
     */
    @Test
    public void testDeleteDeploy(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //删除部署的流程，第一个参数是id 如果部署的流程启动了就不允许删除了
        //repositoryService.deleteDeployment("1");

        //第二个参数是true 级联删除，如果流程启动了，相关的任务一并会删除掉
        repositoryService.deleteDeployment("25001", true);
    }
}
