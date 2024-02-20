package com.mcb.bankpropertyevaluation.dao.repository;

import com.mcb.bankpropertyevaluation.dao.entity.PVApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PVApplicationRepository extends JpaRepository<PVApplication, Long> {
    @Query(value = "SELECT MAX(u.id) FROM PVApplication u")
    Long findMaxId();
}
