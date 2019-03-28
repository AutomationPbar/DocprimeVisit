package com.utils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import javax.imageio.*;
import javax.imageio.stream.*;

public class CompressionFunction {
	
	static String filename = System.getProperty("user.dir") + "\\FailedTestsScreenshots"+File.separator;
    

  public static void compressed() throws IOException {

    File input = new File(filename+"concat.jpg");
    BufferedImage image = ImageIO.read(input);

    File compressedImageFile = new File(filename+"compressed_image.jpg");
    OutputStream os = new FileOutputStream(compressedImageFile);

    Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
    ImageWriter writer = (ImageWriter) writers.next();

    ImageOutputStream ios = ImageIO.createImageOutputStream(os);
    writer.setOutput(ios);

    ImageWriteParam param = writer.getDefaultWriteParam();

    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
    param.setCompressionQuality(0.7f);  // Change the quality value you prefer
    writer.write(null, new IIOImage(image, null, null), param);

    os.close();
    ios.close();
    writer.dispose();
  }
}