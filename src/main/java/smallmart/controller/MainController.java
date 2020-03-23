package smallmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import smallmart.service.CartService;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Thread.sleep;

@Controller
@ControllerAdvice
public class MainController {
    @Autowired
    CartService cartService;
//    private ItemRepo itemRepo;
//    @Autowired
//    private CartRepo cartRepo;
//    @Autowired
//    private UserRepo userRepo;
//    @Autowired
//    private ProductRepo productRepo;

    @GetMapping(value = "/")
    public String index(Model model) {
        return "greeting";
    }

    @GetMapping("/main")    //по нажат. на <main page> - вывод предст. со списком продукции
    public String main(HttpSession session, Model model
            ,@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC ) Pageable pageable
    ) {
//        List<Product> products = (List<Product>) productRepo.findAll();
        Page page =  cartService.getAll(pageable);
//                productRepo.findAll((org.springframework.data.domain.Pageable) pageable);//Products
//        model.addAttribute("size", products.size());
//        model.addAttribute("products", products);

        model.addAttribute("size", page.getTotalElements());
        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
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
        user = cartService.getUser(user);
        Date now = (Date) session.getAttribute("now");  //время начала покупок
        if (now == null) {
            now = new Date();
            session.setAttribute("now", now);
        }
//        List<Product> products = getProducts(itemsId);  //a) for form submit
        List<Item> items = cartService.getItems(parts);

        Cart cart = cartService.getSessionCart(session, user, now, items);

        session.setAttribute("user", user);
        session.removeAttribute("cart");
        session.setAttribute("cart", cart);
//        return "redirect:/cart";
        return new ResponseEntity(HttpStatus.OK);
    }


    //---------------------------------myParsing-------------------------------------


//    private List<Product> getProducts(String itemsId) { //form submit
//        List<String> strings = getStrings(itemsId);
//        int indexSeparator = strings.indexOf("itemId");
//        List<Bundle> bundles = new ArrayList<>();
//        for (int j = 0, i = indexSeparator; i < strings.size(); i++, j++) {
//
//            if (!strings.get(j).contains("number")) {
//                Bundle bundle = new Bundle();
//                bundle.setQuantity(Integer.valueOf(strings.get(j)));
//                bundles.add(bundle);
//            }
//            if (!strings.get(i).contains("itemId")) {
//                Bundle bundle = bundles.get(bundles.size() - 1);
//                bundle.setItemId(Long.valueOf(strings.get(i)));
//            }
//        }
//        List<Product> products = new ArrayList<>(); // продукты для отображения
//        for (Bundle bundle : bundles) {
//            for (int i = 0; i < bundle.getQuantity(); i++) {
//                products.add(productRepo.findById(bundle.getItemId()).get());
//            }
//        }
//        return products;
//    }
//
//    private List<String> getStrings(String itemsId) {
//        String[] arrStr = itemsId.split("[&]");
//        String[] newArray = Arrays.copyOfRange(arrStr, 0, arrStr.length - 1); //отбросить _csrf
//        List<String> strings = new ArrayList<>();
//        for (int i = 0; i < newArray.length; i++) {
//            String[] tempArr = arrStr[i].split("[=]");
//            strings.addAll(Arrays.asList(tempArr));
//        }
//        return strings;
//    }
}
