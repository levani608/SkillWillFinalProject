package com.study.projects.newphotoproject.model.param.filterparams;

import com.study.projects.newphotoproject.model.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserFilterFieldParam{

    private List<String> fieldNames;

    private List<Long> userIds;

    private String username;

    private String firstname;

    private String lastname;

    private String email;

    private List<UserStatus> userStatuses;

    private LocalDate createdFrom;

    private LocalDate createdTo;

    private LocalDate updatedFrom;

    private LocalDate updatedTo;

}
