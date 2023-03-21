package com.o0u0o.flowable.part;
import org.flowable.engine.IdentityService;import org.flowable.engine.ProcessEngine;import org.flowable.engine.ProcessEngines;import org.flowable.idm.api.Group;import org.flowable.idm.api.User;import org.junit.Test; /**
 * <h1>用户和组的维护</h1>
 * ClassName: Part08
 * Description:
 * @Author o0u0o
 * @Date 2023/3/20 5:04 PM
 */
public class Part08 {

    /**
     * <h1>维护用户</h1>
     */
    @Test
    public void createUser(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //通过identityService完成相关的用户和组的管理
        IdentityService identityService = processEngine.getIdentityService();
        String userId = "003";
        String firstName = "赵";
        String lastName = "雨";
        String email = "zhaoyu@163.com";

        User user = identityService.newUser(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        identityService.saveUser(user);
    }

    /**
     * <h2>创建用户组</h2>
     */
    @Test
    public void createGroup(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();
        //创建group对象，并指定相关的信息
        Group group = identityService.newGroup("group1");
        group.setName("销售部");
        group.setType("type1");
        //创建Group对应的表结构数据
        identityService.saveGroup(group);
    }
}
