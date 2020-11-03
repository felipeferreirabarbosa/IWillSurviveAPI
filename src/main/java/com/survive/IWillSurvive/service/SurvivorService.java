package com.survive.IWillSurvive.service;

import com.survive.IWillSurvive.dto.ItemInventoryDTO;
import com.survive.IWillSurvive.dto.LocationDTO;
import com.survive.IWillSurvive.exception.InvalidLocationException;
import com.survive.IWillSurvive.exception.SurvivorNotFoundException;
import com.survive.IWillSurvive.model.entity.ItemsPoints;
import com.survive.IWillSurvive.model.entity.Status;
import com.survive.IWillSurvive.model.entity.Survivor;
import com.survive.IWillSurvive.model.repository.ItemsPointsRepository;
import com.survive.IWillSurvive.model.repository.StatusRepository;
import com.survive.IWillSurvive.model.repository.SurvivorRepository;
import com.survive.IWillSurvive.validate.SurvivorLocationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class SurvivorService {

    private final SurvivorRepository survivorRepository;
    private final StatusRepository statusRepository;
    private final ItemsPointsRepository itemsPointsRepository;

    @Autowired
    public SurvivorService(SurvivorRepository survivorRepository, StatusRepository statusRepository,  ItemsPointsRepository itemsPointsRepository) {
        this.survivorRepository = survivorRepository;
        this.statusRepository = statusRepository;
        this.itemsPointsRepository = itemsPointsRepository;
    }

    public Survivor attLocation(Integer idSurvivor, LocationDTO locationDTO){
        Survivor survivor = survivorRepository.findById(idSurvivor).orElseThrow(SurvivorNotFoundException::new);
        survivor.setLastLocation(locationDTO.getLocation());
        return survivorRepository.save(survivor);
    }

    public Survivor create(Survivor survivor){
        survivor.setStatus(Status.builder().infected(false).build());
        statusRepository.save(survivor.getStatus());
        return survivorRepository.save(survivor);
    }

    public String itemsPerSurvivor(){
        List<Survivor> listSurvivors = survivorRepository.findAllNotInfected();
        Integer numberOfSurvivors = listSurvivors.size();
        List<ItemInventoryDTO> itemInventory = new ArrayList<ItemInventoryDTO>();

        for(Survivor survivor : listSurvivors){
            itemInventory.addAll(survivor.getInventoryList());
        }

        itemInventory.sort(Comparator.comparing(ItemInventoryDTO::getDescription));

        groupByItem(itemInventory);

        for (ItemInventoryDTO item : itemInventory) {
            item.setAmount(item.getAmount()/numberOfSurvivors);
        }
        return convertListToInventoryAvg(itemInventory);
    }

    private void groupByItem(List<ItemInventoryDTO> itemInventory) {
        List<ItemInventoryDTO> itemInventoryAux = new ArrayList<ItemInventoryDTO>();
        String description = "";
        Integer itemCount = -1;
        for (ItemInventoryDTO item : itemInventory){
            boolean thisIsTheSameResource = item.getDescription().equals(description);
            if(thisIsTheSameResource){
                itemInventoryAux.get(itemCount).setAmount(itemInventoryAux.get(itemCount).getAmount()+item.getAmount());
            }
            else{
                itemCount++;
                description=item.getDescription();
                itemInventoryAux.add(item);
            }
        }
        itemInventory.clear();
        itemInventory.addAll(itemInventoryAux);
    }

    private String convertListToInventoryAvg(List<ItemInventoryDTO> listItemInventory){
        String inventory=new String();
        for(ItemInventoryDTO inventoryItem : listItemInventory) {
            inventory=inventory+inventoryItem.getAmount()+" "+inventoryItem.getDescription()+" per Survivor,";
        }
        inventory=inventory.substring(0,inventory.length()-1);
        return inventory;
    }

    public Integer lostPointByInfectedSurvivors(){
        List<Survivor> listSurvivors = survivorRepository.findAllInfected();
        List<ItemInventoryDTO> itemInventory = new ArrayList<ItemInventoryDTO>();

        for(Survivor survivor : listSurvivors){
            itemInventory.addAll(survivor.getInventoryList());
        }

        return countListPoints(itemInventory);

    }

    private Integer countListPoints(List<ItemInventoryDTO> listInventory){
        List<ItemsPoints> itemsPoints = itemsPointsRepository.findAll();
        Integer points = 0;
        for(ItemInventoryDTO item : listInventory){
            for(ItemsPoints point : itemsPoints){
                boolean thisIsTheSameResource = point.getDescription().equals(item.getDescription());
                if(thisIsTheSameResource){
                    points+=point.getPoints()*item.getAmount();
                }

            }
        }
        return points;
    }

}
