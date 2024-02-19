package com.mcb.bankpropertyevaluation.controller.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PVApplicationDto {

    private Long id;
    private UserDto createdBy;
    private String fosreferenceNumber;
    private String type;
    private FacilityDto facilityDto;
    private List<BorrowerDto> borrowersDto;
    private List<DocumentDto> documentsDto;
    private List<CommentsDto> commentsDto;
}
