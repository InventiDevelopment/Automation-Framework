package cz.inventi.sample.framework.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ConfigFiles {
    String appConfig() default "config/appConfig.yml";
    String driverConfig() default "config/driverConfig.yml";
}
