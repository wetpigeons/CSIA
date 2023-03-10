public class Item {
    public int year;
    public int month;
    public int day;
    public String event;

    public Item(int month, int day, int year, String event){
        this.month = month;
        this.day = day;
        this.year = year;
        this.event = event;
        System.out.println("New Event: " + month + "/" + day + "/"+year+"   "+event);
    }
}
