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
public class WeekTaskTimer implements TaskTimer {
    @Autowired
    private TaskDao taskDao;
    @Scheduled(cron = "0 0 0 ? * MON")
    @Override
    public void newTask() {
        System.out.println("每周执行一次------------");
        Task task = new Task();
        task.setType("周周期任务");
        List<TaskVo> cycleList = taskDao.findCycleTask(task);
        for (TaskVo taskVo:cycleList){
            TaskVo vo = new TaskVo();
            vo.setId(taskVo.getId());
            vo.setAvailable(false);
            taskDao.editTask(vo);
            taskVo.setId(null);
            taskVo.setBeginTime( new Date(System.currentTimeMillis()));
            taskVo.setFinishTime(new Date(System.currentTimeMillis()+1000*24*60*60*7));
            taskDao.addTaskIntoPlan(taskVo);
        }
    }

}
