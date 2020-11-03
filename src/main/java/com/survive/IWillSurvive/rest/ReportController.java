package com.survive.IWillSurvive.rest;

import com.survive.IWillSurvive.model.entity.Survivor;
import com.survive.IWillSurvive.model.repository.ItemsPointsRepository;
import com.survive.IWillSurvive.model.repository.SurvivorRepository;
import com.survive.IWillSurvive.service.SurvivorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "ReportController")
@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final SurvivorRepository survivorRepository;
    private final SurvivorService survivorService;

    @Autowired
    ReportController(SurvivorRepository survivorRepository, SurvivorService survivorService){
       this.survivorRepository = survivorRepository;
        this.survivorService = survivorService;
    }

    @ApiOperation(value = "A report of all healthy survivors with us.", tags={"findNotInfectedSurvivors"})
    @GetMapping("/healthy")
    public List<Survivor> findNotInfectedSurvivors(){
        return survivorRepository.findAllNotInfected();
    }

    @ApiOperation(value = "A report of all infected survivors.", tags={"findInfectedSurvivors"})
    @GetMapping("/infected")
    public List<Survivor> findInfectedSurvivors(){
        return survivorRepository.findAllInfected();
    }

    @ApiOperation(value = "A overview of our healthy survivors.", tags={"percentOfNonInfectedSurvivors"})
    @GetMapping("/percentOfNonInfected")
    public Float percentOfNonInfectedSurvivors(){
        return survivorRepository.findPercentOfNotInfected();
    }

    @ApiOperation(value = "A overview of our infected survivors.", tags={"percentOfInfectedSurvivors"})
    @GetMapping("/percentOfInfected")
    public Float percentOfInfectedSurvivors(){
        return 100 - survivorRepository.findPercentOfNotInfected();
    }

    @ApiOperation(value = "Items infected.", tags={"lostPointByInfectedSurvivors"})
    @GetMapping("/lostPoints")
    public Integer lostPointByInfectedSurvivors(){
        return survivorService.lostPointByInfectedSurvivors();
    }

    @ApiOperation(value = "A overview of your resources.", tags={"itemsPerSurvivor"})
    @GetMapping("/itemsPerSurvivor")
    public String itemsPerSurvivor(){
        return survivorService.itemsPerSurvivor();
    }

}
