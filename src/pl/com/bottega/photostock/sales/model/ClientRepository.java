package pl.com.bottega.photostock.sales.model;

/**
 * Created by Slawek on 23/04/16.
 */
public interface ClientRepository {
    Client load(String nr);

    void save(Client client);
}
