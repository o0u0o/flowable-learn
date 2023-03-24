package com.o0u0o.flowable.part;
import org.flowable.engine.IdentityService;import org.flowable.engine.ProcessEngine;import org.flowable.engine.ProcessEngines;import org.flowable.idm.api.Group;import org.flowable.idm.api.User;import org.junit.Test;import java.util.List; /**
 * <h1>用户和组的维护</h1>
 * ClassName: Part08
 * Description:
 * @Author o0u0o
 * @Date 2023/3/20 5:04 PM
 */
public class Part08 {

    /**
     * <h2>维护用户</h2>
     * 会保存在ACT_ID_USER表中
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

    @Test
    public void queryUser(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //通过identityService完成相关的用户和组的管理
        IdentityService identityService = processEngine.getIdentityService();
        List<User> list = identityService.createUserQuery().list();
        list.stream().forEach(user -> {
            System.out.println("user.getId() = " + user.getId());
            System.out.println("user.getFirstName() = " + user.getFirstName());
            System.out.println("user.getLastName() = " + user.getLastName());
            System.out.println("===========");
        });
    }

    /**
     * <h2>创建用户组</h2>
     * 数据会保存在ACT_ID_GROUP表中
     */
    @Test
    public void createGroup(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();
        //创建group对象，并指定相关的信息
        Group group = identityService.newGroup("group2");
        group.setName("技术部");
        group.setType("type2");
        //创建Group对应的表结构数据
        identityService.saveGroup(group);
    }

    /**
     * <h2>将用户分配给对应的Group</h2>
     */
    @Test
    public void userGroup(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();
        //根据组的编号找到对应的Group对象
        Group group = identityService.createGroupQuery().groupId("group1").singleResult();
        //
        List<User> userList = identityService.createUserQuery().list();

        //将查询出来的3个用户都分配给销售部
        userList.stream().forEach(u -> {
            //将用户分配给组
            identityService.createMembership(u.getId(), group.getId());
        });
    }

    /**
     * <h2>查询该组下有哪些人</h2>
     */
    @Test
    public void queryGroupUsers(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();

        List<User> userList = identityService.createUserQuery().memberOfGroup("group1").list();
        userList.stream().forEach(user -> {
            System.out.println("user.getId() = " + user.getId());
            System.out.println("user.getFirstName() = " + user.getFirstName());
            System.out.println("user.getLastName() = " + user.getLastName());
            System.out.println("==========");
        });
    }

}
