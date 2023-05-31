package io.github.bialekmm.bookingapp.controller;

import io.github.bialekmm.bookingapp.dto.UserDto;
import io.github.bialekmm.bookingapp.entity.UserEntity;
import io.github.bialekmm.bookingapp.service.AgeService;
import io.github.bialekmm.bookingapp.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;
    private final AgeService ageService;
    public UserController(UserService userService, AgeService ageService) {
        this.userService = userService;
        this.ageService = ageService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UserEntity user = new UserEntity();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@ModelAttribute("user") UserDto userDto, BindingResult result, Model model){
        UserEntity existingUser = userService.findByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", "!email", "There is already an account registered with the same email");
        }
        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }
        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model, Authentication authentication){
        List<UserDto> users = userService.findAllUsers();
        List<Integer> ages = users.stream().
                map(user -> ageService.ageFromBirthDate(user.getEmail(), user.getBirthDate())).
                collect(Collectors.toList());
        model.addAttribute("authentication",authentication);
        model.addAttribute("users", users);
        model.addAttribute("ages", ages);
        return "users";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
