package pl.com.bottega.photostock.sales.model;

import java.util.List;

/**
 * Created by Slawek on 23/04/16.
 */
public interface ReservationRepository {
    void save(Reservation reservation);

    Reservation load(String reservatonNr);

    Reservation findOpenedPer(Client client);

    List<Reservation> find(String clientNr);
}
