import javax.swing.*;
import java.awt.*;

public class Cosmos extends JPanel {


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 1000);
    }

    public static void main(String[] args) {

        JFrame window = new JFrame("Cosmos");
        Cosmos cosmos = new Cosmos();

        window.add(cosmos);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(480, 5);
        window.setVisible(true);
        window.pack();
    }
}
