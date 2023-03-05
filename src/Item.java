public class Item {
    public static int year;
    public static int month;
    public static int day;
    public static String event;

    public Item(int month, int day, int year, String event){
        this.month = month;
        this.day = day;
        this.year = year;
        this.event = event;
        System.out.println("New Event: " + month + "/" + day + "/"+year+"   "+event);
    }
}
