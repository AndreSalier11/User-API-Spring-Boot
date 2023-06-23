package com.cmas.systems.user.domain.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.cmas.systems.user.domain.User;
import com.cmas.systems.user.domain.models.UserModel;
import com.cmas.systems.user.endpoint.UserController;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserModel> {

    public UserModelAssembler() {
        super(UserController.class, UserModel.class);
    }

    @Override
    public UserModel toModel(User user) {
        UserModel userModel = instantiateModel(user);

        userModel.add(linkTo(UserController.class).slash(user.getId()).withRel("GET"));
        userModel.add(linkTo(UserController.class).slash(user.getId()).withRel("PUT"));
        userModel.add(linkTo(UserController.class).slash(user.getId()).withRel("DELETE"));
        
        userModel.setId(user.getId());
        userModel.setFirstName(user.getFirstName());
        userModel.setLastName(user.getLastName());
        userModel.setEmail(user.getEmail());
        userModel.setBirthDate(user.getBirthDate());
        userModel.setActive(user.getActive());
        return userModel;
    }
}
