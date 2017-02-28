package ua.kas.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) throws IOException {

		BufferedImage image = ImageIO.read(new File("res/icon.png"));
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

		// Color color = null;
		int pixel, alpha, red, green, blue;

		for (int width = 0; width < image.getWidth(); width++) {
			for (int height = 0; height < image.getHeight(); height++) {
				// color = new Color(image.getRGB(width, height));
				pixel = image.getRGB(width, height);

				alpha = (pixel >> 24) & 0xff;
				red = (pixel >> 16) & 0xff;
				green = (pixel >> 8) & 0xff;
				blue = (pixel >> 0) & 0xff;

				newImage.setRGB(width, height, new Color(red, 0, 0, alpha).getRGB());
			}
		}

		ImageIO.write(newImage, "png", new File("res/image.png"));

	}

}
