import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class RoundedImageAnimation extends JPanel implements ActionListener {
    private BufferedImage image;
    private int x, y;
    private int panelWidth, panelHeight;
    private Timer timer;

    public RoundedImageAnimation() {
        try {
            image = ImageIO.read(new File("image_wet_otters.jpeg")); // Замените на ваш путь и название изображения
            int newWidth = 300; // Новая ширина изображения
            int newHeight = 200; // Новая высота изображения
            image = resizeImage(image, newWidth, newHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }

        x = 0;
        y = 0;
        panelWidth = 800; // Ширина панели
        panelHeight = 600; // Высота панели

        timer = new Timer(10, this);
        timer.start();
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return resizedImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int cornerRadius = 30; // Радиус скругления углов
        Shape roundedRect = new RoundRectangle2D.Double(x, y, image.getWidth(), image.getHeight(), cornerRadius, cornerRadius);

        g2d.clip(roundedRect);
        g2d.drawImage(image, x, y, this);

        g2d.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x++;
        y++;
        if (x > panelWidth || y > panelHeight) {
            x = -image.getWidth(); // Сдвигаем изображение за левый верхний угол
            y = -image.getHeight();
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rounded Image Animation");
        RoundedImageAnimation panel = new RoundedImageAnimation();
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
