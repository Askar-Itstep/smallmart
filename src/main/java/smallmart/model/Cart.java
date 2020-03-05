package smallmart.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static java.util.stream.Stream.of;

@Entity
@Table(name = "carts")
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = {CascadeType.ALL})//mappedBy="cart"
    @JoinColumn(name = "item_id")
    private List<Item> items;

    private double cost;    //стоимость всей корзины

//cascade = {CascadeType.ALL},
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private Date date;
    private boolean isQueue;

    public Cart() { }

    public Cart(List<Item> items, User user) {
        this.items = items;
        this.user = user;
        this.cost = items.stream().mapToDouble(item->item.getProduct().getPrice()).sum();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.cost = items.stream().mapToDouble(item->item.getProduct().getPrice()).sum();
        this.items = items;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isQueue() {
        return isQueue;
    }

    public void setQueye(boolean queue) {
        isQueue = queue;
    }

    @Override
    public String toString() {
        List<String> itemStrings = new ArrayList<>();
        items.forEach(i->itemStrings.add(i.getProduct().getTitle()));
        String[] arrStr = (String[]) itemStrings.toArray(new String[0]);
        String stroka = String.join(", ", arrStr);
        return "Cart{" + "items=" + stroka +'}';
    }
}