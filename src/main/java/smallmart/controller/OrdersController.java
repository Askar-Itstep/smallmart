package smallmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import smallmart.model.*;
import smallmart.repository.CartRepo;
import smallmart.repository.ItemRepo;
import smallmart.repository.UserRepo;

import javax.persistence.Transient;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class OrdersController {
    @Autowired
    CartRepo cartRepo;
    @Autowired
    ItemRepo itemRepo;
    @Autowired
    UserRepo userRepo;

    //вход employee, manager на стр. orderList, orderEdit
//все заказы  + фильтр по клиенту
    @GetMapping("/order")
    public String findByName(
            @RequestParam(required = false, defaultValue = "") String clientName,
            Model model){
        List<Cart> carts = new ArrayList<>();   //найдем корзины
        List<Item> items = new ArrayList<>();   //найдем вещи в этих корзинах

        if(clientName != null && !clientName.equals("") && !clientName.equals(" ")){
            List<User> users = userRepo.findByUserName(clientName); //.get(0); //пусть будет 1-ый
            for (User user : users) {
                List<Cart> carts1 = cartRepo.findAllByUser(user);
                if (carts1 != null && carts1.size() > 0){
                    carts.addAll(carts1);
                }
            }
        }
        else {
            carts = (List<Cart>) cartRepo.findAll();
        }
        model.addAttribute("carts", carts);
        model.addAttribute("clientname", clientName);
        return "orderList";
    }
    @GetMapping("/myOrder") //это просто форма-модель не нужна
    public String createContactus(){
        return "myOrder";
    }

    @Transactional
    @PostMapping("/myOrder")    //по нажат. кнопки в "myOrder"
    public String toBuy(HttpSession session, @RequestParam Map<String, String> form, Model model){
        User user = (User)session.getAttribute("user");
        Date date = (Date) session.getAttribute("now");

        if(user.getUsername() == null || user.getUsername().equals("")) {
            model.addAttribute("error", " user is NULL!");
            return "error_page";
        }
        if(date == null || date.getTime() == 0) {
            model.addAttribute("error", " date is NULL!");
            return "error_page";
        }

        Long userId = user.getId();

        for (Map.Entry<String, String> entry : form.entrySet()) {
            if(entry.getKey().contains("name"))
                userRepo.updateUsername(entry.getValue(), userId);
            else if(entry.getKey().contains("email"))
                userRepo.updatePassword(entry.getValue(), userId);
            else if(entry.getKey().contains("phone"))
                userRepo.updateUserPhone(entry.getValue(), userId);
            else if(entry.getKey().contains("address"))
                userRepo.updateUserAddrress(entry.getValue(), userId);
        }
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart.getItems()==null || cart.getItems().size()==0){
            model.addAttribute("error", "items not found!");
            return "error_page";
        }
        model.addAttribute("cart", cart);
        Optional<User> userOptional = userRepo.findById(userId);
        if(!userOptional.isPresent()){
            model.addAttribute("error", "user not found!");
            return "error_page";
        }
        model.addAttribute("items", cart.getItems());
        model.addAttribute("items_size", cart.getItems().size());
        user = userOptional.get();
        model.addAttribute("user", user);
        return "confirm";
    }

    @Transactional
    @PostMapping("/buy")
    public String buy(@RequestParam Map<String, String>  form, Model model, HttpSession session){
            Cart cart = new Cart();
            for (Map.Entry<String, String> item:form.entrySet())
                if (item.getKey().contains("cartId")) {
                    Long cartId = Long.valueOf(item.getValue());
                    Optional<Cart> optionalCart = cartRepo.findById(cartId);
                    if (optionalCart.isPresent())
                        cart = optionalCart.get();
                }
            cart.setQueye(true);
            cartRepo.updateQueue(cart.getId(), true);   //для манагера заказа
        model.addAttribute("cart", cart);
        session.invalidate();
        return "buyBuy";
    }
}
