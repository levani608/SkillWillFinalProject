package com.study.projects.newphotoproject.model.mapper;


import com.study.projects.newphotoproject.model.dto.ResponseTokensDto;

public class ResponseTokensMapper {

    public static ResponseTokensDto toResponseTokensDto(String token, Long expiration) {
        return new ResponseTokensDto(token,expiration);
    }

}
