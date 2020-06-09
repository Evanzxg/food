package org.evan.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: food
 * @description: 借助日期监控service执行时间
 * @author: Evan
 * @create: 2020-05-28 17:07
 **/
@Aspect
@Component
public class ServiceLogAspect {
    /**
     * AOP通知：
     * 1.前置通知：在方法调用之前执行
     * 2.后置通知：在方法正常调用之后执行
     * 3.环绕通知：在方法调用之前和之后，都分别可以执行的通知
     * 4.异常通知：如果在方法调用过程中发生异常，则通知
     * 5.最终通知：在方法调用之后执行
     */
    public static final Logger log = LoggerFactory.getLogger(ServiceLogAspect.class);

    public static final long ERROR_TIME = 3000;
    public static final long WARN_TIME = 2000;

    /**
     * 切面表达式：
     * execution 代表所要执行的表达式主体
     * 第一处 * 代表方法返回类型 * 代表所有类型
     * 第二处 包名代表aop监控的类所在的包
     * 第三处 。。代表该包以及其子包下的所有类方法
     * 第四处 * 代表类名，*代表所有类
     * 第五处 *（..） *代表类中的方法名，（..）表示方法中的任何参数
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "execution(* org.evan.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("====== 开始执行 {}.{} ======",joinPoint.getTarget().getClass(),joinPoint.getSignature().getName());

        long beginTime = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long endTime = System.currentTimeMillis();

        long taskTime = endTime - beginTime;

        if(taskTime > ERROR_TIME){
            log.error("====== 执行结束 耗时：{} 毫秒 ======",taskTime);
        }else if(taskTime > WARN_TIME){
            log.warn("====== 执行结束 耗时：{} 毫秒 ======",taskTime);
        }else{
            log.info("====== 执行结束 耗时：{} 毫秒 ======",taskTime);
        }
        return proceed;
    }
}
