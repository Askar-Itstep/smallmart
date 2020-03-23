package smallmart.controller;

import com.azure.core.annotation.Get;
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
import smallmart.service.CartService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public String cartEditForm(Model model, HttpSession session) {

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            model.addAttribute("error", "Запрошенная корзина отсутствует!");
            return "error_page";
        }
        model.addAttribute("cart", cart);
        return "cart";
    }

    @Transactional
    @GetMapping("{itemId}")  //remove from cart
    public String removeFromCart(HttpSession session, @PathVariable Long itemId, Model model) {
        boolean flagRemove = cartService.remove(itemId);
        if (!flagRemove) {
            model.addAttribute("error", "Такой продукт отсутсвтует!");
            return "error_page";
        }
        Cart cart = cartService.getCart(itemId);
        model.addAttribute("cart", cart);
        session.removeAttribute("cart");
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @PostMapping
    public String beginBuy() {//в предст. пустая форма - без исп. модели
//
        return "redirect:/myOrder"; //to OrderControll
    }
}
