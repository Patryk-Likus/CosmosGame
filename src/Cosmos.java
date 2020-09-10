import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Cosmos extends JPanel {

    class Meteoryt {

        int x;
        int y;
        int size;
        int dx;
        int dy;

        public Meteoryt() {
            Random random = new Random();

            size = (random.nextInt(10) + 1) * 20;

            int vector = random.nextInt(4);

            if (vector == 0) {
                x = 0;
                y = random.nextInt(1000);
                dx = random.nextInt(15) + 1;
                dy = random.nextInt(15) - 7;
            } else if (vector == 1) {
                x = random.nextInt(1000);
                y = 0;
                dx = random.nextInt(15) - 7;
                dy = random.nextInt(15) + 1;
            } else if (vector == 2) {
                x = random.nextInt(1000);
                y = 1000;
                dx = random.nextInt(15) - 7;
                dy = random.nextInt(15) - 7;
            } else if (vector == 3) {
                x = 1000;
                y = random.nextInt(1000);
                dx = random.nextInt(15) - 7;
                dy = random.nextInt(15) - 7;
            }
        }

        public boolean isVisible() {
            return x + size >= 0 && y + size >= 0 && x < 1000 && y < 1000;
        }

        public void motion() {
            x += dx;
            y += dy;
        }
    }


    int x = 10;
    int y = 10;

    ArrayList<Meteoryt> meteoryts = new ArrayList<>();


    public Cosmos() {

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {

                while (x != e.getX() || y != e.getY()) {
                    x += e.getX() - x;
                    y += e.getY() - y;

                    repaint();
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    meteoryts.add(new Meteoryt());
                }
            }
        }).start();
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            g.drawImage(ImageIO.read(new File("tlo.png")), 0, 0, null);
            g.drawImage(ImageIO.read(new File("ufo.png")), x - 20, y - 20, null);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Meteoryt[] meteorytsTab = new Meteoryt[meteoryts.size()];
        meteoryts.toArray(meteorytsTab);

        for(Meteoryt meteor: meteorytsTab){
            try{
                meteor.motion();
                g.drawImage(resize(ImageIO.read(new File("meteor.png")), meteor.size, meteor.size), meteor.x, meteor.y, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static BufferedImage resize(BufferedImage img, int height, int width){
        Image tmp = img.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
        BufferedImage scalling = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = scalling.createGraphics();
        graphics2D.drawImage(tmp, 0, 0, null);
        graphics2D.dispose();
        return scalling;
    }

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
