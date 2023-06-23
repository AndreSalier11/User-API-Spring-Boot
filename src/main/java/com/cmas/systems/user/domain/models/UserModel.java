package com.cmas.systems.user.domain.models;

import java.io.Serializable;
import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class UserModel extends RepresentationModel<UserModel> implements Serializable {
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private Date birthDate;
    private Boolean active;

    public UserModel(Long id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }
}
