package com.cmas.systems.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmas.systems.user.domain.User;
import com.cmas.systems.user.exceptions.SomeFieldNotFilledException;
import com.cmas.systems.user.exceptions.UserNotFoundException;
import com.cmas.systems.user.repository.UserRepository;


/**
 * This is the Service Layer of this project
 * @see UserRepository
 * @author André Salier
 */

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    /**
     * @return All Users from database
     */

    public List<User> findAll() {
        return userRepository.findAll();
    }


    /**
     * @param id
     * @return User with the specified id
     * @throws UserNotFoundException If the id was not found 
     */

    public User findById(Long id) {
        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
    }


    /**
     * @param user
     * @return Saved user
     * @throws SomeFieldNotFilledException If any field has not been filled in correctly
     */

    public User insert(User user) {
        if (checkUserFields(user)) {
            return userRepository.save(user);
        }
        throw new SomeFieldNotFilledException();
    }


    /**
     * Checks that all user fields are correctly filled in
     * @param user
     */

    private boolean checkUserFields(User user) {
        return user.getFirstName() == null ? false
                : user.getLastName() == null ? false
                        : user.getEmail() == null ? false
                                : user.getBirthDate() == null ? false : user.getActive() == null ? false : true;
    }


    /**
     * @param id
     * @return Deleted user
     * @throws UserNotFoundException If the id was not found 
     */

    public void delete(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }


    /**
     * @param newUser
     * @return Updated user
     * @throws UserNotFoundException If the id was not found 
     */

    public User update(User newUser) {
        User obj = findById(newUser.getId());
        updateData(newUser, obj);
        return userRepository.save(newUser);
    }

    
    /**
     * Only updates fields that are filled in
     * @param newObj
     * @param obj
     */

    private void updateData(User newObj, User obj) {
        newObj.setFirstName(newObj.getFirstName() == null ? obj.getFirstName() : newObj.getFirstName());
        newObj.setLastName(newObj.getLastName() == null ? obj.getLastName() : newObj.getLastName());
        newObj.setEmail(newObj.getEmail() == null ? obj.getEmail() : newObj.getEmail());
        newObj.setBirthDate(newObj.getBirthDate() == null ? obj.getBirthDate() : newObj.getBirthDate());
        newObj.setActive(newObj.getActive() == null ? obj.getActive() : newObj.getActive());
    }

    public void setRepo(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
