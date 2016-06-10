package pl.com.bottega.photostock.sales.model;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.*;

/**
 * Created by Slawek on 12/03/16.
 */
public class Reservation extends Object{
    private String number;
    private Client owner;
    private List<Product> items = new LinkedList<>();
    private boolean closed;

    public Reservation(Client owner) {
        this.owner = owner;
    }

    public void add(Product product) throws ProductNotAvailableException, IllegalArgumentException{
        //....
        if (!product.isAvailable())
            throw new ProductNotAvailableException(
                    "Trying to reserve", product.getNumber(), Reservation.class);
        if (items.contains(product))
            throw new IllegalArgumentException("product is duplicated");

        items.add(product);
    }

    public void remove(Product product){
        boolean removed = items.remove(product);
        if (!removed)
            throw new IllegalArgumentException("product never added");
    }

    public Offer generateOffer(){

        List<Product> result = Lists.newLinkedList(Iterables.filter(items, new Predicate<Product>() {
            @Override
            public boolean apply(Product product) {
                return product.isAvailable();
            }
        }));

        Comparator<Product> comparator = new PriceAndNameProductComparator();
        Collections.sort(result, comparator);

        return new Offer(owner, result);
    }

    public int getItemsCount() {
        return items.size();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Client getOwner() {
        return owner;
    }

    public void close() {
        closed = true;
    }

    public boolean isClosed() {
        return closed;
    }
}
