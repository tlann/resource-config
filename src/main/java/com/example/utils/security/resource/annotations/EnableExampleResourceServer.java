package com.example.utils.security.resource.annotations;

import com.example.utils.security.resource.autoconfig.ResourceServerConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ResourceServerConfig.class})
public @interface EnableExampleResourceServer {
}
