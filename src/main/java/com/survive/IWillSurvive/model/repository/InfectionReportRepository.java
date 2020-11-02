package com.survive.IWillSurvive.model.repository;

import com.survive.IWillSurvive.model.entity.InfectionReport;
import com.survive.IWillSurvive.model.entity.Survivor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InfectionReportRepository  extends JpaRepository<InfectionReport, Integer> {
    @Query(value = "select count(ir) from InfectionReport ir where ir.status.id = ?1 and ir.snitch.id = ?2")
    public Integer checkIfReportExists(Integer idStatus, Integer idSnitch);

    @Query(value = "select ir from InfectionReport ir join ir.status s where s.id = ?1 ")
    public  List<InfectionReport> findByStatus(Integer idStatus);
}
