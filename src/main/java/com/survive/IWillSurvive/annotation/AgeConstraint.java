package com.survive.IWillSurvive.annotation;

import com.survive.IWillSurvive.validate.SurvivorAgeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SurvivorAgeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AgeConstraint {
    String message() default "Please, put a valid age!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
