package fr.delodev.eatit.models;

public class UserModel
{
    private String Name;
    private String Password;

    public UserModel(String mName, String mPassword) {
        this.Name = mName;
        this.Password = mPassword;
    }

    public UserModel() { }

    public String getName() { return Name;}
    public void setName(String name) { Name = name; }

    public String getPassword() { return Password; }
    public void setPassword(String password) { Password = password; }
}
