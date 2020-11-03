package com.survive.IWillSurvive.rest;

import com.survive.IWillSurvive.dto.LocationDTO;
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

@Api(value = "SurvivorController")
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

    @ApiOperation(value = "Add a survivor on database.", tags={"create"})
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Survivor create(@RequestBody @Valid Survivor survivor){
      return survivorService.create(survivor);
    }

    @ApiOperation(value = "Trade operation, where a trader proposes a barter of items.", tags={"trade"})
    @PutMapping("/trade")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void trade(@RequestBody TradeDTO tradeDTO) {
        tradeService.trade(tradeDTO);
    }

    @ApiOperation(value = "Update a survivor location.", tags={"attLocation"})
    @PutMapping("/{idSurvivor}/location")
    public Survivor attLocation(@PathVariable Integer idSurvivor, @Valid @RequestBody LocationDTO locationDTO){
        return survivorService.attLocation(idSurvivor, locationDTO);
    }
}
