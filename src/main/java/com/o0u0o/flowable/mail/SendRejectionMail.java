package com.o0u0o.flowable.mail;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * <h1></h1>
 * @author o0u0o
 * @date 2023/3/13 7:15 PM
 */
public class SendRejectionMail implements JavaDelegate {

    /**
     * flowable的一个触发器
     * @param execution
     */
    @Override
    public void execute(DelegateExecution execution) {
        //触发执行的逻辑，按照我们在流程中的定义，应该给被拒绝的员工发送通知的邮件
        System.out.printf("不好意思！你的请假申请被拒绝了。。。。请安心工作！");
    }
}
