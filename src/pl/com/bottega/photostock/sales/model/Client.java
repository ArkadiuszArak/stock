package pl.com.bottega.photostock.sales.model;


import pl.com.bottega.photostock.sales.model.client.Payer;
import pl.com.bottega.photostock.sales.model.client.PayerStrategy;
import pl.com.bottega.photostock.sales.model.client.StandardPayerStrategy;


/**
 * Created by Slawek on 12/03/16.
 */
public class Client {

    private String name;
    private String address;
    private Money amount;
    private boolean active = true;
    private String number;

    private PayerStrategy payerStrategy;

    private Payer payer = new ClientPayer();
    public Client(String number, String name, String address, Money amount, boolean active) {
        this.number = number;
        this.name = name;
        this.address = address;
        this.amount = amount;
        this.active = active;
    }


    //klasa wewnętrzna - "widzi" prywatne pola klasy, która ją zawiera
    private class ClientPayer implements Payer{

        @Override
        public Money getAmount() {
            return amount;
        }

        @Override
        public void setAmount(Money value) {
            amount = value;
        }
    }

    public Client(String number, String name, String address, Money amount, PayerStrategy payerStrategy) {
        this.number = number;
        this.name = name;
        this.address = address;
        this.amount = amount;

        this.payerStrategy = payerStrategy;
    }

    public Client(String number, String name, String address, Money amount) {
        this(number, name, address, amount, new StandardPayerStrategy());
    }


    public boolean canAfford(Money price) {
        return payerStrategy.canAfford(payer, price);
    }


    public void charge(Money price, String cause) {
        if (! payerStrategy.canAfford(payer, price))
            throw new IllegalStateException("can not afford " + price);
        payerStrategy.charge(payer, price, cause);
    }

    public void recharge(Money amount) {
        payerStrategy.recharge(payer, amount);
    }

    public Money getSaldo() {
        return payerStrategy.calculateSaldo(this.new ClientPayer());
    }

    public boolean isActive() {
        return active;
    }

    public String introduce(){
        return name;// + " - " + status.getPolishString();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PayerStrategy getPayerStrategy() {
        return payerStrategy;
    }

    public void setPayerStrategy(PayerStrategy payerStrategy) {
        this.payerStrategy = payerStrategy;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Money getAmount() {
        return amount;
    }

    public String[] export() { //number, name, address, amount, priceCents,priceCurrency
        return new String[]{
                getNumber(),
                getName(),
                getAddress(),
                String.valueOf(amount.cents()),
                amount.currency()
        };
    }
}