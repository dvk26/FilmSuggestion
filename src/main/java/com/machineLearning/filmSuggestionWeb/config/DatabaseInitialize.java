package com.machineLearning.filmSuggestionWeb.config;


import com.machineLearning.filmSuggestionWeb.enums.GenderEnum;
import com.machineLearning.filmSuggestionWeb.model.RoleEntity;
import com.machineLearning.filmSuggestionWeb.model.UserEntity;
import com.machineLearning.filmSuggestionWeb.repository.RoleRepository;
import com.machineLearning.filmSuggestionWeb.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class DatabaseInitialize implements CommandLineRunner {


    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitialize(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> START INIT DATABASE");
        long countRoles = this.roleRepository.count();
        long countUsers = this.userRepository.count();

        if (countRoles == 0) {
            List<RoleEntity> allRoles = this.roleRepository.findAll();

            RoleEntity adminRole = new RoleEntity();
            adminRole.setCode("ADMIN");
            adminRole.setName("Admin co toan bo quyen han!");

            RoleEntity userRole = new RoleEntity();
            userRole.setCode("USER");
            userRole.setName("Nguoi dung binh thuong");
            allRoles.add(adminRole);
            allRoles.add(userRole);
            this.roleRepository.saveAll(allRoles);
        }

        if (countUsers == 0) {
            UserEntity adminUser = new UserEntity();
            adminUser.setUserName("admin");
            adminUser.setEmail("admin@gmail.com");
//            adminUser.setAddress("227 nguyen van cu, quan 5, tphcm");
//            adminUser.setAge(25L);
//            adminUser.setGender(GenderEnum.FEMALE);

            adminUser.setPassword(this.passwordEncoder.encode("123456"));
            adminUser.setRole(roleRepository.findByCode("ADMIN"));
            this.userRepository.save(adminUser);
        }
        if (countRoles > 0 && countUsers > 0) {
            System.out.println(">>> SKIP INIT DATABASE ~ ALREADY HAVE DATA...");
        } else
            System.out.println(">>> END INIT DATABASE");
    }
}
