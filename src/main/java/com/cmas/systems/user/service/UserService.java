package com.cmas.systems.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmas.systems.user.domain.User;
import com.cmas.systems.user.exceptions.SomeFieldNotFilledException;
import com.cmas.systems.user.exceptions.UserNotFoundException;
import com.cmas.systems.user.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
    }

    public User insert(User user) {
        if (checkUserFields(user)) {
            return userRepository.save(user);
        }
        throw new SomeFieldNotFilledException();
    }

    private boolean checkUserFields(User obj) {
        return obj.getFirstName() == null ? false
                : obj.getLastName() == null ? false
                        : obj.getEmail() == null ? false
                                : obj.getBirthDate() == null ? false : obj.getActive() == null ? false : true;
    }

    public void delete(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    public User update(User newObj) {
        User obj = findById(newObj.getId());
        updateData(newObj, obj);
        return userRepository.save(newObj);
    }

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
