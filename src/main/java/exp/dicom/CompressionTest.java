package exp.dicom;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

//31.5MB vs. 35.5M

public class CompressionTest
{
	public static void main(String[] args)
	{
		int[] grayInputArray = new int[1];
		int[] grayArray = new int[1];
		int[] pngGrayArray = new int[2];

		try { // 375-534
			// File input = new File(args[0]);

			// for (int i = 375; i <= 534; i += 2) {
			for (int i = 0; i < 9; i += 2) {

				/*
				 * File inputFile1 = new File(
				 * "/Users/martin/common/rubin-lab/dicom-files/PFLEINKESMONPRDD6/1.2.840.113619.2.144.45424936.6676.1283868244."
				 * + i + ".dcm"); File inputFile2 = new File(
				 * "/Users/martin/common/rubin-lab/dicom-files/PFLEINKESMONPRDD6/1.2.840.113619.2.144.45424936.6676.1283868244."
				 * + (i + 1) + ".dcm");
				 */

				File inputFile1 = new File("/Users/martin/common/rubin-lab/dicom-files/daniel/00000" + i + ".dcm");
				File inputFile2 = new File("/Users/martin/common/rubin-lab/dicom-files/daniel/00000" + (i + 1) + ".dcm");

				System.out.println("file1 " + inputFile1.getAbsolutePath());
				System.out.println("file2 " + inputFile2.getAbsolutePath());

				DicomReader dicomReader1 = new DicomReader(inputFile1);
				DicomReader dicomReader2 = new DicomReader(inputFile2);
				File outputFile = new File("/Users/martin/tmp/generated/generated" + i + ".png");
				OutputStream outputStream = new FileOutputStream(outputFile);
				BufferedImage pngImage1 = dicomReader1.getPNGImage();
				BufferedImage pngImage2 = dicomReader2.getPNGImage();
				WritableRaster raster1 = pngImage1.getRaster();
				WritableRaster raster2 = pngImage2.getRaster();
				BufferedImage pngDeltaImage = new BufferedImage(raster1.getWidth(), raster1.getHeight(),
						BufferedImage.TYPE_USHORT_GRAY);
				WritableRaster deltaRaster = pngDeltaImage.getRaster();

				int numberOfDifferentPixels = 0;
				for (int x = 0; x < raster1.getWidth(); x++) {
					for (int y = 0; y < raster1.getHeight(); y++) {
						grayArray = raster1.getPixel(x, y, grayInputArray);
						int pixelValue1 = grayArray[0];
						grayArray = raster2.getPixel(x, y, grayInputArray);
						int pixelValue2 = grayArray[0];
						int deltaValue;
						if (pixelValue1 != pixelValue2) {
							numberOfDifferentPixels++;
							deltaValue = pixelValue1 - pixelValue2;
						} else
							deltaValue = 0;
						pngGrayArray[1] = high(deltaValue);
						pngGrayArray[0] = low(deltaValue);
						deltaRaster.setPixel(x, y, pngGrayArray);
					}
				}
				System.out.println("Number of different " + numberOfDifferentPixels);
				System.out.println("Number " + (float)numberOfDifferentPixels
						/ (float)(raster1.getWidth() * raster1.getHeight()));

				ImageIO.write(pngDeltaImage, "png", outputStream);
				outputStream.close();
				System.out.print(".");
			}
			System.out.println("\nDone.");
		} catch (FileNotFoundException e) {
			System.out.println(e.getClass().getName() + " " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getClass().getName() + " " + e.getMessage());
			e.printStackTrace();
		} finally {
		}
	}

	private static int high(int value)
	{
		return (value >> 8) & 255;
	}

	private static int low(int value)
	{
		return value & 255;
	}

}
