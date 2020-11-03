package com.survive.IWillSurvive.validate;

import com.survive.IWillSurvive.annotation.LocationConstraint;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class SurvivorLocationValidator implements ConstraintValidator<LocationConstraint, String>{

    @Override
    public void initialize(LocationConstraint localConstraint){}


    /*
        Regex explanation:
        " -? – this part identifies if the given number is negative, the dash “–” searches for dash literally and the
            question mark “?” marks its presence as an optional one
        \d+ – this searches for one or more digits
        (\.\d+)? – this part of regex is to identify float numbers. Here we're searching for one or more digits followed
            by a period. The question mark, in the end, signifies that this complete group is optional."
            Reference: https://www.baeldung.com/java-check-string-number
    */
    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public boolean isValid(String value, ConstraintValidatorContext context){
        boolean locationIsNotBetweenParenthesis = value.charAt(value.length()-1) != ')'
                || value.charAt(0) != '(' ;
        boolean locationHaveMoreThenHundredCharacters = value.length() > 100;
        String values = value.substring(1,value.length()-1);
        String[] latitudeLongitude = values.split(",");
        boolean coordinatesNumeric;
        if(latitudeLongitude.length != 2) {
            coordinatesNumeric = false;
        } else{
            coordinatesNumeric=(isNumeric(latitudeLongitude[0]) && isNumeric(latitudeLongitude[1]));
        }
        return !(locationIsNotBetweenParenthesis || locationHaveMoreThenHundredCharacters) && coordinatesNumeric;
    }
}
