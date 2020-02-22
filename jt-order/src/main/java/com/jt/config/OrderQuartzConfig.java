package com.jt.config;

import com.jt.quartz.OrderQuartz;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderQuartzConfig {

    //定义任务详情
    @Bean
    public JobDetail orderJobDetail(){
        //指定job的名称和持久化保存任务
        return JobBuilder.newJob(OrderQuartz.class)
                         .withIdentity("orderQuartz")
                         .storeDurably()
                         .build();
    }

    //定义触发器
    @Bean
    public Trigger orderTrigger(){
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/1 * * * ?");
        return TriggerBuilder.newTrigger()
                             .forJob(orderJobDetail())
                             .withIdentity("orderQuartz")
                             .withSchedule(scheduleBuilder).build();
    }
}
