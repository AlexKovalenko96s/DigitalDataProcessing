package ua.kas.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class Controller {

	@FXML
	ImageView imageV_1;
	@FXML
	ImageView imageV_2;

	@FXML
	TextField tf_v;

	private String path_image;

	public void openImage(ActionEvent e) throws IOException {
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image (*.png)", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showOpenDialog(null);

		path_image = file.getAbsolutePath();

		File file1 = new File(path_image);
		Image image = new Image(file1.toURI().toString());
		imageV_1.setImage(image);
	}

	public void go() throws IOException {
		BufferedImage image = ImageIO.read(new File(path_image));
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

				newImage.setRGB(image.getWidth() - width - 1, height,
						new Color(255 - red, 255 - green, 255 - blue, alpha).getRGB());
			}
		}

		ImageIO.write(newImage, "png", new File("image.png"));

		Image card = SwingFXUtils.toFXImage(newImage, null);

		imageV_2.setImage(card);

	}

	public void window() throws IOException {
		BufferedImage image = ImageIO.read(new File(path_image));
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Random random = new Random();

		// Color color = null;
		int pixel = 0, alpha, red, green, blue;
		int x, y;

		int v = Integer.parseInt(tf_v.getText());

		for (int width = 0; width < image.getWidth(); width++) {
			for (int height = 0; height < image.getHeight(); height++) {
				// color = new Color(image.getRGB(width, height));

				x = width + (int) ((random.nextDouble() - 0.5) * v);
				y = height + (int) ((random.nextDouble() - 0.5) * v);

				if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
					pixel = image.getRGB(x, y);

					alpha = (pixel >> 24) & 0xff;
					red = (pixel >> 16) & 0xff;
					green = (pixel >> 8) & 0xff;
					blue = (pixel >> 0) & 0xff;

					newImage.setRGB(image.getWidth() - width - 1, height, new Color(red, green, blue, alpha).getRGB());
				}
			}
		}

		ImageIO.write(newImage, "png", new File("windiw.png"));

		Image card = SwingFXUtils.toFXImage(newImage, null);

		imageV_2.setImage(card);

	}
}
