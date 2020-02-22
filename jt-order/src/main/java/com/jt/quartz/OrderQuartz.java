package com.jt.quartz;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.OrderMapper;
import com.jt.pojo.Order;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@Component
public class OrderQuartz extends QuartzJobBean {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 目的:每隔1分钟 将超时订单状态修改1改为6
     * 条件: now - created > 30分钟
     * 		 status = 1
     * sql:
     * 		update tb_order set status=6,updated=#{date}
     * 		where status=1 and 	created < now-30分钟
     */
    @Override
    @Transactional
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取当前时间的日历对象
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,-30); //Calendar.MINUTE：按分钟计算
        Date timeout = calendar.getTime();  //超时时间

        Order order = new Order();
        order.setStatus(6).setUpdated(new Date());
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("status",1).lt("created",timeout);
        orderMapper.update(order,updateWrapper);
        System.out.println("定时任务执行成功！");

    }
}
