package exp.dicom;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.stream.FileImageInputStream;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.imageio.plugins.dcm.DicomImageReadParam;
import org.dcm4che2.imageioimpl.plugins.dcm.DicomImageReader;
import org.dcm4che2.imageioimpl.plugins.dcm.DicomImageReaderSpi;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.io.StopTagInputHandler;

public class DicomReader
{
	protected final File dicomFile;

	public DicomReader(File value)
	{
		dicomFile = value;
	}

	public DicomObject getDicomObject() throws Exception
	{
		DicomInputStream dis = null;
		StopTagInputHandler stop = new StopTagInputHandler(Tag.PixelData);
		dis = new DicomInputStream(dicomFile);
		dis.setHandler(stop);
		dis.close();
		return dis.readDicomObject();
	}

	public BufferedImage getImage() throws IOException
	{
		return getImage(0);
	}

	public BufferedImage getImage(int frameValue) throws IOException
	{
		FileImageInputStream fis = null;
		fis = new FileImageInputStream(dicomFile);
		DicomImageReader codec = (DicomImageReader)new DicomImageReaderSpi().createReaderInstance();
		codec.setInput(fis);
		DicomImageReadParam param = (DicomImageReadParam)codec.getDefaultReadParam();
		BufferedImage image = codec.read(frameValue, param);
		fis.close();
		return image;
	}

	public BufferedImage getPNGImage() throws IOException
	{
		return getPNGImage(0);
	}

	public BufferedImage getPNGImage(int frameValue) throws IOException
	{
		FileImageInputStream fis = null;
		DicomInputStream dis = null;
		StopTagInputHandler stop = new StopTagInputHandler(Tag.PixelData);
		dis = new DicomInputStream(dicomFile);
		dis.setHandler(stop);
		DicomObject object = dis.readDicomObject();
		RasterProcessor rasterProcessor = new RasterProcessor(object);
		dis.close();
		fis = new FileImageInputStream(dicomFile);
		DicomImageReader codec = (DicomImageReader)new DicomImageReaderSpi().createReaderInstance();
		codec.setInput(fis);
		DicomImageReadParam param = (DicomImageReadParam)codec.getDefaultReadParam();
		Raster raster = codec.readRaster(frameValue, param);
		BufferedImage pngImage = rasterProcessor.buildPng(raster);
		dis.close();
		return pngImage;
	}

}
