package com.survive.IWillSurvive.service;

import com.survive.IWillSurvive.dto.InfectionReportDTO;
import com.survive.IWillSurvive.exception.ReportManyTimesException;
import com.survive.IWillSurvive.exception.SurvivorNotFoundException;
import com.survive.IWillSurvive.model.entity.InfectionReport;
import com.survive.IWillSurvive.model.entity.Status;
import com.survive.IWillSurvive.model.entity.Survivor;
import com.survive.IWillSurvive.model.repository.InfectionReportRepository;
import com.survive.IWillSurvive.model.repository.StatusRepository;
import com.survive.IWillSurvive.model.repository.SurvivorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InfectionReportService {

    private final InfectionReportRepository reportRepository;
    private final StatusRepository statusRepository;
    private final SurvivorRepository survivorRepository;
    private final Integer MAX_REPORTS_TO_MARK_INFECTED = 5;

    @Autowired
    public InfectionReportService(InfectionReportRepository reportRepository, StatusRepository statusRepository, SurvivorRepository survivorRepository) {
        this.reportRepository = reportRepository;
        this.statusRepository = statusRepository;
        this.survivorRepository = survivorRepository;
    }

    public InfectionReport reportInfected(InfectionReportDTO infectionReportDTO){
        Status statusInfected = statusRepository.findById(infectionReportDTO.getIdInfected()).orElseThrow(SurvivorNotFoundException::new);
        statusInfected.setReports(reportRepository.findByStatus(infectionReportDTO.getIdInfected()));
        Survivor snitcher = survivorRepository.findById(infectionReportDTO.getIdSurvivor()).orElseThrow(SurvivorNotFoundException::new);
        if(reportRepository.checkIfReportExists(infectionReportDTO.getIdInfected(),infectionReportDTO.getIdSurvivor())==0) {
            com.survive.IWillSurvive.model.entity.InfectionReport infectionReport = new InfectionReport();

            infectionReport.setSnitch(snitcher);
            infectionReport.setStatus(statusInfected);

            if(statusInfected.getReports().size()+1==MAX_REPORTS_TO_MARK_INFECTED || infectionReportDTO.getIdSurvivor().equals(infectionReportDTO.getIdInfected())) {
                statusInfected.setInfected(true);
                statusRepository.save(statusInfected);
            }

            return reportRepository.save(infectionReport);
        } else {
            throw new ReportManyTimesException();
        }

    }

}
