package com.mcb.bankpropertyevaluation.service;

import com.mcb.bankpropertyevaluation.controller.payload.PVApplicationDto;
import org.springframework.web.multipart.MultipartFile;

public interface PVApplicationIngestService {
    String createEvaluationApplication(PVApplicationDto pvAppDto, MultipartFile file);

    String updateEvaluationApplication(PVApplicationDto pvAppDto);
}
