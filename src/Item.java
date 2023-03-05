public class Item {
    public  int year;
    public  int month;
    public int day;
    public  String event;
    public  String description;

    public Item(int month, int day, int year, String event, String description){
        this.month = month;
        this.day = day;
        this.year = year;
        this.event = event;
        this.description = description;
        System.out.println("New Event: " + month + "/" + day + "/"+year+"   "+event + ": " + description);
    }
}
