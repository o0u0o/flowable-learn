package com.o0u0o.flowable.demand;
import com.o0u0o.flowable.common.utils.FlowableUtils;import org.flowable.bpmn.model.FlowElement;import org.flowable.bpmn.model.Process;import org.flowable.bpmn.model.UserTask;import org.flowable.engine.*;import org.flowable.engine.repository.Deployment;import org.flowable.engine.repository.ProcessDefinition;import org.flowable.engine.runtime.ProcessInstance;import org.flowable.task.api.Task;import org.flowable.task.api.history.HistoricTaskInstance;import org.junit.Test;import java.util.*; /**
 * <h1>需求管理</h1>
 * ClassName: Test01
 * Description:
 * @Author o0u0o
 * @Date 2023/3/24 10:28 AM
 */
public class Test01 {

    /**
     * <h2>流程部署</h2>
     * 将流程模型部署到数据库
     */
    @Test
    public void deploy(){
        //1.获取 processEngine 对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2.获取 RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3.关联要部署的文件 并且完成流程的部署操作
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("bpmn/需求提报-网关.bpmn20.xml")
                .name("需求提报-网关")
                //部署流程
                .deploy();

        System.out.println("deploy.getId(流程部署ID):" + deploy.getId());
        System.out.println("deploy.getName((流程部署名):" + deploy.getName());
        System.out.println("deploy.getParentDeploymentId() = " + deploy.getParentDeploymentId());
    }

    /**
     * <h1>启动流程实例</h1>
     */
    @Test
    public void runProcess(){
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = engine.getRuntimeService();

        //流程定义ID
        String processDefinitionId = "demand:1:4";

        //给流程定义中的uel表达式赋值
        Map<String, Object> variables = new HashMap<>();
        variables.put("INITIATOR", "网省市场部-小王");
        variables.put("assignee_node1", "总部业务处室-蔡徐坤");
        variables.put("assignee_node2", "总部技术部-倪大强");
        variables.put("assignee_node3", "需求研发负责人-卢本伟");
        variables.put("assignee_node4", "网省市场部-小王");

        //设置角色 是技术部不走总部业务处室
        variables.put("role", true);

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, variables);

        System.out.println("processInstance = " + processInstance);
        System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId());
    }

    @Test
    public void completeTask(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processDefinitionId("demand:1:4")
                //网省市场部-小王 提报了任务
                .taskAssignee("网省市场部-小王")
                .singleResult();

        task.setDescription("提报了任务");
        task.setCategory("需求提报");

        if (task != null){
            //获取当前流程实例所有的 已有的流程变量
            taskService.complete(task.getId());
            System.out.println("完成了任务:" + task.getName());
        }
    }

    @Test
    public void setVariables(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        //给流程定义的uel表达式赋值
        Map<String, Object> variables = new HashMap<>();
        variables.put("role",  false);
        runtimeService.setVariables("", variables);
    }

    @Test
    public void completeTask1(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        HistoryService historyService = processEngine.getHistoryService();

        Task task = taskService.createTaskQuery()
                .processDefinitionId("demand:1:4")
                //网省市场部-小王 提报了任务
                .taskAssignee("需求研发负责人-倪大强")
                .singleResult();

        task.setDescription("总部技术部审核");
        task.setCategory("审核");

        // 获取流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(task.getProcessDefinitionId()).singleResult();

        // 获取所有节点信息
        Process process = repositoryService.getBpmnModel(processDefinition.getId()).getProcesses().get(0);

        // 获取全部节点列表，包含子节点
        Collection<FlowElement> allElements = FlowableUtils.getAllElements(process.getFlowElements(), null);
        // 获取当前任务节点元素
        FlowElement source = null;
        if (allElements != null) {
            for (FlowElement flowElement : allElements) {
                // 类型为用户节点
                if (flowElement.getId().equals(task.getTaskDefinitionKey())) {
                    // 获取节点信息
                    source = flowElement;
                }
            }
        }


        // 深度优先算法思想：延边迭代深入
        List<UserTask> parentUserTaskList = FlowableUtils.iteratorFindParentUserTasks(source, null, null);
        if (parentUserTaskList == null || parentUserTaskList.size() == 0) {
            throw new RuntimeException("当前节点为初始任务节点，不能驳回");
        }

        // 获取活动 ID 即节点 Key
        List<String> parentUserTaskKeyList = new ArrayList<>();
        parentUserTaskList.forEach(item -> parentUserTaskKeyList.add(item.getId()));
        // 获取全部历史节点活动实例，即已经走过的节点历史，数据采用开始时间升序
        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .orderByHistoricTaskInstanceStartTime().asc().list();

        historicTaskInstanceList.stream().forEach(historicTaskInstance -> {
            System.out.println("====================");
            System.out.println("Id:" + historicTaskInstance.getId());
            System.out.println("Name："+ historicTaskInstance.getName());
            System.out.println("审核人：" + historicTaskInstance.getAssignee());
            System.out.println("Description:" + historicTaskInstance.getDescription());

        });


//        if (task != null){
//            //获取当前流程实例所有的 已有的流程变量
//            taskService.complete(task.getId());
//            System.out.println("完成了任务:" + task.getName());
//        }
    }
}
