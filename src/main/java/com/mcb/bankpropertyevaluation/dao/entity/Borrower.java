package com.mcb.bankpropertyevaluation.dao.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
@Data
public class Borrower {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String customerNumber;

    @Size(max = 20)
    private String contactNumber;

    @Size(max = 50)
    private String email;

    @Size(max = 100)
    private String address;


}
