package com.cmas.systems.user.config;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.cmas.systems.user.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    public UserRepository userRepository;

    // @Bean
    // public Class<?> controllerClass() {
    //     return UserController.class;
    // }

    @Override
    public void run(String... args) throws Exception {
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        // userRepository.deleteAll();
         
        // User maria = new User(null, "Maria", "Brown", "maria@gmail.com", sdf.parse("08/05/1985"), true);
        // User alex = new User(null, "Alex", "Green", "alex@gmail.com", sdf.parse("16/07/2001"), false);
        // User bob = new User(null, "Bob", "Grey", "bob@gmail.com", sdf.parse("20/02/1998"), true);

        // userRepository.saveAll(Arrays.asList(maria, alex, bob));
    }
}
