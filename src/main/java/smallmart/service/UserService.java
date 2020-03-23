package smallmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import smallmart.model.MailSender;
import smallmart.model.Role;
import smallmart.model.User;
import smallmart.repository.UserRepo;

import java.util.*;
import java.util.stream.Collectors;

//Service - это aлиас для @Component
//Class- чтобы связать сервис UserService со Spring Security (поле userService into class WebSecur.)

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<List<User>> optionalUsers = userRepo.findByUserName(s);
        return optionalUsers.<UserDetails>map(users -> users.get(0)).orElse(null);
    }

    public List<User> findAll() {
        return (List<User>) userRepo.findAll();
    }

    public boolean addUser(User user){
//        Optional<List<User>> usersOption = userRepo.findByUserName(user.getUserName()); //поиск по имени?
        Optional<User> userOption = userRepo.findFirstByUserNameAndEmail(user.getUserName(), user.getEmail());  //хотя бы по 2ум парам.
        if(userOption.isPresent()){
            return false;
        }
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(user);

        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format("Hello, %s!\n" +
                                    "Wellcome to smallmart. Please, visit next link: http://localhost:8080/activate/%s",
                            user.getUsername(),
                            user.getActivationCode()
                    );
            mailSender.send(user.getEmail(), "Activation code", message);
        }
        return true;
    }

    public void saveUser(String username, Map<String, String> form, User user) {
        user.setUserName(username);
        //все возм. роли
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();    //очистить список ролей юзера
        //и установ. новые
        for (String s : form.keySet()) {    //все ключи формы (имя, роль + невидим: id, _csrf)
            if(roles.contains(s)){
                user.getRoles().add(Role.valueOf(s));   //так добавляемая роль предопределенная - то нет проблем!
            }
        }
        userRepo.save(user);
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);
        if(user == null){
            return false;
        }
        user.setActivationCode(null);
        userRepo.save(user);
        return  true;
    }

    public Long updateUserRepo(Map<String, String> form, User user) {
        Long userId = user.getId();

        for (Map.Entry<String, String> entry : form.entrySet()) {
            if (entry.getKey().contains("name"))
                userRepo.updateUsername(entry.getValue(), userId);
            else if (entry.getKey().contains("email"))
                userRepo.updatePassword(entry.getValue(), userId);
            else if (entry.getKey().contains("phone"))
                userRepo.updateUserPhone(entry.getValue(), userId);
            else if (entry.getKey().contains("address"))
                userRepo.updateUserAddrress(entry.getValue(), userId);
        }
        return userId;
    }

    public Optional<User> findById(Long userId) {
        return userRepo.findById(userId);
    }
}
