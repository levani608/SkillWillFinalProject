package com.study.projects.newphotoproject.model.param.filterparams;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class UserFilterParam {


    private String username;

    private Integer page;
    private Integer size;

}
