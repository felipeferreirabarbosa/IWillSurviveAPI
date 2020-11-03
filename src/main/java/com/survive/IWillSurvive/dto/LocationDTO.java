package com.survive.IWillSurvive.dto;

import com.survive.IWillSurvive.annotation.LocationConstraint;
import lombok.Data;

@Data
public class LocationDTO {
    @LocationConstraint
    private String location;
}
