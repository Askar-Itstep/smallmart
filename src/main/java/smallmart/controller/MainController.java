package smallmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import smallmart.model.*;
import smallmart.repository.CartRepo;
import smallmart.repository.ItemRepo;
import smallmart.repository.ProductRepo;
import smallmart.repository.UserRepo;
import smallmart.service.Bundle;

import javax.servlet.http.HttpSession;
import java.util.*;

import static java.lang.Thread.sleep;

@Controller
public class MainController {
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductRepo productRepo;

    @GetMapping(value = "/")
    public String index(Model model){
        return "greeting";
    }

    @GetMapping("/main")    //по нажат. на <main page> - вывод предст. со списком продукции
    public String main(HttpSession session, Model model){
        List<Product> products = (List<Product>) productRepo.findAll();
        model.addAttribute("size", products.size());
        model.addAttribute("products", products);
        User user = (User) session.getAttribute("user");
        if(user == null){
            user = new User();
            session.setAttribute("user", user);
        }
        model.addAttribute("user", user);   //вместо unknown
        return "main";
    }


    @Transactional
    @PostMapping("/main")   //обработ. формы-по нажат. <Start order>
    public  String itemPutCart(HttpSession session, @RequestBody  String itemsId){    //form
                            //System.out.println(itemsId);
        if(itemsId == null){
            System.out.println("items is NULL");
            return "error_page";
        }
                            //продукы корзины
        List<Product> products = getProducts(itemsId);

        User user = (User) session.getAttribute("user");    //для повторного захода
        if(user.getUsername() == null) {
            user = new User();  //заполн. осталь. полей и запись в БД - при оформл. заказа в форме Cart
            user.setUserName("client"); //будет изменено
            user.setRoles(Collections.singleton(Role.USER));
        }
        userRepo.save(user);
        Date now = (Date) session.getAttribute("now");
        if(now == null) {
            now = new Date();
            session.setAttribute("now", now);
        }
        Optional<Cart> optionalCart = cartRepo.findCartByUserAndDateAfter(user, now);//в принципе не обяз.-кажд. user уникален.
        //а)-очищать корзину до оконч. покупок
        optionalCart.ifPresent(cart -> cartRepo.delete(cart));

        List<Item> items = (List<Item>) session.getAttribute("items");
        if(items == null)
            items = new ArrayList<>();
        for (Product product : products) {
            Item item = new Item(product);
            items.add(item);
        }
        itemRepo.saveAll(items);
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart == null){
            cart = new Cart(items, user);
            cart.setDate(now);
        }
        else
            cart.setItems(items);
                                //System.out.println(items.size());
        Cart finalCart = cart;
        items.forEach(i->i.setCart(finalCart));
        user.setCart(cart);
        cartRepo.save(cart);    //б)-заполнять корзину снова
        userRepo.updateCartByUserId(cart, user.getId());

        session.setAttribute("user", user);
        session.removeAttribute("items");
        session.setAttribute("items", items);
        session.removeAttribute("cart");
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

//---------------------------------myParsing-------------------------------------
    private List<Product> getProducts(@RequestBody String itemsId) {
        List<String> strings = getStrings(itemsId);
        int indexSeparator = strings.indexOf("itemId");
        List<Bundle> bundles = new ArrayList<>();
        for (int j = 0, i = indexSeparator; i < strings.size(); i++, j++){

            if(!strings.get(j).contains("number")){
                Bundle bundle = new Bundle();
                bundle.setQuantity(Integer.valueOf(strings.get(j)));
                                                //System.out.println("number: " + strings.get(j)+" , "+"j: "+j);
                bundles.add(bundle);
            }
            if(!strings.get(i).contains("itemId")){
                Bundle bundle = bundles.get(bundles.size()-1);
                bundle.setItemId(Long.valueOf(strings.get(i)));
                                            // System.out.println("itemID: " + strings.get(i)+" , "+"i: "+i);
            }
        }
        List<Product> products = new ArrayList<>(); // продукты для отображения
        for (Bundle bundle : bundles) {
            for(int i =0; i < bundle.getQuantity(); i++){
                products.add(productRepo.findById(bundle.getItemId()).get());
            }
        }
        return products;
    }

    private List<String> getStrings(@RequestBody String itemsId) {
        String[] arrStr = itemsId.split("[&]");
        String[] newArray = Arrays.copyOfRange(arrStr, 0, arrStr.length-1); //отбросить _csrf
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < newArray.length; i++){
            String[] tempArr = arrStr[i].split("[=]");
            strings.addAll(Arrays.asList(tempArr));
        }
        return strings;
    }


}
