package com.o0u0o.flowable.listener;
import org.flowable.task.service.delegate.DelegateTask;import org.flowable.task.service.delegate.TaskListener; /**
 * <h1>自定义的监听器</h1>
 * 
 * @author o0u0o
 * @date 2023/3/18 4:27 PM
 */
public class MyTaskListener implements TaskListener  {

    /**
     * <h2>监听器触发的方法</h2>
     * 可以通过监听器的方式分配
     * @param delegateTask
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("MyTaskListener触发了..." + delegateTask.getName());
        //流程设计器的名称
        if ("请假流程".equals(delegateTask.getName())
                && "create".equals(delegateTask.getEventName())){
            //满足触发条件的事件，那么我们就来设置 assignee
            delegateTask.setAssignee("小明");
        }
        else {
            delegateTask.setAssignee("小李");
        }
    }
}
