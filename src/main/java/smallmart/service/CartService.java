package smallmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import smallmart.model.*;
import smallmart.repository.CartRepo;
import smallmart.repository.ItemRepo;
import smallmart.repository.ProductRepo;
import smallmart.repository.UserRepo;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class CartService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private ItemRepo itemRepo;


    public boolean remove(Long itemId) {
        Optional<Item> itemOptional = itemRepo.findById(itemId);//itemRepo.findById(itemId);
        if(!itemOptional.isPresent()){
           return false;
        }

        return true;
    }

    public Cart getCart(Long itemId) {
        Optional<Item> itemOptional = itemRepo.findById(itemId);
        Item item = itemOptional.get();
        Cart cart = itemOptional.get().getCart();   //=(Cart) session.getAttribute("cart");
        cart.removeItems(item);
        int flag = cart.resetCost();    //if 0 => cost = 0..
        itemRepo.delete(itemOptional.get());
        return cart;
    }

    public Page getAll(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    public User getUser(User user) {
        if (user.getUsername() == null) {
            user = new User();              //осталь. поля и запись в БД - при оформл. заказа в форме Cart
            user.setUserName("client");     //изменить (для авториз. юзеров)
            user.setRoles(Collections.singleton(Role.USER));
        }
        userRepo.save(user);    //норм. - даже если не купит, корзина должна остаться
        return user;
    }

    public List<Item> getItems(Part[] parts) {
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
        return items;
    }
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

    public Cart getSessionCart(HttpSession session, User user, Date now, List<Item> items) {
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
        return cart;
    }

    public List<Cart> getCartsByName(String clientName) {
        List<Cart> carts = new ArrayList<>();   //найдем корзины
        if(clientName != null && !clientName.equals("") && !clientName.equals(" ")) {
            Optional<List<User>> optionalUsers = userRepo.findByUserName(clientName);

            if (optionalUsers.isPresent()) {        //и для все юзеров Вася
                List<User> users = optionalUsers.get();
                for (User user : users) {           //найти их корзины
                    List<Cart> carts1 = cartRepo.findAllByUser(user);
                    if (carts1 != null && carts1.size() > 0) {
                        carts.addAll(carts1);
                    }
                }
            }
        }
        else {      //если Васянов нет - найти все корзины
            carts = (List<Cart>) cartRepo.findAll();
        }
        return carts;
    }

    public Cart getCartByBuy(Map<String, String> form, Cart cart) {
        for (Map.Entry<String, String> item : form.entrySet())
            if (item.getKey().contains("cartId")) {
                Long cartId = Long.valueOf(item.getValue());
                Optional<Cart> optionalCart = cartRepo.findById(cartId);
                if (optionalCart.isPresent())
                    cart = optionalCart.get();
            }
        cart.setQueye(true);
        cartRepo.updateQueue(cart.getId(), true);   //для манагера заказа
        return cart;
    }
}
