package com.survive.IWillSurvive.rest;

import com.survive.IWillSurvive.dto.TradeDTO;
import com.survive.IWillSurvive.model.entity.Survivor;
import com.survive.IWillSurvive.service.SurvivorService;
import com.survive.IWillSurvive.service.TradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Map;

@Api(value = "SurvivorController", description = "Api related to manage survivors.")
@RestController
@RequestMapping("/api/survivors")
public class SurvivorController {

    private final TradeService tradeService;
    private final SurvivorService survivorService;

    @Autowired
    public SurvivorController(TradeService tradeService,
                              SurvivorService survivorService) {
        this.tradeService = tradeService;
        this.survivorService = survivorService;
    }

    @ApiOperation(value = "Add a survivor on database", tags={"survivor create"})
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Survivor create(@RequestBody @Valid Survivor survivor){
      return survivorService.create(survivor);
    }

    @PutMapping("/trade")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void trade(@RequestBody TradeDTO tradeDTO) {
        tradeService.trade(tradeDTO);
    }

    @PutMapping("/{idSurvivor}/location")
    public Survivor attLocation(@PathVariable Integer idSurvivor, @RequestBody Map<String,String> body){
        String location = body.get("location");
        if(location.isEmpty()) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Location is required!");
        }
        return survivorService.attLocation(idSurvivor, location);
    }
}
