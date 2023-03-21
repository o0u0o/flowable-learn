package com.o0u0o.flowable.part;
import com.o0u0o.flowable.FlowableHiApplicationTests;import org.flowable.engine.*;import org.flowable.engine.repository.Deployment;import org.flowable.engine.runtime.ProcessInstance;import org.flowable.task.api.Task;import org.junit.Test;import java.util.HashMap;import java.util.List;import java.util.Map; /**
 * <h1>候选人</h1>
 * ClassName: Part07
 * Description:
 * @Author o0u0o
 * @Date 2023/3/20 1:54 PM
 */
public class Part07 extends FlowableHiApplicationTests {
    /**
     * <h2>测试部署</h2>
     * 将流程模型部署到数据库
     */
    @Test
    public void deploy(){
        //1.获取 processEngine 对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
        //2.获取 RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3.关联要部署的文件 并且完成流程的部署操作
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("bpmn/请假流程-候选人.bpmn20.xml")
                .name("请假流程-候选人")
                //部署流程
                .deploy();

        System.out.println("deploy.getId(流程部署ID):" + deploy.getId());
        System.out.println("deploy.getName((流程部署名):" + deploy.getName());
    }

    /**
     * <h1>启动流程实例</h1>
     */
    @Test
    public void runProcess(){
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = engine.getRuntimeService();

        //流程定义ID
        String processDefinitionId = "holiday-candidate:1:4";

        //给流程定义中的uel表达式赋值
        Map<String, Object> variables = new HashMap<>();
        variables.put("candidate1", "张三");
        variables.put("candidate2", "李四");
        variables.put("candidate3", "王五");

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, variables);

        System.out.println("processInstance = " + processInstance);
        System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId());

    }

    /**
     * <h2>主任务查询</h2>
     * 根据登录的用户查询对应的可拾取的任务
     * 查询可拾取的任务
     */
    @Test
    public void queryTaskCandidate(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        //流程定义ID
        String processDefinitionKey = "holiday-candidate:1:4";
//        String processDefinitionId = "2501";
        String candidateUser = "张三";
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionId("holiday-candidate:1:4")
                //根据候选人查询
                .taskCandidateUser(candidateUser).list();

        taskList.stream().forEach(task -> {
            System.out.println("=============可拾取的任务：====================");
            System.out.println("task.getId() = " + task.getId());
            System.out.println("task.getName() = " + task.getName());
        });
    }

    /**
     * <h1>拾取任务</h1>
     * 1、候选人[张三]拾取任务
     * 2、一个任务只能被一个人拾取，被张三拾取了之后，就不能被李四拾取
     */
    @Test
    public void claimTaskCandidate(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        //流程定义ID
        String candidateUser = "李四";
        Task task = taskService.createTaskQuery()
                .processDefinitionId("holiday-candidate:1:4")
                //根据候选人查询
                .taskCandidateUser(candidateUser)
                .singleResult();
        //如果任务不为空
        if (task != null){
            //拾取对应的任务
            taskService.claim(task.getId(), candidateUser);
            System.out.println(candidateUser + "拾取任务[" + task.getName() + "]成功");
        } else {
            System.out.println("任务已经被拾取过了！");
        }
    }

    /**
     * <h2>退回任务</h2>
     * 1、如果张三拾取了任务，但是又不想做了，可以通归还任务将任务返回
     */
    @Test
    public void unClaimTaskCandidate() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        String taskAssignee = "张三";
        Task task = taskService.createTaskQuery()
                .processDefinitionId("holiday-candidate:1:4")
                .taskAssignee(taskAssignee).singleResult();
        if (task != null){
            //归还任务
            taskService.unclaim(task.getId());
            System.out.println(taskAssignee + "归还任务[" + task.getName() + "]成功");
        } else {
            System.out.println("没有这个任务，无法继续归还！");
        }
    }

    /**
     * <h2>任务交接</h2>
     * 1、如果我获取了任务，但是不想执行，那么我可以把任务交接给其他用户 指派
     */
    @Test
    public void assignTaskCandidate() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        String taskAssignee = "李四";
        Task task = taskService.createTaskQuery()
                .processDefinitionId("holiday-candidate:1:4")
                .taskAssignee(taskAssignee).singleResult();
        if (task != null){
            //归还任务 将该任务设置给王五
            taskService.setAssignee(task.getId(),"王五");
            System.out.println(taskAssignee + "指派任务[" + task.getName() + "]成功");
        } else {
            System.out.println("没有这个任务，无法继续指派！");
        }
    }

    @Test
    public void completeTask(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                //.processInstanceId("2501")
                .processDefinitionId("holiday-candidate:1:4")
                //张三去处理这个任务
                .taskAssignee("王五")
                .singleResult();
        if (task != null){
            //获取当前流程实例所有的 已有的流程变量
            taskService.complete(task.getId());
            System.out.println("完成了任务:" + task.getName());
        }

    }



}
