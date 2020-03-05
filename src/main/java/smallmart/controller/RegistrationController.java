package smallmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import smallmart.model.Role;
import smallmart.model.User;
import smallmart.repository.UserRepo;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String username, @RequestParam String password, Map<String, Object> model){
        User userFromDb = userRepo.findByUserName(username).get(0);
        if(userFromDb != null){
            model.put("message", "User exist!");
            return "registration";
        }
        if(username.isEmpty()||username.equals("")||password.equals("") || password.isEmpty()){
            //System.out.println("No register empty!");
            model.put("message", "No register empty!");
            return "registration";
        }
        System.out.println("Regisration!");
        User user = new User(username, password);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        return "redirect:/login";
    }
}
