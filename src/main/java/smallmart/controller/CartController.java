package smallmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import smallmart.model.Cart;
import smallmart.model.Item;
import smallmart.model.Product;
import smallmart.repository.CartRepo;
import smallmart.repository.ItemRepo;
import smallmart.repository.UserRepo;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private ItemRepo itemRepo;

    @GetMapping
    public String cartEditForm(Model model){
        List<Cart> carts = (List<Cart>) cartRepo.findAll();
        if(carts == null || carts.size()==0){
            model.addAttribute("error", "Cart is EMPTY!");
            return "error_page";
        }
        Long maxId = carts.get(carts.size()-1).getId();
//        List<Product> products = new ArrayList<>();
        Optional<Cart> cart = cartRepo.findById(maxId);
        if(!cart.isPresent()){
            model.addAttribute("error", "Запрошенная корзина отсутствует!");
            return "error_page";
        }
//        List<Item> items = cart.get().getItems();
        model.addAttribute("cart", cart.get());
//        items.forEach(i->products.add(i.getProduct()));   //и без этого работ.?????????
//        model.addAttribute("products", products);
        return "cart";
    }

    @Transactional
    @GetMapping("{itemId}")  //remove from cart
    public String removeFromCart(HttpSession session, @PathVariable Long itemId, Model model){
        Optional<Item> item = itemRepo.findById(itemId);
        if(!item.isPresent()){
            model.addAttribute("error", "Такой продукт отсутсвтует!");
            return "error_page";
        }
        itemRepo.delete(item.get());
        Cart cart = item.get().getCart();
        List<Item> items = itemRepo.findAllByCart(cart);
        cart.setItems(items);
        cartRepo.updateCostCart(cart.getId(),  cart.getCost());
//        model.addAttribute("cart", cart);
//        session.removeAttribute("items");
//        session.removeAttribute("cart");
//        session.setAttribute("items", items);
//        session.setAttribute("cart", cart);
        return "redirect:/cart";//"cart";
    }

    @PostMapping
    public String beginBuy( ){//в предст. пустая форма - без исп. модели
//                           Model model, HttpSession session, @RequestBody String itemsId){    //зачем?

//        List<String> strProducts = getStrings(itemsId);
//        List<Integer> nums = new ArrayList<>();
//        strProducts.stream().filter(s->s.matches("\\d*")).forEach(d->nums.add(Integer.valueOf(d)));

        return "redirect:/myOrder"; //to OrderControll
    }
}
