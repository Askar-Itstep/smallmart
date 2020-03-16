package smallmart.controller;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
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
        model.addAttribute("user", user);
//---------AZURE STORAGE --------IMAGE PRODUCT---------------------
        String path = "https://springsmallmart.blob.core.windows.net/img";
        session.setAttribute("container", path);
        session.setMaxInactiveInterval(3600);
        return "main";
    }


    @Transactional
    @PostMapping("/main")                   //обработ. формы-по нажат. <Start order>
    public  String itemPutCart(HttpSession session, @RequestBody  String itemsId, @RequestBody String cartJson){
        if(itemsId == null){
            System.out.println("items is NULL");
            return "error_page";        }
        User user = (User) session.getAttribute("user");    //для повторного захода
        if(user.getUsername() == null) {
            user = new User();              //осталь. поля и запись в БД - при оформл. заказа в форме Cart
            user.setUserName("client");     //изменить (для авториз. юзеров)
            user.setRoles(Collections.singleton(Role.USER));
        }
        userRepo.save(user);    //норм. - даже если не купит, корзина должна остаться!
        Date now = (Date) session.getAttribute("now");  //время начала покупок
        if(now == null) {
            now = new Date();
            session.setAttribute("now", now);
        }
        List<Product> products = getProducts(itemsId);  //текущ. продукы корзины
        List<Item> items = new ArrayList<>();   //коллекц. товаров корз. (пуст)
        for (Product product : products) {      //товары добавить в коллекц. товаров
            Item item = new Item(product);
            items.add(item);
        }
        itemRepo.saveAll(items);
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart == null){
            cart = new Cart(items, user);
            cart.setDate(now);
        }
        else {
//            cart.setItems(items);         //надо добавлять, а не устанавл.
            items.forEach(cart::addItems);  //добвал. - работ. и для повторн. захода на гл. стр.
        }
        Cart finalCart = cart;          //не лишнее!!!!
        items.forEach(i->i.setCart(finalCart)); //долж. быть идентич.со стр.101
        cartRepo.save(cart);    //корз. сохр. по клику "купить"

        user.setCart(cart);
        userRepo.updateCartByUserId(cart, user.getId());

        session.setAttribute("user", user);
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
