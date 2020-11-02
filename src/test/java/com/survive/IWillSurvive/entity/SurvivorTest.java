package com.survive.IWillSurvive.entity;

import com.survive.IWillSurvive.dto.ItemInventoryDTO;
import com.survive.IWillSurvive.exception.AmountOfItemNotFoundInInventoryException;
import com.survive.IWillSurvive.model.entity.Survivor;
import com.survive.IWillSurvive.validate.SurvivorLocationValidator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SurvivorTest {

    private Survivor survivor;

    public void setUp(){
        survivor = new Survivor();
        survivor.setName("Obj-01");
        survivor.setAge(25);
        survivor.setGender("Male");
        survivor.setInventory("Fiji Water:10,AK47:5,Campbell Soup:2");
    }

    @Test
    public void inventoryToListTest(){
        setUp();
        List<ItemInventoryDTO> result = survivor.getInventoryList();
        List<ItemInventoryDTO> expected = new ArrayList<ItemInventoryDTO>();
        expected.add(new ItemInventoryDTO("Fiji Water",10));
        expected.add(new ItemInventoryDTO("AK47",5));
        expected.add(new ItemInventoryDTO("Campbell Soup",2));
        assertEquals(expected,result);
    }

   @Test
    public void canTradeThisAmountOfItemFalseTest(){
        setUp();
        ItemInventoryDTO item = new ItemInventoryDTO("Fiji Water",12);
       assertThrows(AmountOfItemNotFoundInInventoryException.class,() -> {
           survivor.canTradeThisAmountOfItem(item);
       });
    }

    @Test()
    public void canTradeThisAmountOfItemTrueTest(){
        setUp();
        ItemInventoryDTO item = new ItemInventoryDTO("AK47",2);
        survivor.canTradeThisAmountOfItem(item);
    }

    @Test
    public void convertListToInventoryTest(){
        setUp();
        List<ItemInventoryDTO> list = new ArrayList<ItemInventoryDTO>();
        list.add(new ItemInventoryDTO("Fiji Water",45));
        list.add(new ItemInventoryDTO("AK47",1));
        survivor.setInventoryList(list);
        survivor.convertListToInventory();
        String expected = "Fiji Water:45,AK47:1";
        assertEquals(expected,survivor.getInventory());
    }
}
