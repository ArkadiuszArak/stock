package pl.com.bottega.photostock.sales.infrastructure.repositories;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.ClientRepository;
import pl.com.bottega.photostock.sales.model.Money;

import java.io.File;

/**
 * Created by arkadiuszarak on 15/05/2016.
 */
public class FileClientRepositoryTest {

    @Test
    public void shouldLoadClient(){
        //given
        ClientRepository clientRepository = new FileClientRepository(getClass().getResource("/fixtures/clients.csv").getPath());

        //when
         Client client = clientRepository.load("nr3");

        //then

    }

    @Test
    public void shouldWriteClient(){
        //given
        FileClientRepository fileClientRepository = new FileClientRepository("tmp/clients.csv");
        Client Janek = new Client("nr1", "Janek", "Javova", new Money(300.0, "PLN"));
        Client Alicja = new Client("nr2", "Alicja", "Pythonowa", new Money(500.0, "USD"));

        //when
        fileClientRepository.save(Janek);
        fileClientRepository.save(Alicja);

        Client readJanek = fileClientRepository.load("nr1");
        Client readAlicja = fileClientRepository.load("nr2");

        //then
        Assert.assertEquals("nr1", readJanek.getNumber());
        Assert.assertEquals(new Money(500.0, "USD"), readAlicja.getAmount());
        Assert.assertNotEquals(null, readAlicja.getName());
        Assert.assertNotEquals(null, readAlicja.getAddress());
    }

    @Test
    public void shouldDeleteTmpFile(){
        File deleteTmpFile = new File("tmp/clients.csv");
        deleteTmpFile.delete();
    }
}
