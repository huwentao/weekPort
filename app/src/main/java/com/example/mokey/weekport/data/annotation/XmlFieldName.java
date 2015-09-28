package com.example.mokey.weekport.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by mokey on 15-9-6.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XmlFieldName {
    String fieldName() default "";
    String value() default "";
    FieldType type() default FieldType.StringType;
}
