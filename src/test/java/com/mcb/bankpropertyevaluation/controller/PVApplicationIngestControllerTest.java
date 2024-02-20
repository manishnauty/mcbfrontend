package com.mcb.bankpropertyevaluation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.mcb.bankpropertyevaluation.controller.payload.CurrencyDto;
import com.mcb.bankpropertyevaluation.controller.payload.PVApplicationDto;
import com.mcb.bankpropertyevaluation.service.PVApplicationIngestService;
import com.mcb.bankpropertyevaluation.service.PVApplicationReadService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PVApplicationIngestControllerTest {

    @InjectMocks
    private PVApplicationIngestController controller;
    @Mock
    private PVApplicationIngestService service;

    @Test
    void testCreateEvaluationApplication() throws JsonProcessingException {
        Exception exception = assertThrows(MismatchedInputException.class, () -> {
            controller.createEvaluationApplication(new MockMultipartFile("name","".getBytes()),"");
        });
        Mockito.verify(service, Mockito.times(0)).createEvaluationApplication(Mockito.any(),Mockito.any());
    }

}
