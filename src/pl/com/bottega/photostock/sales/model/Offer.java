package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.photostock.sales.model.products.Picture;

import java.util.List;

/**
 * Created by Slawek on 12/03/16.
 */
public class Offer {

    private final Client owner;
    private List<Product> items;

    private Money totalCost;

    public Offer(Client owner, List<Product> items) {
        if (items.size() == 0)
            throw new IllegalArgumentException("Items can't be empty");

        this.items = items;
        this.owner = owner;
        this.totalCost = calculateTotalCost();
    }

    private Money calculateTotalCost() {
        Money sum = items.get(0).calculatePrice().getZero();//aby ustalić walutę

        for(Product p : items)
            sum = sum.add(p.calculatePrice());

        return sum;
    }

    public Money getTotalCost() {
        return totalCost;
    }

    public int getItemsCount(){
        return items.size();
    }

    public List<Product> getItems() {
        return items;
    }
}
