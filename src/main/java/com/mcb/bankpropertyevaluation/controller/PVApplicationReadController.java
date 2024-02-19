package com.mcb.bankpropertyevaluation.controller;

import com.mcb.bankpropertyevaluation.controller.payload.CurrencyDto;
import com.mcb.bankpropertyevaluation.controller.payload.DocumentTypeDto;
import com.mcb.bankpropertyevaluation.controller.payload.FacilityTypeDto;
import com.mcb.bankpropertyevaluation.controller.payload.PVApplicationDto;
import com.mcb.bankpropertyevaluation.service.PVApplicationReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app/bpea")
public class PVApplicationReadController {

    @Autowired
    private PVApplicationReadService pvApplicationReadService;
    @GetMapping("/currencies")
    //@PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('ADMIN')")
    public ResponseEntity<List<CurrencyDto>> getCurrencies() {
        return new ResponseEntity<>(pvApplicationReadService.getCurrencies(), HttpStatus.OK);
    }

    @GetMapping("/facilitytypes")
    //@PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('ADMIN')")
    public ResponseEntity<List<FacilityTypeDto>> getFacilityTypes() {
        return new ResponseEntity<>(pvApplicationReadService.getFacilityTypes(), HttpStatus.OK);
    }

    @GetMapping("/documenttypes")
    //@PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('ADMIN')")
    public ResponseEntity<List<DocumentTypeDto>> getDocumentTypes() {
        return new ResponseEntity<>(pvApplicationReadService.getDocumentTypes(), HttpStatus.OK);
    }

    @GetMapping("/fetchapplication")
    public ResponseEntity<List<PVApplicationDto>> fetchAllApplication(){
        return new ResponseEntity<>(pvApplicationReadService.fetchApplication(), HttpStatus.OK);
    }

    @GetMapping("/fetchapplication/{id}")
    public ResponseEntity<PVApplicationDto> fetchAllApplication(@PathVariable Long id){
        return new ResponseEntity<>(pvApplicationReadService.fetchApplicationById(id), HttpStatus.OK);
    }


}
