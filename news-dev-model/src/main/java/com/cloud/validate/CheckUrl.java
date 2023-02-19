package com.cloud.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 22:41 2022/11/14
 * @Modified by:ycy
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckUrlValidate.class)
public @interface CheckUrl {

    String message() default "Url不正确";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
