package com.survive.IWillSurvive.annotation;

import com.survive.IWillSurvive.validate.SurvivorLocationValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SurvivorLocationValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LocationConstraint {
    String message() default "Please, put your location in format '(latitude,longitude)'!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
