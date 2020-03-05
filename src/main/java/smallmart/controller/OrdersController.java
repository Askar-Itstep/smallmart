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
//            List<Item> finalItems = items;    //без этого работает!?-откуда cart берет items??????
//            carts.forEach(c-> finalItems.addAll(itemRepo.findAllByCart(c)));
        }
        else {
            carts = (List<Cart>) cartRepo.findAll();
//            carts.forEach(c-> System.out.println(c.toString()));
            items = (List<Item>) itemRepo.findAll();
//            List<Item> finalItems = items;
//            carts.forEach(c->{
//                List<Item> itemList = new ArrayList<>();
//                finalItems.stream().filter(i->i.getCart().getId()==c.getId()).forEach(i->itemList.add(i));
//                c.setItems(itemList);
//            });                      //без этого тоже работает!???
        }
//        model.addAttribute("items", itemList);
        model.addAttribute("carts", carts);
        model.addAttribute("clientname", clientName);
        return "orderList";
    }
    //приходит из MainControll (redirect)
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
        Long userId = user.getId();//userRepo.getMaxId();-Так нельзя при подключ. неск. юзеров
//        System.out.println("date (orderControll: " + date);

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
        Cart cart = new Cart();
        List<Item> items = new ArrayList<>();
                                            //findCartByUserAndDateAfter(user, date);-смысла нет -
        Optional<Cart> optionalCart = cartRepo.findByUser(user);//кажд. запись даже того же юзера уникаль.+стаб. работ.
        if(optionalCart.isPresent()) {
             cart = optionalCart.get();
             items = itemRepo.findAllByCart(cart);
        }
        else {
            model.addAttribute("error", "cart not found!");
            return "error_page";
        }
        if(items == null || items.size()==0){
            model.addAttribute("error", "items not found!");
            return "error_page";
        }
        model.addAttribute("cart", cart);
        Optional<User> userOptional = userRepo.findById(userId);
        if(!userOptional.isPresent()){
            model.addAttribute("error", "user not found!");
            return "error_page";
        }
        model.addAttribute("items", items);
        model.addAttribute("items_size", items.size());
        user = userOptional.get();
        model.addAttribute("user", user);
        return "confirm";
    }

    @Transactional
    @PostMapping("/buy")
    public String buy(@RequestParam Map<String, String>  form, Model model){
            Cart cart = new Cart();
            for (Map.Entry<String, String> item:form.entrySet()){
//                System.out.printf("Key: %s  Value: %s \n", item.getKey(), item.getValue());
                if(item.getKey().contains("cartId")){
                    Long cartId = Long.valueOf(item.getValue());
                    Optional<Cart> optionalCart = cartRepo.findById(cartId);
                    if(optionalCart.isPresent())
                        cart = optionalCart.get();
                }
            }
            cart.setQueye(true);
            cartRepo.updateQueue(cart.getId(), true);   //для манагера заказа
        model.addAttribute("cart", cart);
        return "buyBuy";
    }
}
