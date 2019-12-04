package lucene.external;

import java.io.Serializable;

public class Employee implements Serializable {

    private String userId;
    private String password;
    private String name;
    private String address;
    private String country;
    private String zip;
    private String email;
    private String salary;
    private String sex;
    private String language;
    private String about;
    private String status;
    
    // for anonymization
    private String bucketId;

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }
    
    
    

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalary() {        
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Employee(String userId, String password, String name, String address, String country, String zip, String email, String salary, String sex, String language, String about, String status) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.address = address;
        this.country = country;
        this.zip = zip;
        this.email = email;
        
        // peforming anonymization for salary
        double amount = Double.parseDouble(salary);
        double low  = amount*3/4;
        double high = amount + (amount*1/4);  
        this.salary = low+"-"+high;
        
        //this.salary = salary;
        
        this.sex = sex;
        this.language = language;
        this.about = about;
        this.status = status;
    }

    
    
    public String toString() {
        return "Employee " + getUserId() + ": " + getName() + " (" + getSalary()+ ")";
    }
}