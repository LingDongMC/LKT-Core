package com.github.lockoct.utils;

import com.github.lockoct.Main;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class CronJobUtil {
    public static void setJob(Class<? extends Job> jobClass, String name, String group, String crontab) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            CronTrigger trigger = TriggerBuilder.newTrigger().
                    withIdentity("LKTPluginTrigger", group).
                    withSchedule(CronScheduleBuilder.cronSchedule(crontab)).build();
            JobDetail detail = JobBuilder.newJob(jobClass).withIdentity(name, group).build();
            scheduler.scheduleJob(detail, trigger);
        } catch (SchedulerException e) {
            ColorLogUtil.logError(Main.plugin, I18nUtil.getText(Main.plugin, "pluginMsg.cronjobAddFailed"));
            e.printStackTrace();
        }
    }
}
