package com.mcb.bankpropertyevaluation.service;

import com.mcb.bankpropertyevaluation.controller.payload.CurrencyDto;
import com.mcb.bankpropertyevaluation.controller.payload.DocumentTypeDto;
import com.mcb.bankpropertyevaluation.controller.payload.FacilityTypeDto;
import com.mcb.bankpropertyevaluation.controller.payload.PVApplicationDto;

import java.util.List;

public interface PVApplicationReadService {
    List<CurrencyDto> getCurrencies();

    List<FacilityTypeDto> getFacilityTypes();

    List<DocumentTypeDto> getDocumentTypes();

    List<PVApplicationDto> fetchApplication();

    PVApplicationDto fetchApplicationById(Long id);
}
