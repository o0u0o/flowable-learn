package com.o0u0o.flowable.part;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.junit.jupiter.api.Test;

/**
 * <h1>Part01</h1>
 * @author o0u0o
 * @date 2023/3/10 8:13 PM
 */
public class Part01 {

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
        configuration.setJdbcUrl("jdbc:mysql://localhost:3306/flowable-learn?serverTimezone=UTC");
        //如果数据库中标结构不存在，就新建
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);


        //通过ProcessEngineConfiguration构建我们需要的processEngine 对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
    }
}
