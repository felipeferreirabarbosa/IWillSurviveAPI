package com.survive.IWillSurvive.service;

import com.survive.IWillSurvive.dto.ItemInventoryDTO;
import com.survive.IWillSurvive.dto.TradeDTO;
import com.survive.IWillSurvive.exception.SurvivorNotFoundException;
import com.survive.IWillSurvive.exception.TradePointsDontMatch;
import com.survive.IWillSurvive.exception.TradeWithAInfectedException;
import com.survive.IWillSurvive.model.entity.ItemsPoints;
import com.survive.IWillSurvive.model.entity.Survivor;
import com.survive.IWillSurvive.model.repository.ItemsPointsRepository;
import com.survive.IWillSurvive.model.repository.SurvivorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TradeService {


    private final SurvivorRepository survivorRepository;
    private final ItemsPointsRepository itemsPointsRepository;

    @Autowired
    public TradeService(SurvivorRepository survivorRepository, ItemsPointsRepository itemsPointsRepository) {
        this.survivorRepository = survivorRepository;
        this.itemsPointsRepository = itemsPointsRepository;
    }

    public void trade(TradeDTO tradeDTO) {
        Survivor trader = survivorRepository.findById(tradeDTO.getIdTrader()).orElseThrow(SurvivorNotFoundException::new);
        List<ItemInventoryDTO> traderListInventory = itemToListDTO(tradeDTO.getItemsTrader());

        Survivor receiver = survivorRepository.findById(tradeDTO.getIdReceiver()).orElseThrow(SurvivorNotFoundException::new);
        List<ItemInventoryDTO> receiverListInventory = itemToListDTO(tradeDTO.getItemsReceiver());

        haveAInfectedOnTrade(receiver, trader);
        checkIfItemExistInInventoryAndRemove(trader, traderListInventory);
        checkIfItemExistInInventoryAndRemove(receiver, receiverListInventory);
        boolean isTradePointsEquals = countListPoints(traderListInventory).equals(countListPoints(receiverListInventory));
        if (!isTradePointsEquals) {
            throw new TradePointsDontMatch();
        } else{
            tradeOperation(trader, receiverListInventory);
            tradeOperation(receiver, traderListInventory);
            survivorRepository.save(trader);
            survivorRepository.save(receiver);
        }


    }

    private Integer countListPoints(List<ItemInventoryDTO> listInventory){
        List<ItemsPoints> itemsPoints = itemsPointsRepository.findAll();
        Integer points = 0;
        for(ItemInventoryDTO item : listInventory){
            for(ItemsPoints point : itemsPoints){
                if(point.getDescription().equals(item.getDescription())){
                    points+=point.getPoints()*item.getAmount();
                }

            }
        }
        return points;
    }

    private void checkIfItemExistInInventoryAndRemove(Survivor survivor, List<ItemInventoryDTO> listInventory) {
        for (ItemInventoryDTO itemInventoryDTO : listInventory) {
            survivor.canTradeThisAmountOfItem(itemInventoryDTO);
        }
    }

    private void haveAInfectedOnTrade(Survivor survivor, Survivor trader) {
        if(trader.getStatus().getInfected() || survivor.getStatus().getInfected()) {
            throw new TradeWithAInfectedException();
        }
    }


    private void tradeOperation(Survivor survivor, List<ItemInventoryDTO> receiverListInventory) {
        List<ItemInventoryDTO> inventory = survivor.getInventoryList();
        inventory.addAll(receiverListInventory);
        survivor.setInventoryList(inventory);
        survivor.convertListToInventory();
    }

    private List<ItemInventoryDTO> itemToListDTO(String items){
        String[] inventorySplit = items.split(",|:");
        List<ItemInventoryDTO> listInventory = new ArrayList<ItemInventoryDTO>();
        for(int i=0;i<inventorySplit.length/2;i++){
            ItemInventoryDTO item = new ItemInventoryDTO(inventorySplit[i*2],Integer.valueOf(inventorySplit[i*2+1]));
            listInventory.add(item);
        }
        return  listInventory;
    }

}
