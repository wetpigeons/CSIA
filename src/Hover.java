import java.awt.Color; //Credit to TabsPH on github
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

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
        lbl.setText( String.format( "<html><u>%s</u></html>", lblStr) );
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JLabel lbl = (JLabel) e.getComponent();
        lbl.setText( String.format( "<html>%s</html>", orgStr ) );
        lbl.setForeground( new Color(0, 0, 0) );
    }
}