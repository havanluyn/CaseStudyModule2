package model;

import java.io.Serializable;
import java.time.Instant;

public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String nameAcount;
    private String passWord;
    private double amount;
    private Instant createAt;

    public Client() {
    }

    public Client(long id, String nameAcount, String passWord, double amount, Instant createAt) {
        this.id = id;
        this.nameAcount = nameAcount;
        this.passWord = passWord;
        this.amount = amount;
        this.createAt = createAt;
    }

    public long getId() {
        return id;
    }

    public Instant getDateCreate() {
        return createAt;
    }

    public void setDateCreate(Instant dateCreate) {
        this.createAt = createAt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameAcount() {
        return nameAcount;
    }

    public void setNameAcount(String nameAcount) {
        this.nameAcount = nameAcount;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", nameAcount='" + nameAcount + '\'' +
                ", passWord='" + passWord + '\'' +
                ", amount=" + amount +
                ", dateCreate=" + createAt +
                '}';
    }
}
