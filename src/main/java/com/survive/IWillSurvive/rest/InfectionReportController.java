package com.survive.IWillSurvive.rest;


import com.survive.IWillSurvive.dto.InfectionReportDTO;
import com.survive.IWillSurvive.dto.TradeDTO;
import com.survive.IWillSurvive.model.entity.InfectionReport;
import com.survive.IWillSurvive.model.entity.Status;
import com.survive.IWillSurvive.model.entity.Survivor;
import com.survive.IWillSurvive.model.repository.InfectionReportRepository;
import com.survive.IWillSurvive.model.repository.StatusRepository;
import com.survive.IWillSurvive.model.repository.SurvivorRepository;
import com.survive.IWillSurvive.service.InfectionReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/infectionReport")
public class InfectionReportController {

    private final  InfectionReportService infectionReportService;

    @Autowired
    public InfectionReportController(InfectionReportService infectionReportService) {
        this.infectionReportService = infectionReportService;
    }

    @PutMapping("/reportInfected")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public InfectionReport reportInfected(@RequestBody InfectionReportDTO infectionReportDTO){
       return  infectionReportService.reportInfected(infectionReportDTO);
    }

}
