import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.MonthDay;
import java.util.*;

public class Main {
    static JFrame frame;
    static JPanel calendar;
    static JPanel toDo;
    static JPanel workouts;
    static JTabbedPane tabs;
    static Calendar cal = Calendar.getInstance();
    static int monthDif;

    public static void main(String[] args) {

        /*
        --Tabs at top: Calendar, To-Do, Workouts
        -- Calendar has clickable items (links?) to to-do or workouts
        --To-Do is a checklist, customizable
        --Workouts can save different swing text boxes or something, clicking a workout will change the visible text box - maybe scrollable list on left, text on right
         */
        frame = new JFrame();
        initializeCalendar();

        toDo = new JPanel();
        workouts = new JPanel();
        tabs = new JTabbedPane();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.add(tabs);

        tabs.addTab("Calendar", calendar);
        tabs.addTab("To-Do List", toDo);
        tabs.addTab("Saved Workouts", workouts);

        frame.setVisible(true);

    }

    public static void initializeCalendar() {
        calendar = new JPanel(new GridBagLayout());
        JPanel temp;
        GridBagConstraints c = new GridBagConstraints();
        GridBagConstraints c1 = new GridBagConstraints();

        temp = new JPanel();
        JLabel month = new JLabel(cal.getDisplayName(Calendar.MONTH, 2, Locale.US));
        temp.add(month);
        temp.setBorder(BorderFactory.createLineBorder(Color.black));

        c.ipady = 50;
        c.fill = GridBagConstraints.BOTH;
        c.gridx=1;
        c.gridwidth = 5;
        calendar.add(temp, c); //Month

        c.gridwidth = 1;
        c.ipady = 20;
        c.ipadx = 100;
        c.gridy = 1;
        for (int i = 0; i < 7; i++) { //Days of the week
            cal.set(Calendar.DAY_OF_WEEK, i + 1);

            c.gridx = i;
            temp = new JPanel();
            temp.add(new JLabel(cal.getDisplayName(Calendar.DAY_OF_WEEK, 2, Locale.US)));
            temp.setBorder(BorderFactory.createLineBorder(Color.black));
            calendar.add(temp, c);
        }
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int startIndex = cal.get(Calendar.DAY_OF_WEEK);
        cal = Calendar.getInstance();

        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        int daysOfPrevious = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal = Calendar.getInstance();

        JLabel dayNumber;
        int index = daysOfPrevious - startIndex + 2;
        System.out.println(daysOfPrevious);
        System.out.println(startIndex);
        System.out.println(index);

        c.ipady = 100;
        c.ipadx = 100;
        c.anchor = GridBagConstraints.CENTER;

        c1.ipadx = 0;
        c1.ipady = 0;
        c1.weightx = 0.1;
        c1.weighty = 0.1;
        c1.anchor = GridBagConstraints.FIRST_LINE_START;

        boolean prevMonth = true;

        for (int j = 2; j < 7; j++) { //rows
            c.gridy = j;
            for (int i = 0; i < 7; i++) { //Days - columns
                c.gridx = i;
                temp = new JPanel(new GridBagLayout());
                if (index <= daysOfPrevious && prevMonth) { //previous month
                    dayNumber = new JLabel(String.valueOf(index));
                    temp.add(dayNumber, c1);
                    index++;
                } else if (prevMonth){ //change to current month
                    prevMonth = false;
                    index = 1;
                }
                if (index <= days && !prevMonth) { //current month
                    dayNumber = new JLabel(String.valueOf(index));
                    temp.add(dayNumber, c1);
                    index++;
                }
                temp.setBorder(BorderFactory.createLineBorder(Color.black));
                calendar.add(temp, c);
            }
        }
        c.ipadx=0;
        c.ipady=0;
        c.anchor = GridBagConstraints.CENTER;
        c.weighty=0;
        c.weightx=0;
        c.insets= new Insets(15,15,15,15);
        c.gridy=0;
        c.gridx=0;
        JButton left = new JButton("Previous Month");
        left.addActionListener(e -> {monthDif--; initializeCalendar();});
        calendar.add(left,c);
        JButton right = new JButton("Next Month");
        right.addActionListener(e -> {monthDif++; initializeCalendar();});
        c.gridx=6;
        calendar.add(right,c);

    }
}
