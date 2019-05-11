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
public class seeder {
    private String identifier;
    private int ID;
    private int count;
    
    public seeder(String ident, int co, int id){
        identifier=ident;
        ID=id;
        count=co;
    }
    
    public seeder(String ident, int co){
        identifier=ident;
        ID=-1;
        count=co;
    }
    
    public seeder(seeder s){
        identifier=s.getIdentifier();
        ID=s.getID();
        count=s.getCount();
    }
    
    public String getIdentifier(){
        return identifier;
    }
    
    public int getID(){
        return ID;
    }
    
    public int getCount(){
        return count;
    }
}
