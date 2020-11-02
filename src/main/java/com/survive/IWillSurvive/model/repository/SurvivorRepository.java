package com.survive.IWillSurvive.model.repository;

import com.survive.IWillSurvive.model.entity.Status;
import com.survive.IWillSurvive.model.entity.Survivor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SurvivorRepository extends JpaRepository<Survivor, Integer> {

    @Query(value = "select s from Survivor s join s.status sta where  sta.infected=false")
    public List<Survivor> findAllNotInfected();

    @Query(value = "select s from Survivor s join s.status sta where  sta.infected=true")
    public List<Survivor> findAllInfected();

    @Query(value = "select count(s)*100/(select count(s) from Survivor s) from Survivor s join s.status sta where sta.infected=false")
    public Float findPercentOfNotInfected();

}
