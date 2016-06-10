package pl.com.bottega.photostock.sales.infrastructure.repositories;

import org.junit.Before;
import org.junit.Test;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.ProductRepository;
import pl.com.bottega.photostock.sales.model.products.Picture;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by arkadiuszarak on 29/05/2016.
 */
public class JDBCProductRepositoryTest {

    ProductRepository repo;

    @Before
    public void setUp() throws Exception {
        //given
        Connection c = DriverManager.getConnection("jdbc:hsqldb:mem:stock", "SA", "");
        dropTables(c);
        createProductsTable(c);
        createTagsTable(c);
        insertTagsTest(c);
        insertTestProduct(c);
        c.close();

        //when
        repo = new JDBCProductRepository("jdbc:hsqldb:mem:stock", "SA", "");
    }

    @Test
    public void shouldLoadProduct() throws Exception {
        //when
        Product product = repo.load("nr1");

        //then
        assertEquals("nr1", product.getNumber());
        assertEquals(Picture.class, product.getClass());
    }

    @Test
    public void shouldReturnNullWhenProductDoesntExist() throws Exception{

        Product product = repo.load("nr500");
        //then
        assertNull(product);
    }

    @Test
    public void shouldSaveProduct() throws IOException {
        //given
        Product picture = new Picture("nr2", new Money(20.0, "PLN"), new String[] {}, false);

        //when
        repo.save(picture);

        //then
        Product saved = repo.load("nr2");
        assertEquals("nr2", saved.getNumber());
        assertEquals(new Money(20.0, "PLN"), saved.calculatePrice());
    }

    @Test
    public void shouldSaveProductwithTags() throws IOException {
        //given
        Product picture = new Picture("nr2", new Money(20.0, "PLN"), new String[] {"one", "two", "three"}, false);

        //when
        repo.save(picture);

        //then
        Picture saved = (Picture) repo.load("nr2");
        assertArrayEquals(new String[] {"one", "two", "three"}, saved.getTags());
    }

    @Test
    public void nullTags() throws Exception{
        //given
        Product picture = new Picture("nr2", new Money(20.0, "PLN"), new String[] {}, false);

        //when
        repo.save(picture);

        //then
        Picture saved = (Picture) repo.load("nr2");
        assertNotEquals(new String[] {"one", "two", "three"}, saved.getTags());
    }

    @Test
    public void shouldUpdateProductWithTag() throws Exception{
        Product picture = new Picture("nr2", new Money(20.0, "PLN"), new String[] {"one", "two", "three"}, false);
        Product pictureUpdate = new Picture("nr2", new Money(20.0, "PLN"), new String[] {"one", "three"}, false);

        //when
        repo.save(picture);
        repo.save(pictureUpdate);

        //then
        Picture updated = (Picture) repo.load("nr2");
        assertArrayEquals(new String[] {"one", "three"}, updated.getTags());
    }


    private void insertTestProduct(Connection c) throws Exception {
        c.createStatement().executeUpdate("INSERT INTO Products (number, available, priceCents, priceCurrency, length, type) VALUES ('nr1', true, 200, 'USD', NULL, 'Picture');");
    }

    private void createProductsTable(Connection c) throws SQLException {

        c.createStatement().executeUpdate(
                "CREATE TABLE Products (\n" +
                        "  id INTEGER IDENTITY PRIMARY KEY,\n" +
                        "  number VARCHAR(20) NOT NULL, \n" +
                        "  type VARCHAR(20) NOT NULL,\n" +
                        "  available BOOLEAN DEFAULT true NOT NULL,\n" +
                        "  priceCents INTEGER DEFAULT 0 NOT NULL,\n" +
                        "  priceCurrency CHAR(3) DEFAULT 'PLN' NOT NULL,\n" +
                        "  length BIGINT\n" +
                        ");");
    }

    private void createTagsTable(Connection c) throws Exception {

        c.createStatement().executeUpdate("CREATE TABLE Tags (\n" +
                "  id INTEGER IDENTITY PRIMARY KEY,\n" +
                "  name VARCHAR(255) NOT NULL\n" +
                ");");

        c.createStatement().executeUpdate("CREATE TABLE ProductsTags (\n" +
                "  productId INTEGER FOREIGN KEY REFERENCES Products(id),\n" +
                "  tagId INTEGER FOREIGN KEY REFERENCES Tags(id),\n" +
                "  PRIMARY KEY (productId, tagId)\n" +
                ");");
    }

    private void dropTables(Connection c) throws Exception {
        c.createStatement().executeUpdate("DROP TABLE ProductsTags IF EXISTS;");
        c.createStatement().executeUpdate("DROP TABLE Tags IF EXISTS;");
        c.createStatement().executeUpdate("DROP TABLE PRODUCTS IF EXISTS ");

    }
    private void insertTagsTest(Connection c) throws Exception {
        c.createStatement().executeUpdate("INSERT INTO Tags (name) VALUES ('rock');");
    }
}