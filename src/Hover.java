import java.awt.Color; //Credit to TabsPH on github
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.*;

public class Hover extends MouseAdapter {

    private String lblStr = "";
    private String orgStr = "";

    public Hover( String lblStr, String orgStr ) {
        this.lblStr = lblStr;
        this.orgStr = orgStr;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel lbl = (JLabel) e.getComponent();
        lbl.setForeground(new Color(255, 127, 80));
        lbl.setText( String.format( "<html><u>%s</u></html>", lblStr));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JLabel lbl = (JLabel) e.getComponent();
        lbl.setForeground( new Color(0, 0, 0) );
        int length = orgStr.length();
        if(length>10){
            lbl.setText( String.format( "<html>%s</html>", orgStr.substring(0,9)+"..."));
        } else {
            lbl.setText(String.format("<html>%s</html>", orgStr));
        }
    }
    @Override
    public void mouseClicked(MouseEvent e){
        JLabel lbl = (JLabel) e.getComponent();
        JPanel parent = (JPanel) lbl.getParent();
        parent.remove(lbl);
        try {
            Main.deleteEvent(orgStr);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        parent.revalidate();
        parent.repaint();
    }
}