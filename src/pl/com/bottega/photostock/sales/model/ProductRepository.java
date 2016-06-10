package pl.com.bottega.photostock.sales.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Slawek on 17/04/16.
 */
public interface ProductRepository {
    Product load(String nr);
    void save(Product product) throws IOException;

    List<Product> find(String[] tags, String author,
                       Money minPrice, Money maxPrice, boolean acceptNotavailable);
}
