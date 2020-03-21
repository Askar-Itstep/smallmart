package smallmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import smallmart.model.Role;
import smallmart.model.User;
import smallmart.service.UserService;

import java.util.Map;

@Controller
@RequestMapping("/user")    //обобщ. путь (маппиг) для всех членов класса

public class UsersController {
    @Autowired
//    UserRepo userRepo;
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")    //путь из формы userList
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";  //и на форму userEdit
    }

    //из формы userEdit
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user){
        userService.saveUser(username, form, user);
        return "redirect:/user";
    }

//    @GetMapping("profile")
//    public String getProfile(Model model, @AuthenticationPrincipal User user){
//        model.addAttribute("username", user.getUsername());
//        model.addAttribute("email", user.getEmail());
//
//        return "profile";
//    }
//    @PostMapping("profile")
//    public String updateProfile(@AuthenticationPrincipal User user,
//                                @RequestParam String password,
//                                @RequestParam String email){
//        return "redirect:/user/profile";
//
//    }
}
