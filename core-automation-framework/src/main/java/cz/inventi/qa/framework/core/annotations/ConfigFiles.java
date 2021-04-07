package cz.inventi.qa.framework.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ConfigFiles {
    String appsConfig() default "config/appsConfig.yml";
    String driverConfig() default "config/webDriverConfig.yml";
}
