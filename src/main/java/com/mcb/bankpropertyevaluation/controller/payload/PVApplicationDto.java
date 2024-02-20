package com.mcb.bankpropertyevaluation.controller.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class PVApplicationDto {

    private Long id;
    private UserDto createdBy;
    private String referenceNumber;
    private String fosreferenceNumber;
    private String type;
    private String createdOn;
    private String modifiedOn;
    private FacilityDto facilityDto;
    private List<BorrowerDto> borrowersDto;
    private List<DocumentDto> documentsDto;
    private List<CommentsDto> commentsDto;
}
