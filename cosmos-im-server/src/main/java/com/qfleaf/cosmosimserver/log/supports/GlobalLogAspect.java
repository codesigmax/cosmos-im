package com.qfleaf.cosmosimserver.log.supports;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
@Component
public class GlobalLogAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalLogAspect.class);
    
    // 定义切点表达式：所有Controller层方法
    @Pointcut("execution(* com.qfleaf.cosmosimserver..interfaces.rest.*.*(..))")
    public void controllerLayer() {}
    
    // 环绕通知：记录方法执行时间
    @Around("controllerLayer()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        
        // 记录请求信息
        logger.info("请求开始: URI={}, method={}, IP={}, 参数={}",
                request.getRequestURI(),
                request.getMethod(),
                request.getRemoteAddr(),
                Arrays.toString(joinPoint.getArgs()));
        
        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            
            // 记录响应信息
            logger.info("请求结束: URI={}, 耗时={}ms, 返回结果={}",
                    request.getRequestURI(),
                    (endTime - startTime),
                    result);
            
            return result;
        } catch (Exception e) {
            logger.error("请求异常: URI={}, 异常信息={}",
                    request.getRequestURI(),
                    e.getMessage());
            throw e;
        }
    }
}
