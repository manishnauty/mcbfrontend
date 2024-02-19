package com.mcb.bankpropertyevaluation.dao.entity;

import javax.persistence.*;

import lombok.Data;

import java.util.List;

@Entity
@Table(name = "application")
@Data
public class PVApplication {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User createdBy;

    @Column(name = "fos_reference_number")
    private String fosreferenceNumber;

    @Column(name = "type")
    private String type;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "app_id")
    private Facility facility;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "app_id")
    private List<Borrower> borrowers;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "app_id")
    private List<Document> documents;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "app_id")
    private List<Comment> comments;
}
