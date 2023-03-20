package com.o0u0o.flowable;

import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FlowableHiApplicationTests {

    @Test
    void contextLoads() {
    }


    public ProcessEngineConfiguration configuration = null;

    @Before
    public void before(){
        //获取 ProcessEngineConfiguration 对象
        configuration = new StandaloneProcessEngineConfiguration();

        //配置相关的数据库连接信息(后期放到属性文件去)
        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("Mysql8.0");
        configuration.setJdbcUrl("jdbc:mysql://localhost:3306/flowable-learn?serverTimezone=UTC&nullCatalogMeansCurrent=TRUE");

//        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
//        configuration.setJdbcUsername("root");
//        configuration.setJdbcPassword("Mysql8.0");
//        configuration.setJdbcUrl("jdbc:mysql://localhost:3306/px_demand_manage_test?serverTimezone=UTC&nullCatalogMeansCurrent=TRUE");

        //如果数据库中标结构不存在，就新建
        configuration = configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
    }


}
