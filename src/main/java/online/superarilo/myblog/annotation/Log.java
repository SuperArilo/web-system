package online.superarilo.myblog.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Log {

    /**
     *
     */
    String value() default "";


    /**
     * 是否需要持久化
     */
    boolean isPersist() default true;
}
