package pl.com.bottega.photostock.sales.model;

/**
 * Created by Slawek on 23/04/16.
 */
public interface PurchaseRepository {
    Purchase load(String nr);

    void save(Purchase purchase);
}
