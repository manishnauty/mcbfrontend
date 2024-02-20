package com.mcb.bankpropertyevaluation.service.impl;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.mcb.bankpropertyevaluation.controller.payload.PVApplicationDto;
import com.mcb.bankpropertyevaluation.dao.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PVApplicationIngestServiceImplTest {

    @InjectMocks
    private PVApplicationIngestServiceImpl appService;

    @Mock
    private PVApplicationRepository pvApplicationRepository;

    @Mock
    private FacilityTypeRepository facilityTypeRepository;

    @Mock
    private DocumentTypeRepository documentTypeRepository;

    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    void testFetchAllApplication(){
        Exception exception = assertThrows(NullPointerException.class, () -> {
            String retValue = appService.createEvaluationApplication(new PVApplicationDto(),new MockMultipartFile("name","".getBytes()));
        });
    }
}
