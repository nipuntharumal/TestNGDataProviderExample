package services;

public class DataAccess {

    private String username;
    private String password;
    private String error;
    private String productsHomeLabelValue;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProductsHomeLabelValue() {
        return productsHomeLabelValue;
    }

    public String getError() {
        return error;
    }

    public String toString(){

        return "TestData{" +
                "username='" + username + '\'' +
                ", password='" + password +
                '}';

    }


}
