package com.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
/**
 * This code try to join two BufferedImage
 * @author wangdq
 * 2013-12-29
 */
public class CombineMultipleImages {
	
	static String imageurl = "";
    public static void joinimages()
    {   
    	String filepath = System.getProperty("user.dir") + "\\FailedTestsScreenshots";
        String filename = System.getProperty("user.dir") + "\\FailedTestsScreenshots"+File.separator;
               
        File folder = new File(System.getProperty("user.dir") + "\\FailedTestsScreenshots"+File.separator);
		File[] listOfFiles = folder.listFiles();
		
		System.out.println("size of files" + listOfFiles.length);
			 int imagesCount = listOfFiles.length;
	        BufferedImage images[] = new BufferedImage[imagesCount];
		
		for (int i = 0; i < listOfFiles.length; i++) {
			  File file = listOfFiles[i];
			  if (file.getName().endsWith((".jpg")))  {
				  //System.out.println("the excel file name is " +file.getName());
				  try {
					images[i] = ImageIO.read(new File(filename+file.getName()));
					Graphics2D g2d = images[i].createGraphics();
		            //g2d.drawRect(10, 10, 80, 80);
		            g2d.dispose();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		            
			    /* do somthing with content */
			  } 
		  
		}
		
		
	        int heightTotal = 0;
	        int widthTotal = 0;
	        int width = images[0].getWidth();;
	        for(int j = 0; j < images.length; j++) {
	            heightTotal += images[j].getHeight();
	            widthTotal += images[j].getWidth();
	            
	        }
	       // System.out.println("heighttotal is " + heightTotal);
	      //  System.out.println("widthtotal is " + widthTotal);
	        int heightCurr = 0;
	        BufferedImage concatImage = new BufferedImage(width, heightTotal, BufferedImage.TYPE_INT_RGB);
	        Graphics2D g2d = concatImage.createGraphics();
	        g2d.setPaint(Color.YELLOW);
        	g2d.fillRect(0, 0, widthTotal, heightTotal);
	        for(int j = 0; j < images.length; j++) {
	            g2d.drawImage(images[j], 0, heightCurr, null);
	            heightCurr += images[j].getHeight()+50;
	        }
	        g2d.dispose();
	        
	        try {
				ImageIO.write(concatImage, "jpg", new File(filename+"concat.jpg"));// export concat image
				CompressionFunction.compressed();
				imageurl =S3bucketurl.GetUrl("compressed_image.jpg","","", "JPEG","Image", "Automation", filename+"compressed_image.jpg");
				
				System.out.println("The image url is " +imageurl );
				FileUtils.cleanDirectory(new File(filepath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	        
 
      
    }
    /**
     * join two BufferedImage
     * you can add a orientation parameter to control direction
     * you can use a array to join more BufferedImage
     */

    public static BufferedImage joinBufferedImage(BufferedImage img1,
    	      BufferedImage img2) {
    	    //int offset = 2;
    	    int width = img1.getWidth();
    	    int height = img1.getHeight() + img2.getHeight();
    	    BufferedImage newImage = new BufferedImage(width, height,
    	        BufferedImage.TYPE_INT_ARGB);
    	    Graphics2D g2 = newImage.createGraphics();
    	    Color oldColor = g2.getColor();
    	    g2.setPaint(Color.WHITE);
    	    g2.fillRect(0, 0, width, height);
    	    g2.setColor(oldColor);
    	    g2.drawImage(img1, null, 0, 0);
    	    g2.drawImage(img2, null, 0, img1.getHeight()+50);
    	    g2.dispose();
    	    return newImage;
    	  }
}
