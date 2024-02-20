package com.mcb.bankpropertyevaluation.controller;

import com.mcb.bankpropertyevaluation.controller.payload.CurrencyDto;
import com.mcb.bankpropertyevaluation.controller.payload.DocumentTypeDto;
import com.mcb.bankpropertyevaluation.controller.payload.FacilityTypeDto;
import com.mcb.bankpropertyevaluation.controller.payload.PVApplicationDto;
import com.mcb.bankpropertyevaluation.service.PVApplicationReadService;
import com.mcb.bankpropertyevaluation.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class PVApplicationReadControllerTest {

    @InjectMocks
    private PVApplicationReadController controller;
    @Mock
    private PVApplicationReadService service;


    @Test
    void testGetCurrencies(){
        Mockito.when(service.getCurrencies()).thenReturn(new ArrayList<CurrencyDto>());
        controller.getCurrencies();
        Mockito.verify(service, Mockito.times(1)).getCurrencies();
    }
    @Test
    void testGetFacilityTypes(){
        Mockito.when(service.getFacilityTypes()).thenReturn(new ArrayList<FacilityTypeDto>());
        controller.getFacilityTypes();
        Mockito.verify(service, Mockito.times(1)).getFacilityTypes();
    }
    @Test
    void testGetDocumentTypes(){
        Mockito.when(service.getDocumentTypes()).thenReturn(new ArrayList<DocumentTypeDto>());
        controller.getDocumentTypes();
        Mockito.verify(service, Mockito.times(1)).getDocumentTypes();
    }
    @Test
    void testFetchAllApplication(){
        Mockito.when(service.fetchApplication()).thenReturn(new ArrayList<PVApplicationDto>());
        controller.fetchAllApplication();
        Mockito.verify(service, Mockito.times(1)).fetchApplication();
    }
    @Test
    void testFetchApplicationById(){
        Mockito.when(service.fetchApplicationById(Mockito.any(Long.class))).thenReturn(new PVApplicationDto());
        controller.fetchApplicationById(1l);
        Mockito.verify(service, Mockito.times(1)).fetchApplicationById(Mockito.any(Long.class));
    }
    @Test
    void testGtNextAppReference(){
        Mockito.when(service.getNextAppReference()).thenReturn(1l);
        controller.getNextAppReference();
        Mockito.verify(service, Mockito.times(1)).getNextAppReference();

    }
}
