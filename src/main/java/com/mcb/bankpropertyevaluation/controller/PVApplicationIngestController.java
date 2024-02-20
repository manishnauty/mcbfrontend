package com.mcb.bankpropertyevaluation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcb.bankpropertyevaluation.controller.payload.PVApplicationDto;
import com.mcb.bankpropertyevaluation.controller.payload.response.FormResponse;
import com.mcb.bankpropertyevaluation.service.PVApplicationIngestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app/bpea")
public class PVApplicationIngestController {

    @Autowired
    private PVApplicationIngestService pvApplicationIngestService;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @PostMapping("/evaluationform")
    public ResponseEntity<FormResponse> createEvaluationApplication(@RequestParam("file") MultipartFile file, @RequestParam("appData") String appData) throws JsonProcessingException {

        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        PVApplicationDto pvAppDto = MAPPER.readValue(appData, PVApplicationDto.class);
        pvApplicationIngestService.createEvaluationApplication(pvAppDto,file);
        return new ResponseEntity<>(new FormResponse("success"), HttpStatus.OK);
    }

}
