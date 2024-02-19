package com.mcb.bankpropertyevaluation.dao.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Facility {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "facility_type_id", referencedColumnName = "id")
    private FacilityType type;

    @Size(max = 50)
    private String catagory;

    @Size(max = 20)
    private String purpose;

    private Integer term;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency ccy;

    private Double amount;
}
