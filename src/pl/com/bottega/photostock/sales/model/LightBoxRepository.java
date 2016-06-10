package pl.com.bottega.photostock.sales.model;

/**
 * Created by Slawek on 14/05/16.
 */
public interface LightBoxRepository {
    void save(LightBox lightBox);

    LightBox load(String lightboxId);

}
