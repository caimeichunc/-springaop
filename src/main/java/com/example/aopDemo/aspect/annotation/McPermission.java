package com.example.aopDemo.aspect.annotation;


import java.lang.annotation.*;

/**
 * @author Mc
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface McPermission {

    String[] value() default {};
}
