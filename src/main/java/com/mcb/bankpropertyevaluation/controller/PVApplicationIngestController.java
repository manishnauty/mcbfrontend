package com.mcb.bankpropertyevaluation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcb.bankpropertyevaluation.controller.payload.PVApplicationDto;
import com.mcb.bankpropertyevaluation.dao.entity.PVApplication;
import com.mcb.bankpropertyevaluation.service.PVApplicationIngestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

//    @PostMapping("/evaluationform")
//    public ResponseEntity<String> createEvaluationApplication(@RequestBody PVApplicationDto pvAppDto){
//        pvApplicationIngestService.createEvaluationApplication(pvAppDto);
//        return new ResponseEntity<>("success", HttpStatus.OK);
//    }

    @PostMapping("/evaluationform")
    public ResponseEntity<String> createEvaluationApplication(@RequestParam("file") MultipartFile file, @RequestParam("appData") String appData) throws JsonProcessingException {
        System.out.println(appData);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        PVApplicationDto pvAppDto = mapper.readValue(appData, PVApplicationDto.class);
        System.out.println(pvAppDto);
        System.out.println(file);
        pvApplicationIngestService.createEvaluationApplication(pvAppDto,file);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @PutMapping(value = "/evaluationform")
    public ResponseEntity<String> updateEvaluationApplication(@Valid @RequestBody PVApplicationDto pvAppDto){
        pvApplicationIngestService.updateEvaluationApplication(pvAppDto);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }






}
