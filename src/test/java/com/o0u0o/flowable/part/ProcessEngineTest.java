package com.o0u0o.flowable.part;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.junit.Test;
/**
 * <h1>流程引擎</h1>
 * @author o0u0o
 * @date 2023/3/14 8:48 PM
 */
public class ProcessEngineTest {

    @Test
    public void processEngine01(){
        //获取 ProcessEngineConfiguration 对象
        StandaloneProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();

        //配置相关的数据库连接信息
        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("stgczxgsdb@2021");
        configuration.setJdbcUrl("jdbc:mysql://222.85.202.49:4002/flowable-learn?serverTimezone=UTC&nullCatalogMeansCurrent=TRUE");

        //如果数据库中的表结构不存在就新建
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE);

        ProcessEngine processEngine = configuration.buildProcessEngine();
    }

    /**
     * 加载默认的配置文件
     */
    @Test
    public void processEngine02(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println("defaultProcessEngine = " + defaultProcessEngine);
    }

    @Test
    public void processEngine03(){
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("flowable.cfg.xml");
        ProcessEngine processEngine = configuration.buildProcessEngine();
        System.out.println("processEngine = " + processEngine);
    }

}
