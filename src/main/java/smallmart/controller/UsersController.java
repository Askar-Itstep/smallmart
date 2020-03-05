package smallmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import smallmart.model.Role;
import smallmart.model.User;
import smallmart.repository.UserRepo;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//вход админа на стр. userList, userEdit
@Controller
@RequestMapping("/user")    //обобщ. путь (маппиг) для всех членов класса
//@PreAuthorize("hasAuthority('ADMIN')")
public class UsersController {
    @Autowired
    UserRepo userRepo;

    @GetMapping //="/user"
    public String userList(Model model){
        model.addAttribute("users", userRepo.findAll());
        return "userList";
    }
    //edit!
    @GetMapping("{user}")    //путь из формы userList
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";  //и на форму userEdit
    }

    //из формы userEdit
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user){
        user.setUserName(username);
        //все возм. роли
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();    //очистить список ролей юзера
                        //и установ. новые
        for (String s : form.keySet()) {    //все ключи формы (имя, роль + невидим: id, _csrf)- зачем?????
//            System.out.println("role: " + s);
            if(roles.contains(s)){
                user.getRoles().add(Role.valueOf(s));   //так добавляемая роль предопределенная - то нет проблем!
            }
        }
        userRepo.save(user);
        return "redirect:/user";
    }
}
