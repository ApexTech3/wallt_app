package com.apex.tech3.wallt_app.models.filters;

import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class UserFilterOptions {
    private Integer holderId;
    private String username;
    private String firstName;
    private String middleName, String;
    private String lastName;
    private String email;
    private String phone;
    private int page;
    private String sortBy;
    private String sortOrder;
}
