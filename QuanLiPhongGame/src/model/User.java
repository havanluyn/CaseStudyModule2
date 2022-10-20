package model;

import java.io.Serializable;
import java.time.LocalTime;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private long Id;
    private String userName;
    private String passWord;
    private String typeUser;
    private LocalTime dateCreate;
    public User( String userName, String passWord, String typeUser) {
        this.Id = 0;
        this.userName = userName;
        this.passWord = passWord;
        this.typeUser = typeUser;
        this.dateCreate = LocalTime.now();
    }
    public User(long id, String userName, String passWord, String typeUser, LocalTime dateCreate) {
        this.Id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.typeUser = typeUser;
        this.dateCreate = dateCreate;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public LocalTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    @Override
    public String toString() {
        return "model.User{" +
                "Id='" + Id + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", typeUser='" + typeUser + '\'' +
                ", dateCreate=" + dateCreate +
                '}';
    }
}
