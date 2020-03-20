package smallmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.util.function.Tuple2;
import smallmart.model.*;
import smallmart.repository.CartRepo;
import smallmart.repository.ItemRepo;
import smallmart.repository.ProductRepo;
import smallmart.repository.UserRepo;
import smallmart.service.Bundle;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Thread.sleep;

@Controller
@ControllerAdvice
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
    public String index(Model model) {
        return "greeting";
    }

    @GetMapping("/main")    //по нажат. на <main page> - вывод предст. со списком продукции
    public String main(HttpSession session, Model model) {
        List<Product> products = (List<Product>) productRepo.findAll();
        model.addAttribute("size", products.size());
        model.addAttribute("products", products);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            session.setAttribute("user", user);
        }
        model.addAttribute("user", user);
//---------AZURE STORAGE --------IMAGE PRODUCT---------------------
        String path = "https://springsmallmart.blob.core.windows.net/img";
        session.setAttribute("container", path);
        session.setMaxInactiveInterval(600);
        return "main";
    }


    @Transactional
    @RequestMapping(value = "/main", method = RequestMethod.POST,
            headers = "Accept=*/*", produces = "application/json", consumes = "application/json")
    public @ResponseBody
//    String                                            //old - submit forms
    ResponseEntity  itemPutCart(HttpSession session, @RequestBody Part[] parts) { //@RequestBody String itemsId    //old - submit forms
        if (parts == null || parts.length == 0) {    //(itemsId == null) {
            System.out.println("items is NULL");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);//"error_page";
        }
        User user = (User) session.getAttribute("user");    //для повторного захода
        if (user.getUsername() == null) {
            user = new User();              //осталь. поля и запись в БД - при оформл. заказа в форме Cart
            user.setUserName("client");     //изменить (для авториз. юзеров)
            user.setRoles(Collections.singleton(Role.USER));
        }
        userRepo.save(user);    //норм. - даже если не купит, корзина должна остаться
        Date now = (Date) session.getAttribute("now");  //время начала покупок
        if (now == null) {
            now = new Date();
            session.setAttribute("now", now);
        }
//        List<Product> products = getProducts(itemsId);  //a) for form submit
        List<Product> products = getProducts2(parts);      //b) ajax
        List<Item> items = new ArrayList<>();   //коллекц. товаров корз. (пуст)
        for (Product product : products) {      //товары добавить в коллекц. товаров
            Item item = new Item(product);
            for (Part part : parts) {
                if(product.getId() == Long.valueOf(part.getProdId())){
                    for (Integer i = 0; i < part.getCount(); i++) {
                        items.add(item);
                    }
                }
            }
        }
        itemRepo.saveAll(items);

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart(items, user);
            cart.setDate(now);
        } else {                    // и для повторн. захода на гл. стр.
//            cart.setItems(items);         //надо не устанавл.
            items.forEach(cart::addItems);  //а добавлять
            cart.resetCost();
        }
        Cart finalCart = cart;          //не лишнее!!!!
        items.forEach(i -> i.setCart(finalCart)); //долж. быть идентич.со стр.101
        cartRepo.save(cart);    //корз. сохр. по клику "купить"

        user.setCart(cart);
        userRepo.updateCartByUserId(cart, user.getId());

        session.setAttribute("user", user);
        session.removeAttribute("cart");
        session.setAttribute("cart", cart);
//        return "redirect:/cart";
        return new ResponseEntity(HttpStatus.OK);
    }


    //---------------------------------myParsing-------------------------------------
    private List<Product> getProducts2(Part[] parts) {  //for ajax
        List<Product> products = new ArrayList<>();
        for (Part part : parts) {
            Long prodId = Long.valueOf((part.getProdId()));
            Optional<Product> optional = productRepo.findById(prodId);
            if (optional.isPresent()) {
                products.add(optional.get());
            }
        }
        return products;
    }

    private List<Product> getProducts(String itemsId) { //form submit
        List<String> strings = getStrings(itemsId);
        int indexSeparator = strings.indexOf("itemId");
        List<Bundle> bundles = new ArrayList<>();
        for (int j = 0, i = indexSeparator; i < strings.size(); i++, j++) {

            if (!strings.get(j).contains("number")) {
                Bundle bundle = new Bundle();
                bundle.setQuantity(Integer.valueOf(strings.get(j)));
                bundles.add(bundle);
            }
            if (!strings.get(i).contains("itemId")) {
                Bundle bundle = bundles.get(bundles.size() - 1);
                bundle.setItemId(Long.valueOf(strings.get(i)));
            }
        }
        List<Product> products = new ArrayList<>(); // продукты для отображения
        for (Bundle bundle : bundles) {
            for (int i = 0; i < bundle.getQuantity(); i++) {
                products.add(productRepo.findById(bundle.getItemId()).get());
            }
        }
        return products;
    }

    private List<String> getStrings(String itemsId) {
        String[] arrStr = itemsId.split("[&]");
        String[] newArray = Arrays.copyOfRange(arrStr, 0, arrStr.length - 1); //отбросить _csrf
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < newArray.length; i++) {
            String[] tempArr = arrStr[i].split("[=]");
            strings.addAll(Arrays.asList(tempArr));
        }
        return strings;
    }
}
