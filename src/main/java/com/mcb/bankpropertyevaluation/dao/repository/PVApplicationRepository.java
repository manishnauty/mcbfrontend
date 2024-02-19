package com.mcb.bankpropertyevaluation.dao.repository;

import com.mcb.bankpropertyevaluation.dao.entity.PVApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PVApplicationRepository extends JpaRepository<PVApplication, Long> {
}
