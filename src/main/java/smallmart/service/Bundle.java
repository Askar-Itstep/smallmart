package smallmart.service;
//исп-ся для принятия пакетов ajax в контроллерах
public class Bundle {   //пачка одинаковых товаров
    private Integer quantity;
    private Long itemId;

    public Bundle() {}

    public Bundle(Integer quantity, Long itemId) {
        this.quantity = quantity;
        this.itemId = itemId;
    }

    public Bundle(String quantity, String itemId) {
        this.quantity = Integer.valueOf(quantity);
        this.itemId = Long.valueOf(itemId);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String toString(){
        return "quantity: " + quantity + " itemId: " + itemId;
    }
}
