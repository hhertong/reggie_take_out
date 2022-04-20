package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.service.OrderService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 订单
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("page")
    public R<Page> page(int page, int pageSize, String number, String beginTime,String endTime){


        //构造分页构造器
        Page<Orders> pageInfo=new Page<>(page,pageSize);

        log.info("page {}",pageInfo);

        //构造条件构造器
        LambdaQueryWrapper<Orders> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        //添加查询条件
        lambdaQueryWrapper.like(number!=null,Orders::getNumber,number);
        //添加选取某一个时间段条件
        lambdaQueryWrapper.ge(beginTime!=null,Orders::getOrderTime,beginTime);
        lambdaQueryWrapper.lt(endTime!=null,Orders::getOrderTime,endTime);
        //添加排序条件
        lambdaQueryWrapper.orderByAsc(Orders::getNumber);

        //分页查询
        orderService.page(pageInfo,lambdaQueryWrapper);

        return R.success(pageInfo);
    }
}