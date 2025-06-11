package com.qfleaf.cosmosimserver.log.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Log {
    String opsName() default "";
}
