package com.survive.IWillSurvive.dto;

import lombok.Data;

@Data
public class ItemInventoryDTO {

    private String description;
    private Integer amount;

    public ItemInventoryDTO(String description, Integer amount) {
        this.description = description;
        this.amount = amount;
    }
}
