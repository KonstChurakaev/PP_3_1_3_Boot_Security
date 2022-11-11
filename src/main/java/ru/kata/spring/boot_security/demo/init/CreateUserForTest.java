package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CreateUserForTest implements ApplicationRunner {


    private UserService userService;
    private PasswordEncoder passwordEncoder;

    private RoleService roleService;

    @Autowired
    public CreateUserForTest(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public void run(ApplicationArguments args) {
        List<User> users = userService.getAllUser();

        if (users.isEmpty()) {
            Set<Role> userRole = new HashSet<>();
            Set<Role> adminRole = new HashSet<>();
            Role role1 = new Role("ROLE_USER");
            Role role2 = new Role("ROLE_ADMIN");

            roleService.addRole(role1);
            roleService.addRole(role2);

            userRole.add(role1);
            adminRole.add(role1);
            adminRole.add(role2);

            userService.addUser(new User("Ivan", "Ivanov", (byte) 20, "admin@admin"
                    , passwordEncoder.encode("1"), adminRole));
            userService.addUser(new User("Oleg", "Ivanov", (byte) 20, "user"
                    , passwordEncoder.encode("1"), userRole));

        }
    }
}