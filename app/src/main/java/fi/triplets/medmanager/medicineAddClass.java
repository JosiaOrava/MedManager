package fi.triplets.medmanager;

public class medicineAddClass {
    String name, amount, hours, minutes;
    Boolean[] dates;
    public medicineAddClass(String name, String amount, String hours, String minutes, Boolean[] array){
        this.name = name;
        this.amount = amount;
        this.hours = hours;
        this.minutes = minutes;
        this.dates = array;
    }


    public String getName(){
        return name;
    }
    public String getAmount(){
        return amount;
    }
    public String getHours(){
        return hours;
    }
    public String getMinutes(){return minutes;}
    public Boolean[] getDates(){
        return dates;
    }
    public void changeInfo(String name, String amount, String hours, String minutes, Boolean[] array){
        this.name = name;
        this.amount = amount;
        this.hours = hours;
        this.minutes = minutes;
        this.dates = array;
    }
}
