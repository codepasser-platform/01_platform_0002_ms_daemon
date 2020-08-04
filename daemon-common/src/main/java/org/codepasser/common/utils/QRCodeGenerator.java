package org.codepasser.common.utils;

import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.codepasser.common.exception.CommonException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import static com.google.common.collect.ImmutableMap.of;
import static com.google.zxing.BarcodeFormat.QR_CODE;
import static com.google.zxing.EncodeHintType.CHARACTER_SET;
import static org.codepasser.common.exception.CommonException.Error.QR_CODE_GENERATE_FAILED;

/**
 * QRCodeGenerator.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public final class QRCodeGenerator {

  private static final int BLACK = 0xFF000000;
  private static final int WHITE = 0xFFFFFFFF;

  private QRCodeGenerator() {}

  public static QRCodeImage generate(String content, int width, int height) throws CommonException {
    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
    BitMatrix bitMatrix;
    try {
      bitMatrix =
          multiFormatWriter.encode(content, QR_CODE, width, height, of(CHARACTER_SET, "UTF-8"));
    } catch (WriterException e) {
      throw new CommonException(QR_CODE_GENERATE_FAILED, e);
    }

    return new QRCodeImage(bitMatrix);
  }

  public static final class QRCodeImage {

    private final BitMatrix matrix;

    private QRCodeImage(BitMatrix matrix) {
      this.matrix = matrix;
    }

    public void writeToStream(String format, OutputStream stream) throws CommonException {
      BufferedImage image = toBufferedImage();
      try {
        ImageIO.write(image, format, stream);
      } catch (IOException e) {
        throw new CommonException(QR_CODE_GENERATE_FAILED, e);
      }
    }

    private BufferedImage toBufferedImage() {
      int width = this.matrix.getWidth();
      int height = this.matrix.getHeight();
      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          image.setRGB(x, y, this.matrix.get(x, y) ? BLACK : WHITE);
        }
      }
      return image;
    }
  }
}
