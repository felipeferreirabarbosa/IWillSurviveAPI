package com.survive.IWillSurvive.validate;


import com.survive.IWillSurvive.annotation.AgeConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SurvivorAgeValidator implements ConstraintValidator<AgeConstraint, Integer> {

    public boolean isValid(Integer value, ConstraintValidatorContext context){
            return !(value<=0||value>999);
        }
}
