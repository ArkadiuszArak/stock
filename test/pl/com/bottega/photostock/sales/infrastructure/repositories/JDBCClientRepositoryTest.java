package pl.com.bottega.photostock.sales.infrastructure.repositories;

import org.junit.Before;
import org.junit.Test;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.ClientRepository;
import pl.com.bottega.photostock.sales.model.Money;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.Assert.assertEquals;

/**
 * Created by arkadiuszarak on 02/06/2016.
 */
public class JDBCClientRepositoryTest {
    ClientRepository repo;

    @Before
    public void setUp() throws Exception {
        //given
        Connection c = DriverManager.getConnection("jdbc:hsqldb:mem:stock", "SA", "");
        createClientTable(c);

        insertTestClient(c);
        c.close();

        repo = new JDBCClientRepository("jdbc:hsqldb:mem:stock", "SA", "");
    }

    @Test
    public void shouldLoadClient(){

        //given
        Client client = repo.load("Jan");

        //then
        assertEquals("Jan", client.getName());
        assertEquals(Client.class, client.getClass());
    }

    @Test
    public void shoulsSaveClient(){
        //given
        Client client = new Client("nr1","Mateusz", "Ziemia", new Money(23.0, "PLN"), true);

        //when
        repo.save(client);

        //then
        Client savedClient = repo.load("Mateusz");
        assertEquals("Mateusz", savedClient.getName());
        assertEquals("Ziemia", savedClient.getAddress());
    }

    @Test
    public void shouldInsertClientIfNotExist(){
        //given
        Client client = new Client("nr1","Mateusz", "Ziemia", new Money(23.0, "PLN"), true);

        //when
        repo.save(client);

        //then
    }

    private void createClientTable(Connection c) throws Exception{
        c.createStatement().executeUpdate("DROP TABLE CLIENTS IF EXISTS ");

        c.createStatement().executeUpdate("CREATE TABLE Clients (\n" +
                "  id IDENTITY PRIMARY KEY,\n" +
                "  number VARCHAR(20) NOT NULL, \n" +
                "  name VARCHAR(255) NOT NULL,\n" +
                "  address VARCHAR(255) NOT NULL ,\n" +
                "  amountCents INTEGER DEFAULT 0 NOT NULL,\n" +
                "  amountCurrency CHAR(3) DEFAULT 'PLN' NOT NULL,\n" +
                "  active BOOLEAN DEFAULT true NOT NULL\n" +
                ");");
    }

    private void insertTestClient (Connection c) throws Exception{
        c.createStatement().executeUpdate("INSERT INTO Clients (number, name, address, amountCents, amountCurrency, active) VALUES ('nr1','Jan', 'ul. Koralowa 10', 10000, 'PLN', true);");
    }

}
