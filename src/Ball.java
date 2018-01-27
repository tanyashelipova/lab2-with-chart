import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Ball extends JPanel implements ActionListener {
    private Ellipse2D.Float ellipse = new Ellipse2D.Float();

    static int r, d, dx, dy, dt, t, t0, i, width, height, h;
    static double x, y, k, maxX, maxY;
    static ArrayList hs, ts;
    final double g = 9.80665;
    boolean initialize = true;
    Timer timer;

    public Ball() {
        hs = new ArrayList<String>();
        ts = new ArrayList<String>();

        try {
            Scanner sc = new Scanner(new File("input.txt"));
            ArrayList<String> lines = new ArrayList<String>();
            for (int j = 0; j < 8; j++) {
                lines.add(sc.nextLine());
            }
            r = Integer.valueOf(lines.get(0));
            d = r * 2;

            x = Double.valueOf(lines.get(1));
            y = Double.valueOf(lines.get(2));

            dx = Integer.valueOf(lines.get(3));
            dy = Integer.valueOf(lines.get(4));

            dt = Integer.valueOf(lines.get(5));

            t = Integer.valueOf(lines.get(6)) * 1000;
            t0 = t;

            k = Double.valueOf(lines.get(7));
        }
        catch (IOException e){

        }

        i = 0;
        width = 794;
        height = 471;

        setXY(x, y, r);
        timer = new Timer(dt, this);
        timer.setInitialDelay(0);
        timer.start();
    }

    public void setXY(double xx, double yy, double r) {
        x = xx;
        y = yy;
        ellipse.setFrame(x - r, y - r, d, d);
    }

    public void reset(double w, double h) {
        maxX = w;
        maxY = h;
        initialize = false;
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Dimension size = getSize();
        if (initialize) reset(size.width, size.height);
        this.step();
        render(g2);
    }

    public void step() {
        if (t > 0) {
            double x0 = x;
            double y0 = y;
            h = (int) (height - y - r);

            setXY(x, y, r);
            i++;
            x += dx;
            y += dy;
            if (x - r < 0) {
                x = r;
                dx *= -k;
                dy *= k;
            }
            if (x + r > maxX) {
                x = maxX - r;
                dx *= -k;
                dy *= k;
            }
            if (y - r < 0) {
                dy *= -k;
                dx *= 0.99;
                y = r;
            }
            if (y + r > maxY) {
                dy *= -k;
                dx *= 0.99;
                y = maxY - r;
            }
            dy += g / 4;
            t -= dt;

            String res = "t" + i + ": (" + x0 + "," + y0 + ") -> " + "(" + x + "," + y + ") " + "\n";
            //String gr = t + " " + h;

            System.out.print(res);
            //System.out.println(gr);
            hs.add(h + "\n");
            ts.add(t + "\n");

        } else {
            timer.stop();

            try (FileWriter writer = new FileWriter("C:\\Users\\1\\OneDrive\\Documents\\uni\\8\\compSimulation\\lab2\\hs.txt", false)) {
                for (int j = 0; j < hs.size(); j++) {
                    writer.write(hs.get(j).toString());
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            try (FileWriter writer = new FileWriter("C:\\Users\\1\\OneDrive\\Documents\\uni\\8\\compSimulation\\lab2\\ts.txt", false)) {
                for (int j = 0; j < ts.size(); j++) {
                    writer.write(ts.get(j).toString());
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


    public void render(Graphics2D g2) {
        g2.setColor(Color.ORANGE);
        g2.fill(ellipse);
        g2.draw(ellipse);
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Ball");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Ball());
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}