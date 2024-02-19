package com.mcb.bankpropertyevaluation.dao.repository;

import com.mcb.bankpropertyevaluation.dao.entity.FacilityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityTypeRepository extends JpaRepository<FacilityType, Long> {
    FacilityType findByName(String name);
}
