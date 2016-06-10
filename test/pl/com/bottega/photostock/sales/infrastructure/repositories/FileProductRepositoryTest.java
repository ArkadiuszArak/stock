package pl.com.bottega.photostock.sales.infrastructure.repositories;

import org.junit.Assert;
import org.junit.Test;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.ProductRepository;
import pl.com.bottega.photostock.sales.model.products.Clip;
import pl.com.bottega.photostock.sales.model.products.Picture;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by maciuch on 14.05.16.
 */
public class FileProductRepositoryTest {

    @Test
    public void shouldLoadProduct() {
        // given
        ProductRepository productRepository = new FileProductRepository(getClass().getResource("/fixtures/products.csv").getPath());

        //when
        Product product = productRepository.load("nr2");

        //then
        //assertEquals(new Product(...), product);

        assertEquals("nr2", product.getNumber());
        assertEquals(Picture.class, product.getClass());
        assertEquals(new Money(5.0, "USD"), product.calculatePrice());
        assertTrue(product.isAvailable());
        assertArrayEquals(new String[] {"tag1", "tag2", "tag3", "tag4"}, ((Picture) product).getTags());
    }

    @Test
    public void shouldThrowDataAccessExceptionWhenFileNotFound() {
        //given
        ProductRepository productRepository = new FileProductRepository("fake path");

        //when
        DataAccessException ex = null;
        try {
            productRepository.load("nr2");
        }
        catch(DataAccessException dae) {
            ex = dae;
        }

        //then
        assertNotNull(ex);
    }

    @Test
    public  void shouldWriteProducts(){
        //given
        ProductRepository productRepository = new FileProductRepository("tmp/products.csv");
        Product clip = new Clip("nr1", true, new Money(500.0, "USD"), 200);
        Product picture = new Picture("nr2", new Money(20.0, "USD"), new String[] {"t1", "t2"}, false);
        //when
        //productRepository.save(clip);
        //productRepository.save(picture);
        //then
        Product clipRead = productRepository.load("nr1");
        Product pictureRead = productRepository.load("nr2");

        Assert.assertEquals("nr1", clipRead.getNumber());
        Assert.assertEquals("nr2", pictureRead.getNumber());
        Assert.assertEquals(new Money(500.0, "USD"), clipRead.calculatePrice());
        assertArrayEquals(new String[] {"t1", "t2"}, ((Picture) pictureRead).getTags());
        Assert.assertEquals(200, ((Clip)clipRead).getLenght());
        Assert.assertEquals(false, pictureRead.isAvailable());
    }

    @Test
    public void shouldUpdateProduct() throws IOException {
        //given
        FileProductRepositoryFasterRead fileProductRepositoryFasterRead = new FileProductRepositoryFasterRead("tmp/product.csv");
        Product clip = new Clip("nr1", true, new Money(10.0, "PLN"), 200);
        Product clipUpdate = new Clip("nr1", true, new Money(20.0, "PLN"), 200);

        //when
        fileProductRepositoryFasterRead.save(clip);
        fileProductRepositoryFasterRead.save(clipUpdate);
    //    productRepository.load("nr1");

        //then
    //    Assert.assertEquals(new Money(20.0, "PLN"), clip.calculatePrice());

    }

    @Test
    public void shoudPutProductsFromFileToMap() throws IOException {
        //given
        Map<String, String> testProduct = new HashMap<>();
        FileProductRepositoryFasterRead fr = new FileProductRepositoryFasterRead("tmp/product.csv");
        String c1 = "nr1,1000,PLN,true,2000,,Clip";
        testProduct.put("nr1", c1);

        //then
        assertEquals(testProduct, fr.putFromFileToHashMap());
    }

    @Test
    public void shouldPutProduct(){
        //given
        FileProductRepositoryFasterRead fr = new FileProductRepositoryFasterRead("tmp/product.csv");
        Map<String, Product> testProduct = new HashMap<>();

        Product clip = new Clip("nr1", true, new Money(10.0, "PLN"), 200);
        Product p1 = new Picture("nr1", new Money(10), new String[]{"ford", "mustang"}, true);

        //when
      //  testProduct.put(p1.getNumber(), p1);

        for (Map.Entry<String, Product> entry : testProduct.entrySet()){
            if (entry.getValue() instanceof Product){
                testProduct.put(p1.getNumber(), p1);
            }
        }

        //then
    }

}
