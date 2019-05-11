/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.TimeZone;

/**
 *
 * @author tbrown
 */
public class appointment {
    private int ID;
    
    private String title;
    
    private String location;
    
    private String contact;
    
    private String start;
    
    private String end;
    
    public appointment(int i, String t, String loc, String con, String st, String en){
        try{
        ID=i;
        title=t;
        location=loc;
        contact = con;
        String [] date = st.split(" ");
        LocalDate da = LocalDate.parse(date[0]);
        String [] time = date[1].split(":");
        
        int hour = Integer.parseInt(time[0]);
        TimeZone timezone = TimeZone.getDefault();
        
        int h = timezone.getRawOffset()/3600000;
        hour=hour+h;
        
        Calendar c = Calendar.getInstance();
        hour=hour+(c.get(Calendar.DST_OFFSET)/3600000);
        
        if(hour>=24){
        hour=hour-24;
            da.plusDays(1);
            //
        }else if(hour<0){
            hour = hour + 24;
            da.minusDays(1);
        }
        time[0]=Integer.toString(hour);
        st = da.toString() + " " + hour + ":" + time[1] + ":" + time[2];
        start=st;
        String[] edate = en.split(" ");
        String[] etime = edate[1].split(":");
        hour=Integer.parseInt(etime[0]);
        hour=hour+h;
        if(hour>=24){
            hour=hour-24;
            da.plusDays(1);
            //
        }else if(hour<0){
            hour = hour + 24;
        }
        en = da.toString() + " " + hour + ":" + etime[1] + ":" + etime[2];
        end=en;
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public appointment(appointment app) {
        ID=app.getID();
        title=app.getTitle();
        location=app.getLocation();
        contact=app.getContact();
        start=app.getStart();
        end=app.getEnd();
    }
    
    public appointment(String st, String en){
        try{
        ID=0;
        title="";
        location="";
        contact = "";
        String [] date = st.split(" ");
        LocalDate da = LocalDate.parse(date[0]);
        String [] time = date[1].split(":");
        
        int hour = Integer.parseInt(time[0]);
        TimeZone timezone = TimeZone.getDefault();
        
        int h = timezone.getRawOffset()/3600000;
        hour=hour+h;
        
        Calendar c = Calendar.getInstance();
        hour=hour+(c.get(Calendar.DST_OFFSET)/3600000);
        
        if(hour>=24){
        hour=hour-24;
            da.plusDays(1);
            //
        }else if(hour<0){
            hour = hour + 24;
            da.minusDays(1);
        }
        time[0]=Integer.toString(hour);
        st = da.toString() + " " + hour + ":" + time[1] + ":" + time[2];
        start=st;
        String[] edate = en.split(" ");
        String[] etime = edate[1].split(":");
        hour=Integer.parseInt(etime[0]);
        hour=hour+h;
        if(hour>=24){
            hour=hour-24;
            da.plusDays(1);
            //
        }else if(hour<0){
            hour = hour + 24;
        }
        en = da.toString() + " " + hour + ":" + etime[1] + ":" + etime[2];
        end=en;
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public int getID(){
        return ID;
    }
    
    public String getTitle(){
        return title;
    }
    
    public String getLocation(){
        return location;
    }
    
    public String getContact(){
        return contact;
    }
    
    public String getStart(){
        return start;
    }
    
    public String getEnd(){
        return end;
    }
    
    public void setID(int i){
        ID=i;
    }
    
    public void setTitle(String t){
        title=t;
    }
    
    public void setLocation(String l){
        location=l;
    }
    
    public void setContact(String c){
        contact=c;
    }
    
    public void setStart(String s){
        start=s;
    }
    
    public void setEnd(String e){
        end=e;
    }
}
