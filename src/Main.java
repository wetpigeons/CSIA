import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.MonthDay;
import java.util.*;

import static java.util.Calendar.MONTH;

public class Main {
    static JFrame frame = new JFrame();
    static JFrame newEvent;
    static JPanel calendar;
    static JPanel toDo;
    static JPanel workouts;
    static JTabbedPane tabs;
    static Calendar cal;
    static int monthDif = 0;
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int width = (int) screenSize.getWidth();
    static int height = (int) screenSize.getHeight();


    public static void main(String[] args) {

        /*
        --Tabs at top: Calendar, To-Do, Workouts
        -- Calendar has clickable items (links?) to to-do or workouts
        --To-Do is a checklist, customizable
        --Workouts can save different swing text boxes or something, clicking a workout will change the visible text box - maybe scrollable list on left, text on right
         */
        initializeNewEvent();
        initializeFrame();

    }

    public static void initializeCalendar() {
        calendar = new JPanel(new GridBagLayout());
        initializeCal();
        JPanel temp;
        GridBagConstraints c = new GridBagConstraints();
        GridBagConstraints c1 = new GridBagConstraints();

        temp = new JPanel();
        JLabel month = new JLabel(cal.getDisplayName(MONTH, 2, Locale.US));
        temp.add(month);
        temp.setBorder(BorderFactory.createLineBorder(Color.black));

        c.ipady = 35;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridwidth = 5;
        calendar.add(temp, c); //Month

        c.gridwidth = 1;
        c.ipady = 15;
        c.ipadx = 80;
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
        initializeCal();

        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        cal.set(MONTH, cal.get(MONTH) - 1);
        int daysOfPrevious = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        initializeCal();

        JLabel dayNumber;
        int index = daysOfPrevious - startIndex + 2;

        c.ipady = 70;
        c.ipadx = 70;
        c.anchor = GridBagConstraints.CENTER;

        c1.ipadx = 0;
        c1.ipady = 0;
        c1.weightx = 0.1;
        c1.weighty = 0.1;
        c1.anchor = GridBagConstraints.FIRST_LINE_START;

        boolean prevMonth = true;
        boolean currentMonth = false;
        boolean nextMonth = false;

        for (int j = 2; j < 7; j++) { //rows
            c.gridy = j;
            for (int i = 0; i < 7; i++) { //Days - columns
                c.gridx = i;
                temp = new JPanel(new GridBagLayout());
                if (index > daysOfPrevious && prevMonth) {
                    prevMonth = false;
                    currentMonth = true;
                    index = 1;
                } else if (index <= daysOfPrevious && prevMonth) { //previous month
                    temp.setBackground(Color.gray);
                    dayNumber = new JLabel(String.valueOf(index));
                    temp.add(dayNumber, c1);
                    index++;
                }
                if (index <= days && currentMonth) { //current month
                    dayNumber = new JLabel(String.valueOf(index));
                    temp.add(dayNumber, c1);
                    index++;
                    if (index > days) {
                        currentMonth = false;
                        nextMonth = true;
                        index = 1;
                    }
                } else if (nextMonth) {
                    temp.setBackground(Color.gray);
                    dayNumber = new JLabel(String.valueOf(index));
                    temp.add(dayNumber, c1);
                    index++;
                }

                if (index == cal.DAY_OF_MONTH && monthDif == 0) {
                    temp.setBackground(Color.decode("#FFA591"));
                } else {
                    temp.setBorder(BorderFactory.createLineBorder(Color.black));
                }
                calendar.add(temp, c);
            }
        } //grids
        c.ipadx = 0;
        c.ipady = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.weighty = 0;
        c.weightx = 0;
        c.insets = new Insets(15, 15, 15, 15);
        c.gridy = 0;
        c.gridx = 0;
        JButton left = new JButton("Previous Month");
        left.addActionListener(e -> {
            monthDif--;
            initializeFrame();
        });
        calendar.add(left, c);
        JButton right = new JButton("Next Month");
        right.addActionListener(e -> {
            monthDif++;
            initializeFrame();
        });
        c.gridx = 6;
        calendar.add(right, c);


        c.gridx = 0;
        c.gridy = 7;
        JButton newEvent = new JButton("Create New Event");
        calendar.add(newEvent, c);
        newEvent.addActionListener(e -> createNewEvent());
    }

    public static void initializeNewEvent() {
        Calendar today = Calendar.getInstance();
        int dayNumber = today.get(Calendar.DAY_OF_MONTH);
        int yearNumber = today.get(Calendar.YEAR);
        int monthNumber = today.get(Calendar.MONTH);

        newEvent = new JFrame();
        newEvent.setSize(350,125);
        newEvent.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        newEvent.setLocationRelativeTo(null);
        JButton create = new JButton("Create New Task");
        JButton cancel = new JButton("Cancel");
        SpinnerModel dayModel = new SpinnerNumberModel(dayNumber,1,31,1);
        SpinnerModel monthModel = new SpinnerNumberModel(monthNumber+1,1,12,1);
        SpinnerModel yearModel = new SpinnerNumberModel(yearNumber,yearNumber-100,yearNumber+100,1);
        JSpinner year = new JSpinner(yearModel);
        JSpinner month = new JSpinner(monthModel);
        JSpinner day = new JSpinner(dayModel);
        JLabel yearLabel = new JLabel("Year:");
        JLabel monthLabel = new JLabel("Month:");
        JLabel dayLabel = new JLabel("Day:");
        JLabel eventLabel = new JLabel("Event:");
        JTextField event = new JTextField("",20);
        JPanel newEventPanel = new JPanel();

        cancel.addActionListener(e -> newEvent.setVisible(false));
        create.addActionListener(e -> new Item((int)month.getValue(),(int)day.getValue(),(int)year.getValue(),event.getText()));
        newEventPanel.add(monthLabel);
        newEventPanel.add(month);
        newEventPanel.add(dayLabel);
        newEventPanel.add(day);
        newEventPanel.add(yearLabel);
        newEventPanel.add(year);
        newEventPanel.add(eventLabel);
        newEventPanel.add(event);
        newEventPanel.add(cancel);
        newEventPanel.add(create);
        newEvent.add(newEventPanel);
    }
    public static void createNewEvent(){
        newEvent.setVisible(true);
    }

    public static void initializeCal() {
        cal = Calendar.getInstance();
        cal.set(MONTH, cal.get(MONTH) + monthDif);
    }

    public static void initializeFrame() {
        frame.setVisible(false);
        frame.removeAll();
        frame = new JFrame();
        initializeCalendar();

        toDo = new JPanel();
        workouts = new JPanel();
        tabs = new JTabbedPane();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);

        frame.add(tabs);

        tabs.addTab("Calendar", calendar);
        tabs.addTab("To-Do List", toDo);
        tabs.addTab("Saved Workouts", workouts);

        frame.setVisible(true);
    }
}
