package com.mcb.bankpropertyevaluation.dao.repository;

import com.mcb.bankpropertyevaluation.dao.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {
    DocumentType findByName(String name);
}
