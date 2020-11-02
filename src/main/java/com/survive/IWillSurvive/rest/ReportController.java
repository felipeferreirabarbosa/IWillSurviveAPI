package com.survive.IWillSurvive.rest;

import com.survive.IWillSurvive.model.entity.Survivor;
import com.survive.IWillSurvive.model.repository.ItemsPointsRepository;
import com.survive.IWillSurvive.model.repository.SurvivorRepository;
import com.survive.IWillSurvive.service.SurvivorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final SurvivorRepository survivorRepository;
    private final ItemsPointsRepository itensPointsRepository;
    private final SurvivorService survivorService;

    @Autowired
    ReportController(SurvivorRepository survivorRepository, ItemsPointsRepository itensPointsRepository, SurvivorService survivorService){
       this.survivorRepository = survivorRepository;
       this.itensPointsRepository = itensPointsRepository;
       this.survivorService = survivorService;
    }

    @GetMapping("/healthy")
    public List<Survivor> findNotInfectedSurvivors(){
        return survivorRepository.findAllNotInfected();
    }

    @GetMapping("/infected")
    public List<Survivor> findInfectedSurvivors(){
        return survivorRepository.findAllInfected();
    }


    @GetMapping("/percentOfNonInfected")
    public Float percentOfNonInfectedSurvivors(){
        return survivorRepository.findPercentOfNotInfected();
    }

    @GetMapping("/percentOfInfected")
    public Float percentOfInfectedSurvivors(){
        return 100 - survivorRepository.findPercentOfNotInfected();
    }

    @GetMapping("/lostPoints")
    public Integer lostPointByInfectedSurvivors(){
        return survivorService.lostPointByInfectedSurvivors();
    }

    @GetMapping("/itemsPerSurvivor")
    public String itemsPerSurvivor(){
        return survivorService.itemsPerSurvivor();
    }

}
