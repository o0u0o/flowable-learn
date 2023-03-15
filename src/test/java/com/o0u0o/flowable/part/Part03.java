package com.o0u0o.flowable.part;

import com.o0u0o.flowable.FlowableHiApplicationTests;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.history.HistoricActivityInstance;
import org.junit.Test;

import java.util.List;

/**
 * <h1>历史信息</h1>
 * @author o0u0o
 * @date 2023/3/13 7:35 PM
 */
public class Part03 extends FlowableHiApplicationTests {

    /**
     * <h2>获取流程任务的历史数据</h2>
     */
    @Test
    public void testHistory(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();

        String proInsId = "holidayRequest:1:3";

        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(proInsId)
                //查询的历史记录的状态是完成的
                .finished()
                //指定排序的字段
                .orderByHistoricActivityInstanceEndTime()
                //升序还是降序
                .asc().list();

        for (HistoricActivityInstance history : list) {
            System.out.println("history.getActivityId() = " + history.getActivityId());
            System.out.println("history.getAssignee() = " + history.getAssignee());
        }



    }
}
