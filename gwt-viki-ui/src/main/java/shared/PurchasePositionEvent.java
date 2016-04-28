package shared;

import de.novanic.eventservice.client.event.Event;

public abstract class PurchasePositionEvent implements Event {
    private String name;
    private long count;
    private long price;
    private String item;
    private Long purchaseSum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Long getPurchaseSum() {
        return purchaseSum;
    }

    public void setPurchaseSum(Long purchaseSum) {
        this.purchaseSum = purchaseSum;
    }
}
