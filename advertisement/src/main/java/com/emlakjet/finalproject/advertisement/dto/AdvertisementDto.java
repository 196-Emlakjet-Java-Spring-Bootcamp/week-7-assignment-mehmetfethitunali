package com.emlakjet.finalproject.advertisement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementDto {

    private String username;

    private String title;

    private String detailedMessage;

    private Long price;

}
