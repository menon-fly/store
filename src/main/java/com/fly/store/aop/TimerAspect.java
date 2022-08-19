package com.fly.store.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect //标记为切面类
public class TimerAspect {
    @Around("execution(* com.fly.store.service.impl.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable { //参数：表示连接点，目标方法对象
        long start = System.currentTimeMillis();
        Object proceed = pjp.proceed(); //调用目标方法
        long end = System.currentTimeMillis();
        System.out.println("耗时: "+ (end-start));
        return proceed;
    }
}
