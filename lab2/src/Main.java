import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;

public class Main extends JPanel implements ActionListener {
  private static int maxWidth;
  private static int maxHeight;
  private final static double proportion = 0.75;
  private final static int trafficLightHeight = 175;
  private final static int trafficLightWidth = 110;
  private double tx = 1;
  private double ty = 0;
  private double angle = 0;
  private double circleAngle = 180;

  private Main() {
    Timer timer = new Timer(10, this);
    timer.start();
  }

  public void paint(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2d.setRenderingHints(rh);

    g2d.setBackground(Color.GRAY);
    g2d.clearRect(0, 0, maxWidth, maxHeight);
    drawFrame(g2d);


    g2d.translate(maxWidth / 2, maxHeight / 2);
    g2d.rotate(angle, 0, -1 * (double) trafficLightHeight / 2 * proportion);
    g2d.translate(tx, ty);

    drawTrafficLight(g2d);
  }

  private static GeneralPath triangle(int x1, int y1, int x2, int y2, int x3, int y3) {
    double[][] points = {
        {x1, y1}, {x2, y2},
        {x3, y3}, {x1, y1}
    };
    GeneralPath triangle = new GeneralPath();
    triangle.moveTo(points[0][0], points[0][1]);
    for (int k = 1; k < points.length; k++) triangle.lineTo(points[k][0], points[k][1]);
    triangle.closePath();
    return triangle;
  }

  private static void drawTrafficLight(Graphics2D g2d) {
    final int height = (int) (trafficLightHeight * Main.proportion);
    final int width = (int) (trafficLightWidth * Main.proportion);
    final int centerX = width / 2;
    final int centerY = height / 2;
    final int startX = -centerX;
    final int startY = -centerY;
    final int rectHeight = height / 2;
    final int rectWidth = width / 11;

    final int triangleIndent = height / 14;

    final int circlesNumber = 3;
    Color[] circleColors = {Color.RED, Color.YELLOW, Color.GREEN};
    final int betweenCircleAndTriangle = (int) (triangleIndent * 1.55);
    final int betweenCircles = (centerY - betweenCircleAndTriangle) / 18;

    final int circlesRadius = (betweenCircleAndTriangle - betweenCircles) / 2;


    g2d.setColor(Color.BLACK);
    g2d.fillRect(startX + centerX - rectWidth / 2, startY + centerY, rectWidth, rectHeight);

    g2d.setColor(Color.RED);
    g2d.fill(triangle(startX + centerX, startY, startX, startY + centerY, startX + width, startY + centerY));

    g2d.setColor(Color.WHITE);
    g2d.fill(triangle(startX + centerX, startY + triangleIndent, startX + triangleIndent, startY + centerY - triangleIndent / 2, startX + width - triangleIndent, startY + centerY - triangleIndent / 2));

    for (int i = 0; i < circlesNumber; i++) {
      g2d.setColor(circleColors[i]);
      g2d.fillOval(startX + centerX - circlesRadius, startY + triangleIndent + (i + 1) * betweenCircleAndTriangle - circlesRadius, 2 * circlesRadius, 2 * circlesRadius);
    }
  }

  private static void drawFrame(Graphics2D g2d) {
    final int frameIndent = 20;
    final int frameWidth = 16;
    BasicStroke bs3 = new BasicStroke(frameWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    GradientPaint gp = new GradientPaint(5, 25, Color.BLUE, 20, 2, Color.RED, true);
    g2d.setStroke(bs3);
    g2d.setPaint(gp);
    g2d.drawRect(frameIndent, frameIndent, maxWidth - 2 * frameIndent, maxHeight - 2 * frameIndent);
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Lab 2 Dzenik Danylo KP 62");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1500, 1000);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.add(new Main());

    frame.setVisible(true);

    Dimension size = frame.getSize();
    Insets insets = frame.getInsets();
    maxWidth = size.width - insets.left - insets.right - 1;
    maxHeight = size.height - insets.top - insets.bottom - 1;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    double radiusInSquare = 400 - trafficLightHeight;
    if (circleAngle >= 0 && circleAngle < 90){
      tx = Math.abs(radiusInSquare * Math.cos(Math.toRadians(circleAngle)));
      ty = -Math.abs(radiusInSquare * Math.sin(Math.toRadians(circleAngle)));
    }else if(circleAngle >= 90 && circleAngle < 180){
      tx = -Math.abs(radiusInSquare * Math.cos(Math.toRadians(circleAngle)));
      ty = -Math.abs(radiusInSquare * Math.sin(Math.toRadians(circleAngle)));
    }else if(circleAngle >= 180 && circleAngle < 270){
      tx = -Math.abs(radiusInSquare * Math.cos(Math.toRadians(circleAngle)));
      ty = Math.abs(radiusInSquare * Math.sin(Math.toRadians(circleAngle)));
    }else if(circleAngle >= 270 && circleAngle < 360){
      tx = Math.abs(radiusInSquare * Math.cos(Math.toRadians(circleAngle)));
      ty = Math.abs(radiusInSquare * Math.sin(Math.toRadians(circleAngle)));
    }else {
      circleAngle = 0;
    }
    circleAngle += 1;
    angle += 0.05;
    repaint();
  }
}
