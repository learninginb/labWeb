package com.simulation.timer.Impl;

import com.simulation.dao.mybatis.knowledge.TaskDao;
import com.simulation.model.knowledge.Task;
import com.simulation.timer.TaskTimer;
import com.simulation.vo.knowledge.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DayTaskTimer implements TaskTimer {
    @Autowired
    private TaskDao taskDao;
    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    public void newTask() {
        System.out.println("每日执行一次------------");
        Task task = new Task();
        task.setType("日周期任务");
        List<TaskVo> cycleList = taskDao.findCycleTask(task);
        for (TaskVo taskVo:cycleList){
            TaskVo vo = new TaskVo();
            vo.setId(taskVo.getId());
            vo.setAvailable(false);
            taskDao.editTask(vo);
            taskVo.setId(null);
            taskVo.setAvailable(true);
            taskVo.setBeginTime( new Date(System.currentTimeMillis()));
            taskVo.setFinishTime(new Date(System.currentTimeMillis()+1000*24*60*60));
            taskDao.addTaskIntoPlan(taskVo);
        }
    }
}
