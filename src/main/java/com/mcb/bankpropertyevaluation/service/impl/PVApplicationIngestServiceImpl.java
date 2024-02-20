package com.mcb.bankpropertyevaluation.service.impl;

import com.mcb.bankpropertyevaluation.controller.payload.PVApplicationDto;
import com.mcb.bankpropertyevaluation.dao.entity.*;
import com.mcb.bankpropertyevaluation.dao.entity.Currency;
import com.mcb.bankpropertyevaluation.dao.repository.*;
import com.mcb.bankpropertyevaluation.security.services.UserDetailsImpl;
import com.mcb.bankpropertyevaluation.service.PVApplicationIngestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PVApplicationIngestServiceImpl implements PVApplicationIngestService {

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
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

    private PVApplication populateApplicationData(PVApplicationDto pvAppDto, MultipartFile file) {
        PVApplication pvApplication = new PVApplication();
        pvApplication.setType(pvAppDto.getType());
        pvApplication.setFosreferenceNumber(pvAppDto.getFosreferenceNumber());
        pvApplication.setReferenceNumber(generateReferenceNumber(pvAppDto.getFosreferenceNumber()));
        pvApplication.setCreatedOn(DATE_FORMAT.format(new Date()));
        populateDocuments(pvApplication, pvAppDto, file);
        populateUserData(pvApplication, pvAppDto);
        populateFacilityData(pvApplication, pvAppDto);
        populateBorrowersData(pvApplication, pvAppDto);

        populateComments(pvApplication, pvAppDto);
        return pvApplication;
    }

    private String generateReferenceNumber(String fosreferenceNumber) {
        String[] fosRefArr =fosreferenceNumber.split("/");
        return new StringBuilder()
                .append("PV")
                .append(fosRefArr[0])
                .append(fosRefArr[1])
                .append(fosRefArr[2]).toString();
    }

    private void populateComments(PVApplication pvApplication, PVApplicationDto pvAppDto) {
        List<Comment> commentList = new ArrayList<>();
        pvAppDto.getCommentsDto().forEach(commentDto ->{
            Comment comment = new Comment();
            comment.setComments(commentDto.getComment());
            comment.setCreatedDate(commentDto.getCreatedDate());
            commentList.add(comment);
        });
        pvApplication.setComments(commentList);
    }

    private void populateDocuments(PVApplication pvApplication, PVApplicationDto pvAppDto, MultipartFile file) {
        List<Document> documentList = new ArrayList<>();

        pvAppDto.getDocumentsDto().forEach(documentDto -> {
            Document document = new Document();
            try {
                document.setDocument(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            DocumentType documentType;
            if (Objects.nonNull(documentDto.getType().getName())){
                documentType = documentTypeRepository.findByName(documentDto.getType().getName());
                if (Objects.nonNull(documentType)){
                    document.setType(documentType);
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
        if (Objects.nonNull(pvAppDto.getFacilityDto().getType().getName())){
            facilityType = facilityTypeRepository.findByName(pvAppDto.getFacilityDto().getType().getName());
            if (Objects.nonNull(facilityType)){
                facility.setType(facilityType);
            }
        }
        if (Objects.nonNull(pvAppDto.getFacilityDto().getCcy().getName())){
            currency = currencyRepository.findByName(pvAppDto.getFacilityDto().getCcy().getName());
            if (Objects.nonNull(currency)){
                facility.setCcy(currency);
            }
        }
    }

    private PVApplication populateUserData(PVApplication pvApplication, PVApplicationDto pvAppDto) {
        User user = null;
        if (Objects.nonNull(pvAppDto.getCreatedBy()) && Objects.nonNull(pvAppDto.getCreatedBy().getUserid())){
            Optional<User> optionalUser;
            optionalUser = userRepository.findById(pvAppDto.getCreatedBy().getUserid());
            if(optionalUser.isPresent()){
                user = optionalUser.get();
                user.setContactNumber(pvAppDto.getCreatedBy().getContactNumber());
            } else {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                Optional<User> usr = userRepository.findById(userDetails.getId());
                user = usr.get();
            }
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Optional<User> usr = userRepository.findById(userDetails.getId());
            user = usr.get();
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
