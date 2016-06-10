package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.photostock.sales.model.products.Picture;

import java.util.Date;
import java.util.List;

/**
 * Created by Slawek on 12/03/16.
 */
public class Purchase {

    private Client owner;
    private Date createDate;
    private String number;
    private List<Product> items;

    public Purchase(Client owner, List<Product> items) {
        this.owner = owner;
        this.items = items;
        this.createDate = new Date();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


}
