package com.survive.IWillSurvive.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Data
public class InfectionReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch =  FetchType.LAZY, optional = true)
    @JoinColumn(nullable = false, name = "id_status")
    Status status;

    @OneToOne
    @JoinColumn(name = "id_snitch")
    Survivor snitch;

}
