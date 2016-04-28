package shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Product implements IsSerializable {

    private String product;
    private String item;
    private long price;
    private long count;
    private AddPositionToPurchaseEvent callback;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public void incrementCount() {
        count++;
    }


}
