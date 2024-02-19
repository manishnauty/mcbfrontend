package com.mcb.bankpropertyevaluation.dao.entity;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class Document {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "document_type_id", referencedColumnName = "id")
    private DocumentType type;

    @Lob
    @Column(name="document", length=100000)
    private byte[] document;
}
