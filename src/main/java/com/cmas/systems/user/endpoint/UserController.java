package com.cmas.systems.user.endpoint;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmas.systems.user.domain.User;
import com.cmas.systems.user.domain.assembler.UserModelAssembler;
import com.cmas.systems.user.domain.models.UserModel;
import com.cmas.systems.user.service.UserService;

/**
 * This is the Controller Class of this project, where requests are handled
 * @see UserService
 * @author André Salier
 */

@RestController
@RequestMapping("/api/v4/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @GetMapping
    public ResponseEntity<List<UserModel>> findAll() {
        List<UserModel> userModelList = userService.findAll()
                .stream().map(userModelAssembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok().body(userModelList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserModel> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return new ResponseEntity<>(userModelAssembler.toModel(user), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserModel> insert(@RequestBody User user) {
        user.setId(userService.insert(user).getId());
        return new ResponseEntity<>(userModelAssembler.toModel(user), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id) {
        User obj = userService.findById(id);
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(obj);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserModel> update(@RequestBody User user, @PathVariable Long id) {
        user.setId(id);
        user = userService.update(user);
        return new ResponseEntity<>(userModelAssembler.toModel(user), HttpStatus.OK);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setUserModelAssembler(UserModelAssembler userModelAssembler) {
        this.userModelAssembler = userModelAssembler;
    }
}

