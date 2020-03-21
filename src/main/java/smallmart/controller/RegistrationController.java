package smallmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import smallmart.model.Role;
import smallmart.model.User;
import smallmart.repository.UserRepo;
import smallmart.service.UserService;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String username, @RequestParam String password,
                          @RequestParam String email, Map<String, Object> model){
    User user = new User(username, password, email);//в классе User много полей для формы (@ModelAttr - не сраб.)
        if(!userService.addUser(user)){
            model.put("message", "User exist!");
            return "registration";
        }
//        if(username.isEmpty()||username.equals("")||password.equals("") || password.isEmpty()){
//            model.put("message", "No register empty!");
//            return "registration";
//        }
//        User user = new User(username, password);

        return "redirect:/login";
    }
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivated = userService.activateUser(code);
        if(isActivated){
            model.addAttribute("message", "User successefully activated!");
        }else {
            model.addAttribute("message", "Activation code is not found!");
        }
        return "login";
    }
}
