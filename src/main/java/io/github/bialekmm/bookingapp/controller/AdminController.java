package io.github.bialekmm.bookingapp.controller;

import io.github.bialekmm.bookingapp.dto.UserDto;
import io.github.bialekmm.bookingapp.entity.UserEntity;
import io.github.bialekmm.bookingapp.repository.UserRepository;
import io.github.bialekmm.bookingapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;


    public AdminController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/users/admin")
    public String admin(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam String email){
        UserEntity user = userRepository.findByEmail(email);
        user.getRoles().clear();
        userService.deleteUser(user.getId());
        return "redirect:/users";
    }

}
