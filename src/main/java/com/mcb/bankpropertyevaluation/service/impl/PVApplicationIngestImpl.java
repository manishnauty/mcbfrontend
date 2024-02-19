package com.mcb.bankpropertyevaluation.service.impl;

import com.mcb.bankpropertyevaluation.controller.payload.PVApplicationDto;
import com.mcb.bankpropertyevaluation.dao.entity.*;
import com.mcb.bankpropertyevaluation.dao.entity.Currency;
import com.mcb.bankpropertyevaluation.dao.repository.*;
import com.mcb.bankpropertyevaluation.service.PVApplicationIngestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class PVApplicationIngestImpl implements PVApplicationIngestService {
    @Autowired
    private PVApplicationRepository pvApplicationRepository;

    @Autowired
    private FacilityTypeRepository facilityTypeRepository;

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public String createEvaluationApplication(PVApplicationDto pvAppDto, MultipartFile file) {
        PVApplication pvApplication = populateApplicationData(pvAppDto,file);
        pvApplicationRepository.save(pvApplication);
        return "success";
    }

    @Override
    public String updateEvaluationApplication(PVApplicationDto pvAppDto) {
       // PVApplication pvApplication = populateApplicationData(pvAppDto);
       // pvApplicationRepository.save(pvApplication);
        return "success";
    }

    private PVApplication populateApplicationData(PVApplicationDto pvAppDto, MultipartFile file) {
        PVApplication pvApplication = new PVApplication();
        pvApplication.setType(pvAppDto.getType());
        pvApplication.setFosreferenceNumber(pvAppDto.getFosreferenceNumber());
        populateDocuments(pvApplication, pvAppDto, file);
        populateUserData(pvApplication, pvAppDto);
        populateFacilityData(pvApplication, pvAppDto);
        populateBorrowersData(pvApplication, pvAppDto);

        populateComments(pvApplication, pvAppDto);
        return pvApplication;
    }

    private void populateComments(PVApplication pvApplication, PVApplicationDto pvAppDto) {
        List<Comment> commentList = new ArrayList<>();
        pvAppDto.getCommentsDto().forEach(commentDto ->{
            Comment comment = new Comment();
            comment.setComments(commentDto.getComments());
            comment.setCreatedDate(commentDto.getCreatedDate());
            commentList.add(comment);
        });
        pvApplication.setComments(commentList);
    }

    private void populateDocuments(PVApplication pvApplication, PVApplicationDto pvAppDto, MultipartFile file) {
        List<Document> documentList = new ArrayList<>();

        pvAppDto.getDocumentsDto().forEach(documentDto -> {
            Document document = new Document();
           // document.setDocument(documentDto.getDocument());
            DocumentType documentType;
            if (Objects.nonNull(documentDto.getType().getName())){
//                documentType = new DocumentType();
//                documentType.setName(documentDto.getType().getName());
//                document.setType(documentType);
                documentType = documentTypeRepository.findByName(documentDto.getType().getName());
                if (Objects.nonNull(documentType)){
                    document.setType(documentType);
                    try {
                        document.setDocument(file.getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else{
                    documentType = new DocumentType();
                    try {
                        document.setDocument(file.getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                   // documentType.setName(documentDto.getType().getName());
                   // document.setType(documentType);
                }

            }
            documentList.add(document);
        });
        pvApplication.setDocuments(documentList);
    }

    private void populateBorrowersData(PVApplication pvApplication, PVApplicationDto pvAppDto) {
        List<Borrower> borrowersList = new ArrayList<>();
        pvAppDto.getBorrowersDto().forEach(borrowerDto -> {
            Borrower borrower = new Borrower();
            borrower.setName(borrowerDto.getName());
            borrower.setContactNumber(borrowerDto.getContactNumber());
            borrower.setEmail(borrowerDto.getEmail());
            borrower.setCustomerNumber(borrowerDto.getCustomerNumber());
            borrower.setAddress(borrowerDto.getAddress());
            borrowersList.add(borrower);
        });
        pvApplication.setBorrowers(borrowersList);
    }

    private PVApplication populateFacilityData(PVApplication pvApplication, PVApplicationDto pvAppDto) {
        Facility facility = new Facility();
        facility.setCatagory(pvAppDto.getFacilityDto().getCatagory());
        facility.setTerm(pvAppDto.getFacilityDto().getTerm());
        facility.setAmount(pvAppDto.getFacilityDto().getAmount());
        facility.setPurpose(pvAppDto.getFacilityDto().getPurpose());

        populateFacilityTypeAndCurrency(pvAppDto, facility);
        pvApplication.setFacility(facility);
        return pvApplication;
    }

    private void populateFacilityTypeAndCurrency(PVApplicationDto pvAppDto, Facility facility) {
        FacilityType facilityType;
        Currency currency;
//        facilityType = new FacilityType();
//        facilityType.setName(pvAppDto.getFacilityDto().getType().getName());
//        facility.setType(facilityType);
//
//        currency = new Currency();
//        currency.setName(pvAppDto.getFacilityDto().getType().getName());
//        facility.setCcy(currency);

        if (Objects.nonNull(pvAppDto.getFacilityDto().getType().getName())){
            facilityType = facilityTypeRepository.findByName(pvAppDto.getFacilityDto().getType().getName());
            if (Objects.nonNull(facilityType)){
                facility.setType(facilityType);
            } else{
                facilityType = new FacilityType();
                facilityType.setName(pvAppDto.getFacilityDto().getType().getName());
                facility.setType(facilityType);
            }
        }
        if (Objects.nonNull(pvAppDto.getFacilityDto().getCcy().getName())){
            currency = currencyRepository.findByName(pvAppDto.getFacilityDto().getCcy().getName());
            if (Objects.nonNull(currency)){
                facility.setCcy(currency);
            } else{
                currency = new Currency();
                currency.setName(pvAppDto.getFacilityDto().getType().getName());
                facility.setCcy(currency);
            }
        }
    }

    private PVApplication populateUserData(PVApplication pvApplication, PVApplicationDto pvAppDto) {
        User user = null;
        if (Objects.nonNull(pvAppDto.getCreatedBy()) && Objects.nonNull(pvAppDto.getCreatedBy().getId())){
            Optional<User> optionalUser;
            optionalUser = userRepository.findById(pvAppDto.getCreatedBy().getId());
            if(optionalUser.isPresent()){
                user = optionalUser.get();
                user.setContactNumber(pvAppDto.getCreatedBy().getContactNumber());
            }
        } else {
            user = new User();
            user.setUsername("Username()");
            user.setContactNumber("ContactNumber()");
            //<Role> roles = populateRoles(pvAppDto);
            //user.setRoles(roles);
            user.setBuisnessUnit("BuisnessUnit()");
        }

        pvApplication.setCreatedBy(user);
        return pvApplication;
    }

    private Set<Role> populateRoles(PVApplicationDto pvAppDto) {
        Set<Role> roles = new HashSet<>();

        pvAppDto.getCreatedBy().getRoles().forEach(userRole-> {
            Role role = new Role();
            role.setName(UserRole.valueOf(userRole.getName()));
            roles.add(role);
        });
        return roles;
    }
}
