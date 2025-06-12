package com.qfleaf.cosmosimserver.log.supports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfleaf.cosmosimserver.log.annotations.Log;
import com.qfleaf.cosmosimserver.log.domain.OpsLogEntity;
import com.qfleaf.cosmosimserver.log.persistence.LogWriteRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
@Order
@RequiredArgsConstructor
public class AnnotationLogAspect {
    private final ObjectMapper objectMapper;
    private final LogWriteRepository logWriteRepo;

    @Pointcut("@annotation(com.qfleaf.cosmosimserver.log.annotations.Log)")
    public void annotationLayer() {
    }

    @Around("annotationLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        // 获取注解信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Log logAnnotation = signature.getMethod().getAnnotation(Log.class);
        assert logAnnotation != null;
        String opsName = logAnnotation.opsName();
        log.debug("请求开始: 操作={}, IP={}, 参数={}",
                opsName,
                request.getRemoteAddr(),
                Arrays.toString(joinPoint.getArgs()));
        try {
            // 执行
            Object result = joinPoint.proceed();
            String resultJson = objectMapper.writeValueAsString(result);
            log.debug("请求结束，结果={}", resultJson);
            // 持久化日志
            OpsLogEntity logEntity = OpsLogEntity.builder()
                    .ipAddress(request.getRemoteAddr())
                    .opsName(opsName)
                    // todo 优化请求参数的日志记录结构
                    .args(Arrays.toString(joinPoint.getArgs()))
                    .result(resultJson)
                    .opsTime(Instant.now())
                    .build();
            persistenceLog(logEntity);
            return result;
        } catch (Exception e) {
//            log.error("执行异常: 操作名称={}，异常信息={}", opsName, e.getMessage(), e);
            // 持久化错误日志
            String errorJson = objectMapper.writeValueAsString(e);
            OpsLogEntity logEntity = OpsLogEntity.builder()
                    .ipAddress(request.getRemoteAddr())
                    .opsName(opsName)
                    .args(Arrays.toString(joinPoint.getArgs()))
                    .result(errorJson)
                    .opsTime(Instant.now())
                    .build();
            persistenceLog(logEntity);
            throw e;
        }
    }

    @Async
    protected void persistenceLog(OpsLogEntity logEntity) {
        logWriteRepo.insert(logEntity);
    }
}
