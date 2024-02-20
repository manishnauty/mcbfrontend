package com.mcb.bankpropertyevaluation.service.impl;

import com.mcb.bankpropertyevaluation.controller.payload.CurrencyDto;
import com.mcb.bankpropertyevaluation.controller.payload.DocumentTypeDto;
import com.mcb.bankpropertyevaluation.controller.payload.FacilityTypeDto;
import com.mcb.bankpropertyevaluation.controller.payload.PVApplicationDto;
import com.mcb.bankpropertyevaluation.dao.entity.*;
import com.mcb.bankpropertyevaluation.dao.repository.CurrencyRepository;
import com.mcb.bankpropertyevaluation.dao.repository.DocumentTypeRepository;
import com.mcb.bankpropertyevaluation.dao.repository.FacilityTypeRepository;
import com.mcb.bankpropertyevaluation.dao.repository.PVApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PVApplicationReadServiceImplTest {

    @InjectMocks
    private PVApplicationReadServiceImpl appService;
    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private FacilityTypeRepository facilityTypeRepository;
    @Mock
    private DocumentTypeRepository documentTypeRepository;
    @Mock
    private PVApplicationRepository pvApplicationRepository;

    DocumentType documentType = new DocumentType(1l,"Title Deed");
    List<Currency> currencyList = List.of(new Currency(1l,"MUR"));
    List<DocumentType> documentTypeList = List.of(documentType);
    List<Document> documentList = List.of(new Document(1l,documentType, "".getBytes()));
    List<Borrower> borrowerList = List.of(new Borrower(1l,"name","cust","2903","email@abc.com","address"));
    List<FacilityType> facilityTypeList = List.of(new FacilityType(1l,"Revolving"));
    Facility facility = new Facility();
    User user = new User();
    PVApplication application = new PVApplication(1l,new User(),"","","","","",null,borrowerList,documentList,new ArrayList<Comment>());

    List<PVApplication> applicationList = List.of(application);
    @Test
    void testGetCurrencies(){
        Mockito.when(currencyRepository.findAll()).thenReturn(currencyList);
        List<CurrencyDto> retValue =appService.getCurrencies();
        Mockito.verify(currencyRepository, Mockito.times(1)).findAll();
        assertEquals(retValue.get(0).getName(),currencyList.get(0).getName());
    }
    @Test
    void testGetFacilityTypes(){
        Mockito.when(facilityTypeRepository.findAll()).thenReturn(facilityTypeList);
        List<FacilityTypeDto> retValue = appService.getFacilityTypes();
        Mockito.verify(facilityTypeRepository, Mockito.times(1)).findAll();
        assertEquals(retValue.get(0).getName(),facilityTypeList.get(0).getName());
    }
    @Test
    void testGetDocumentTypes(){
        Mockito.when(documentTypeRepository.findAll()).thenReturn(documentTypeList);
        List<DocumentTypeDto> retValue = appService.getDocumentTypes();
        Mockito.verify(documentTypeRepository, Mockito.times(1)).findAll();
        assertEquals(retValue.get(0).getName(),documentTypeList.get(0).getName());
    }
    @Test
    void testFetchAllApplication(){
        Mockito.when(pvApplicationRepository.findAll()).thenReturn(applicationList);
        List<PVApplicationDto> retValue = appService.fetchApplication();
        Mockito.verify(pvApplicationRepository, Mockito.times(1)).findAll();
        assertEquals(retValue.get(0).getBorrowersDto().size(),applicationList.get(0).getBorrowers().size());
        assertEquals(retValue.get(0).getDocumentsDto().size(),applicationList.get(0).getDocuments().size());
        assertEquals(retValue.get(0).getCommentsDto(),null);
    }
    @Test
    void testFetchApplicationById(){
        Mockito.when(pvApplicationRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(application));
        PVApplicationDto retValue = appService.fetchApplicationById(1l);
        Mockito.verify(pvApplicationRepository, Mockito.times(1)).findById(Mockito.any(Long.class));
        assertEquals(retValue.getBorrowersDto().size(),application.getBorrowers().size());
        assertEquals(retValue.getDocumentsDto().size(),application.getDocuments().size());
        assertEquals(retValue.getCommentsDto(),null);

    }
    @Test
    void testGtNextAppReference(){
        Mockito.when(pvApplicationRepository.findMaxId()).thenReturn(2l);
        Long retValue = appService.getNextAppReference();
        Mockito.verify(pvApplicationRepository, Mockito.times(1)).findMaxId();
        assertEquals(retValue,3l);
    }

    
}
