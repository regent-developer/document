
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.stereotype.Component;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

/**
 * 通过注解组件ScheduledAnnotationBeanPostProcessor ，获取到所有的通过注解@Scheduled(包括@EnableScheduling)注解启动的ScheduledTask集合，然后在集合里面遍历查找ScheduledTask对应的包名，以此判断是否关闭。
 */
@EnableScheduling
@Component
public class TimerFileWatch {
	@Autowired
    private ScheduledAnnotationBeanPostProcessor mBeanPostProcessor;
	

	@Scheduled(fixedRate = 320 * 1)
	public void WatchFileChange() {
		stopTask();
	}
	
	private void stopTask() {
		Set<ScheduledTask> tasks = mBeanPostProcessor.getScheduledTasks();
		Iterator<ScheduledTask> it = tasks.iterator();
		ScheduledTask scheduledTask = null;
		String strTmp = null;
		
		while (it.hasNext()) {
			scheduledTask = it.next();
			if (scheduledTask == null) {
				continue;
			}
			System.out.println("scheduledTask = " + scheduledTask.toString());
			strTmp = scheduledTask.toString();
			if (strTmp != null && strTmp.equals("TimerFileWatch.WatchFileChange")) {
				scheduledTask.cancel();
			}
        }
	}
}

