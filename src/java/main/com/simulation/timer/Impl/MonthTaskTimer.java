package com.simulation.timer.Impl;

import com.simulation.dao.mybatis.knowledge.TaskDao;
import com.simulation.model.knowledge.Task;
import com.simulation.timer.TaskTimer;
import com.simulation.vo.knowledge.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class MonthTaskTimer implements TaskTimer {
    @Autowired
    private TaskDao taskDao;
    @Scheduled(cron = "0 0 0 1 * ?")
    @Override
    public void newTask() {
        System.out.println("每月执行一次------------");
        Task task = new Task();
        task.setType("日周期任务");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH,1);
        List<TaskVo> cycleList = taskDao.findCycleTask(task);
        for (TaskVo taskVo:cycleList){
            TaskVo vo = new TaskVo();
            vo.setId(taskVo.getId());
            vo.setAvailable(false);
            taskDao.editTask(vo);
            taskVo.setId(null);
            taskVo.setBeginTime( new Date());
            taskVo.setFinishTime(cal.getTime());
            taskDao.addTaskIntoPlan(taskVo);
        }
    }
}
