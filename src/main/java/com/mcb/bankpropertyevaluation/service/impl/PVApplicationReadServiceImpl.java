package com.mcb.bankpropertyevaluation.service.impl;

import com.mcb.bankpropertyevaluation.controller.payload.*;
import com.mcb.bankpropertyevaluation.dao.entity.*;
import com.mcb.bankpropertyevaluation.dao.entity.Currency;
import com.mcb.bankpropertyevaluation.dao.repository.CurrencyRepository;
import com.mcb.bankpropertyevaluation.dao.repository.DocumentTypeRepository;
import com.mcb.bankpropertyevaluation.dao.repository.FacilityTypeRepository;
import com.mcb.bankpropertyevaluation.dao.repository.PVApplicationRepository;
import com.mcb.bankpropertyevaluation.service.PVApplicationReadService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class PVApplicationReadServiceImpl implements PVApplicationReadService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private FacilityTypeRepository facilityTypeRepository;

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private PVApplicationRepository pvApplicationRepository;

    @Override
    public List<CurrencyDto> getCurrencies() {
        List<CurrencyDto> currencyResponseList = new ArrayList<>();
        try {
            List<Currency> currencyList = currencyRepository.findAll();
            currencyList.forEach(currency -> {
                CurrencyDto currencyResponse = new CurrencyDto();
                currencyResponse.setId(currency.getId());
                currencyResponse.setName(currency.getName());
                currencyResponseList.add(currencyResponse);
            });
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        return currencyResponseList;
    }

    @Override
    public List<FacilityTypeDto> getFacilityTypes() {
        List<FacilityTypeDto> facilityTypeResponseList = new ArrayList<>();
        try {
            List<FacilityType> facilityTypeList = facilityTypeRepository.findAll();
            facilityTypeList.forEach(facilityType -> {
                FacilityTypeDto facilityTypeResponse = new FacilityTypeDto();
                facilityTypeResponse.setId(facilityType.getId());
                facilityTypeResponse.setName(facilityType.getName());
                facilityTypeResponseList.add(facilityTypeResponse);
            });
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        return facilityTypeResponseList;
    }

    @Override
    public List<DocumentTypeDto> getDocumentTypes() {
        List<DocumentTypeDto> documentTypeResponseList = new ArrayList<>();
        try {
            List<DocumentType> documentTypeList = documentTypeRepository.findAll();
            documentTypeList.forEach(documentType -> {
                DocumentTypeDto documentTypeResponse = new DocumentTypeDto();
                documentTypeResponse.setId(documentType.getId());
                documentTypeResponse.setName(documentType.getName());
                documentTypeResponseList.add(documentTypeResponse);
            });
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        return documentTypeResponseList;
    }

    @Override
    @Transactional
    public List<PVApplicationDto> fetchApplication() {
        List<PVApplicationDto> pvApplicationDtoList = new ArrayList<>();
        try {
            List<PVApplication> pvApplicationList = pvApplicationRepository.findAll();
            if(!CollectionUtils.isEmpty(pvApplicationList)){
                pvApplicationList.forEach(pvApplication -> {
                    PVApplicationDto pVApplicationDto = new PVApplicationDto();
                    populatePvApplicationResponse(pVApplicationDto, pvApplication);
                    pvApplicationDtoList.add(pVApplicationDto);
                });
            }
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        return pvApplicationDtoList;
    }

    @Override
    @Transactional
    public PVApplicationDto fetchApplicationById(Long id) {
        PVApplicationDto pvApplicationDto = new PVApplicationDto();
        try {
            Optional<PVApplication> pvApplication = pvApplicationRepository.findById(id);
            if (pvApplication.isPresent()){
                populatePvApplicationResponse(pvApplicationDto, pvApplication.get());
            }
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        return pvApplicationDto;
    }

    private void populatePvApplicationResponse(PVApplicationDto pVApplicationDto, PVApplication pvApplication) {
        pVApplicationDto.setId(pvApplication.getId());
        pVApplicationDto.setType(pvApplication.getType());
        pVApplicationDto.setFosreferenceNumber(pvApplication.getFosreferenceNumber());
        pVApplicationDto.setCreatedBy(populateUserDto(pvApplication));
        pVApplicationDto.setFacilityDto(populateFacilityDto(pvApplication));
        pVApplicationDto.setBorrowersDto(populateBorrowerDto(pvApplication));
        pVApplicationDto.setDocumentsDto(populateDocumentsDto(pvApplication));
        pVApplicationDto.setCommentsDto(populateCommentsDto(pvApplication));
    }

    private List<CommentsDto> populateCommentsDto(PVApplication pvApplication) {
        List<CommentsDto> commentsDtoList;
        if (!CollectionUtils.isEmpty(pvApplication.getComments())){
            commentsDtoList = new ArrayList<>();
            pvApplication.getComments().forEach(comment -> {
                CommentsDto commentsDto = new CommentsDto();
                BeanUtils.copyProperties(comment, commentsDto);
                commentsDtoList.add(commentsDto);
            });
        } else {
            commentsDtoList = null;
        }
        return commentsDtoList;
    }

    private List<DocumentDto> populateDocumentsDto(PVApplication pvApplication) {
        List<DocumentDto> documentDtoList;
        if (!CollectionUtils.isEmpty(pvApplication.getDocuments())){
            documentDtoList = new ArrayList<>();
            pvApplication.getDocuments().forEach(document -> {
                DocumentDto documentDto = new DocumentDto();
                DocumentTypeDto documentTypeDto = new DocumentTypeDto();
                //BeanUtils.copyProperties(document,documentDto);
                documentDto.setDocument(document.getDocument());
                //BeanUtils.copyProperties(document.getType(), documentTypeDto);
                //documentDto.setType(documentTypeDto);
                documentDtoList.add(documentDto);
            });
        } else {
            documentDtoList = null;
        }
        return documentDtoList;
    }

    private List<BorrowerDto> populateBorrowerDto(PVApplication pvApplication) {
        List<BorrowerDto> borrowerDtoList;
        if (!CollectionUtils.isEmpty(pvApplication.getBorrowers())){
            borrowerDtoList = new ArrayList<>();
            pvApplication.getBorrowers().forEach(borrower -> {
                BorrowerDto borrowerDto = new BorrowerDto();
                BeanUtils.copyProperties(borrower, borrowerDto);
                borrowerDtoList.add(borrowerDto);
            });
        } else {
            borrowerDtoList = null;
        }
        return borrowerDtoList;
    }

    private FacilityDto populateFacilityDto(PVApplication pvApplication) {
        FacilityDto facilityDto = null;
        FacilityTypeDto facilityTypeDto = null;
        CurrencyDto currencyDto = null;
        if (Objects.nonNull(pvApplication.getFacility())){
            facilityDto = new FacilityDto();
            facilityTypeDto = new FacilityTypeDto();
            currencyDto = new CurrencyDto();
            BeanUtils.copyProperties(pvApplication.getFacility(), facilityDto);
            BeanUtils.copyProperties(pvApplication.getFacility().getType(), facilityTypeDto);
            BeanUtils.copyProperties(pvApplication.getFacility().getCcy(), currencyDto);
            facilityDto.setType(facilityTypeDto);
            facilityDto.setCcy(currencyDto);
        }
        return facilityDto;
    }

    private static UserDto populateUserDto(PVApplication pvApplication) {
        UserDto userDto = null;
        User user = pvApplication.getCreatedBy();
        if (Objects.nonNull(user)){
            userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            Set<RoleDto> roles =  new HashSet<>();
            if(!CollectionUtils.isEmpty(user.getRoles())){
                user.getRoles().forEach(role -> {
                    RoleDto roleDto = new RoleDto();
                    roleDto.setId(role.getId());
                    roleDto.setName(role.getName().toString());
                    roles.add(roleDto);
                });
            }
            userDto.setRoles(roles);
        }
        return userDto;
    }
}
