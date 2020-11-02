package com.survive.IWillSurvive.model.entity;

import com.survive.IWillSurvive.annotation.AgeConstraint;
import com.survive.IWillSurvive.annotation.LocationConstraint;
import com.survive.IWillSurvive.dto.ItemInventoryDTO;
import com.survive.IWillSurvive.exception.AmountOfItemNotFoundInInventoryException;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Survivor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    @AgeConstraint
    private Integer age;

    @Column(nullable = false, length = 50)
    @Size(max = 50, min = 0)
    private String gender;

    @Column(nullable = false, length = 100, name = "last_location")
    @LocationConstraint
    private String lastLocation;

    @OneToOne
    @JoinColumn(name = "id_status")
    private Status status;

    @Column(nullable = false)
    private String inventory;

    @Transient
    private List<ItemInventoryDTO> inventoryList;

    public List<ItemInventoryDTO> getInventoryList() {
        if(inventoryList == null || inventoryList.isEmpty()) {
            inventoryList = new ArrayList<ItemInventoryDTO>();
            String[] inventorySplit = inventory.split(",|:");
            List<ItemInventoryDTO> listInventory = new ArrayList<ItemInventoryDTO>();
            for (int i = 0; i < inventorySplit.length / 2; i++) {
                ItemInventoryDTO item = new ItemInventoryDTO(inventorySplit[i * 2], Integer.valueOf(inventorySplit[i * 2 + 1]));
                inventoryList.add(item);
            }
        }
       return  inventoryList;
    };


    public void canTradeThisAmountOfItem(ItemInventoryDTO item){
        inventoryList = getInventoryList();
        boolean itemIsOnInventory=false;
        for(ItemInventoryDTO inventoryItem : inventoryList){
            if(inventoryItem.getDescription().equals(item.getDescription()) && inventoryItem.getAmount()>=item.getAmount()) {
                if( (inventoryItem.getAmount() - item.getAmount()) > 0) {
                    inventoryItem.setAmount(inventoryItem.getAmount() - item.getAmount());
                } else{
                    inventoryList.remove(inventoryItem);
                }
                itemIsOnInventory=true;
                break;
            }
        }
        if(!itemIsOnInventory){
            throw new AmountOfItemNotFoundInInventoryException();
        }

    }
    public void convertListToInventory(){
        inventory=new String();
        for(ItemInventoryDTO inventoryItem : inventoryList) {
            inventory=inventory+inventoryItem.getDescription()+":"+inventoryItem.getAmount()+",";
        }
        inventory=inventory.substring(0,inventory.length()-1);
    }

    private void validateAge(){
        if(this.getAge()>999)
            throw new NumberFormatException("Wow, it's over 999 years! No! You are a Zombie!");
        if(this.getAge()<=0)
            throw new NumberFormatException("A... Baby?");
    }


}
