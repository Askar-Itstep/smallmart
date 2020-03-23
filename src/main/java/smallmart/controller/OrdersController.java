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
import smallmart.service.CartService;
import smallmart.service.UserService;

import javax.persistence.Transient;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class OrdersController {
    @Autowired
    CartService cartService;
    //    CartRepo cartRepo;
//    @Autowired
//    ItemRepo itemRepo;
//    @Autowired
//    UserRepo userRepo;
    @Autowired
    UserService userService;

    //вход employee, manager на стр. orderList, orderEdit
//все заказы  + фильтр по клиенту
    @GetMapping("/order")
    public String findByName(
            @RequestParam(required = false, defaultValue = "") String clientName,
            Model model) {

        List<Cart> carts = cartService.getCartsByName(clientName);
        model.addAttribute("carts", carts);
        model.addAttribute("clientname", clientName);
        return "orderList";
    }


    @GetMapping("/myOrder") //это просто форма-модель не нужна
    public String createContactus() {
        return "myOrder";
    }


    @Transactional
    @PostMapping("/myOrder")    //по нажат. кнопки в "myOrder"
    public String toBuy(HttpSession session, @RequestParam Map<String, String> form, Model model) {
        User user = (User) session.getAttribute("user");
        Date date = (Date) session.getAttribute("now");

        if (user.getUsername() == null || user.getUsername().equals("")) {
            model.addAttribute("error", " user is NULL!");
            return "error_page";
        }
        if (date == null || date.getTime() == 0) {
            model.addAttribute("error", " date is NULL!");
            return "error_page";
        }
        Long userId = userService.updateUserRepo(form, user);
        Cart cart = (Cart) session.getAttribute("cart");

        Optional<User> userOptional = userService.findById(userId);
        if (cart.getItems() == null || cart.getItems().size() == 0) {
            model.addAttribute("error", "items not found!");
            return "error_page";
        }
        if (!userOptional.isPresent()) {
            model.addAttribute("error", "user not found!");
            return "error_page";
        }
        model.addAttribute("cart", cart);
        model.addAttribute("items", cart.getItems());
        model.addAttribute("items_size", cart.getItems().size());
        user = userOptional.get();
        model.addAttribute("user", user);
        return "confirm";
    }


    @Transactional
    @PostMapping("/buy")
    public String buy(@RequestParam Map<String, String> form, Model model, HttpSession session) {
        Cart cart = new Cart();
        cart = cartService.getCartByBuy(form, cart);
        model.addAttribute("cart", cart);
        session.invalidate();
        return "buyBuy";
    }
}
