import com.simulation.dao.mybatis.knowledge.TaskDao;
import com.simulation.model.knowledge.Task;
import com.simulation.vo.knowledge.TaskVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TaskTest extends BaseTest{
    @Autowired
    private TaskDao taskDao;
    @Test
    public void testFindCycleTask(){
        Task task = new Task();
        task.setType("日周期任务");
        List<TaskVo> taskVoList = taskDao.findCycleTask(task);
        if(!taskVoList.isEmpty()){
            System.out.println(taskVoList.size()+"-------------------------");
        }


    }
}
