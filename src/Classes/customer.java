/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author tbrown
 */
public class customer {
    private int customerID;
    private String customerName;
    
    public customer(int cID, String name){
        customerID=cID;
        customerName=name;
    }
    
    public customer(customer cu){
        customerID=cu.getCustomerID();
        customerName=cu.getCustomerName();
    }
    
    public int getCustomerID(){
        return customerID;
    }
    
    public String getCustomerName(){
        return customerName;
    }
    
    public void setCustomerID(int cID){
        customerID=cID;
    }
    
    public void setCustomerName(String name){
        customerName=name;
    }
    
    public void addCustomer(String name, String cID){
        int ID=Integer.parseInt(cID);
        customerID=ID;
        customerName=name;
    }
}
