package com.hcg.digitrecognition.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class DrawPanel extends JPanel {

  final private List<Point> pointsDrawn;

  public DrawPanel() {
    pointsDrawn = new ArrayList<>();
    this.setBackground(Color.WHITE);
    addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        pointsDrawn.add(e.getPoint());
        repaint();
      }
    });
  }

  public void clear(){
    pointsDrawn.clear();
    repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    final Graphics2D graphics = (Graphics2D) g;
    final BasicStroke stroke = new BasicStroke(12);
    graphics.setStroke(stroke);
    graphics.setColor(Color.BLACK);

    for (int i = 0; i < pointsDrawn.size() - 1; i++) {
      final Point p1 = pointsDrawn.get(i);
      graphics.draw(new Line2D.Double(p1, p1));
    }
  }
  //panel içeriğinden image oluşturulur.
  public BufferedImage createImage() {
    final int width = this.getWidth();
    final int height = this.getHeight();
    final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
    final Graphics2D graphics = image.createGraphics();
    this.paint(graphics);
    graphics.dispose();
    return image;
  }
}
