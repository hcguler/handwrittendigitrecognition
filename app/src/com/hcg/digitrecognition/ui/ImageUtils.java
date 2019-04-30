package com.hcg.digitrecognition.ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;

public class ImageUtils {

  // http://yann.lecun.com/exdb/mnist/
  public static final int MNIST_DIGIT_BOUNDS_SIZE = 20;
  public static final int MNIST_IMAGE_SIZE = 28;

  // This value is recomendation from https://deeplearning4j.org/rbm-mnist-tutorial.html
  private static final int GRAYSCALE_TO_BLACK_AND_WHITE_MIN_LIMIT = 35;
  private static final int BLACK_AND_WHITE_WHTIE_VALUE = 0;
  private static final int BLACK_AND_WHITE_BLACK_VALUE = 255;

  public static double[] getPixelsAndConvertToBlackAndWhite(final BufferedImage image) {
    final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    final double[] result = new double[pixels.length];
    for (int i = 0; i < pixels.length; i++) {
      // Convert the signed pixel value to unsigned byte
      result[i] = (double) (~(int)pixels[i] & 0xFF);
      if (result[i] < GRAYSCALE_TO_BLACK_AND_WHITE_MIN_LIMIT) {
        result[i] = BLACK_AND_WHITE_WHTIE_VALUE;
      } else {
        result[i] = BLACK_AND_WHITE_BLACK_VALUE;
      }
    }
    return result;
  }

  /**
   * Finds the bounding rectangle of black shape
   *
   * @param image
   * @return
   */
  public static Rectangle findBoundsOfBlackShape(final BufferedImage image) {
    final int width = image.getWidth();
    final int height = image.getHeight();
    final double[] pixels = ImageUtils.getPixelsAndConvertToBlackAndWhite(image);

    int yMin = Integer.MAX_VALUE;
    int yMax = -1;
    int xMin = Integer.MAX_VALUE;
    int xMax = -1;

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        double pixel = pixels[y * image.getWidth() + x];
        if (pixel == BLACK_AND_WHITE_BLACK_VALUE) {
          if (y < yMin) {
            yMin = y;
          }
          if (x < xMin) {
            xMin = x;
          }

          if (y > yMax) {
            yMax = y;
          }
          if (x > xMax) {
            xMax = x;
          }
        }
      }
    }

    if (xMin == -1 || yMin == -1 || xMax == Integer.MAX_VALUE || yMax == Integer.MAX_VALUE) {
      throw new IllegalArgumentException("There is no black shape in the image");
    }

    return new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin);
  }

  public static BufferedImage scale(final BufferedImage original,
                                    final int newWidth,
                                    final int newHeight) {

    final BufferedImage resized = new BufferedImage(newWidth, newHeight, original.getType());
    final Graphics2D graphics = createGraphicsAndPaintWhite(resized);

    graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    graphics.drawImage(original,
        0,0,
        newWidth,newHeight,
        0,0,
        original.getWidth(),
        original.getHeight(),null);
    graphics.dispose();
    return resized;
  }

  public static BufferedImage addImageToCenter(final BufferedImage image,
                                               final int newWidth,
                                               final int newHeight) {

    final BufferedImage newBackground = new BufferedImage(newWidth, newHeight, image.getType());
    final Graphics2D graphics = createGraphicsAndPaintWhite(newBackground);

    final Point centerMass = findCenterOfMass(image);
    graphics.drawImage(image,
        (newWidth / 2 - centerMass.x),
        (newHeight / 2 - centerMass.y),
        Color.WHITE,
        null);

    graphics.dispose();
    return newBackground;
  }

  // Shamelessly copied from
  // http://stackoverflow.com/questions/10245220/java-image-resize-maintain-aspect-ratio
  public static Dimension getScaledMnistDigitDimensions(final Rectangle bounds) {
    final int originalWidth = bounds.width;
    final int originalHeight = bounds.height;
    final int boundWidth = ImageUtils.MNIST_DIGIT_BOUNDS_SIZE;
    final int boundHeight = ImageUtils.MNIST_DIGIT_BOUNDS_SIZE;
    int newWidth = originalWidth;
    int newHeight = originalHeight;
    // first check if we need to scale width
    if (originalWidth > boundWidth) {
      //scale width to fit
      newWidth = boundWidth;
      //scale height to maintain aspect ratio
      newHeight = (newWidth * originalHeight) / originalWidth;
    }
    // then check if we need to scale even with the new height
    if (newHeight > boundHeight) {
      //scale height to fit instead
      newHeight = boundHeight;
      //scale width to maintain aspect ratio
      newWidth = (newHeight * originalWidth) / originalHeight;
    }
    return new Dimension(newWidth, newHeight);
  }

  private static Point findCenterOfMass(final BufferedImage image) {
    double[] pixels = getPixelsAndConvertToBlackAndWhite(image);

    int totalMass = 0;
    int totalX = 0;
    int totalY = 0;

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        if (pixels[y * image.getWidth() + x] == BLACK_AND_WHITE_BLACK_VALUE) {
          totalX += x;
          totalY += y;
          totalMass++;
        }
      }
    }

    return new Point(totalX / totalMass, totalY / totalMass);
  }

  private static Graphics2D createGraphicsAndPaintWhite(final BufferedImage image) {
    final Graphics2D graphics = image.createGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
    return graphics;
  }

}
