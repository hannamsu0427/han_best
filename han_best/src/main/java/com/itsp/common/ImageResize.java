package com.itsp.common;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageResize {

	public static final int RATIO = 0;
	public static final int SAME = -1;

	public static void resizeImage(String fileName, int maxWidth, int maxHeight, String tmpPath, String tmpFileName)
			throws IOException {

		String data = fileName;
		BufferedImage originalImage = ImageIO.read(new File(data));
		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

		Image srcImg = null;
		String suffix = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

		if (suffix.equals("bmp") || suffix.equals("png") || suffix.equals("gif")) {
			srcImg = ImageIO.read(new File(data));
		} else {
			// BMP가 아닌 경우 ImageIcon을 활용해서 Image 생성
			// 이렇게 하는 이유는 getScaledInstance를 통해 구한 이미지를
			// PixelGrabber.grabPixels로 리사이즈 할때
			// 빠르게 처리하기 위함이다.
			srcImg = new ImageIcon(fileName).getImage();
		}

		int srcWidth = srcImg.getWidth(null);
		int srcHeight = srcImg.getHeight(null);

		if (srcWidth > maxWidth) {
			float widthRatio = maxWidth / (float) srcWidth;
			srcWidth = (int) (srcWidth * widthRatio);
			srcHeight = (int) (srcHeight * widthRatio);
		}
		if (srcHeight > maxHeight) {
			float heightRatio = maxHeight / (float) srcHeight;
			srcWidth = (int) (srcWidth * heightRatio);
			srcHeight = (int) (srcHeight * heightRatio);
		}

		Image imgTarget = srcImg.getScaledInstance(srcWidth, srcHeight, Image.SCALE_SMOOTH);
		int pixels[] = new int[srcWidth * srcHeight];
		PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, srcWidth, srcHeight, pixels, 0, srcWidth);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			throw new IOException(e.getMessage());
		}

		BufferedImage destImg = new BufferedImage(srcWidth, srcHeight, type);
		destImg.setRGB(0, 0, srcWidth, srcHeight, pixels, 0, srcWidth);

		String newFileName = "resize_" + tmpFileName;
		String newPath = tmpPath + "/" + newFileName;

		ImageIO.write(destImg, suffix, new File(newPath));

	}
}