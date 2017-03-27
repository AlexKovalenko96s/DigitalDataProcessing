package ua.kas.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class Controller {

	@FXML
	ImageView imageV_1;
	@FXML
	ImageView imageV_2;

	@FXML
	ScrollBar scr_rotate;

	@FXML
	VBox vBoxR = new VBox();
	@FXML
	VBox vBoxG = new VBox();
	@FXML
	VBox vBoxB = new VBox();

	@FXML
	TextField tf_v;

	private String path_image;

	private BufferedImage newImage;

	private ArrayList<Integer> grafR = new ArrayList<>();
	private ArrayList<Integer> grafG = new ArrayList<>();
	private ArrayList<Integer> grafB = new ArrayList<>();

	public void openImage(ActionEvent e) throws IOException {
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image (*.png)", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);

		File file = fileChooser.showOpenDialog(null);

		path_image = file.getAbsolutePath();

		File file1 = new File(path_image);
		Image image = new Image(file1.toURI().toString());
		imageV_1.setImage(image);
	}

	public void go() throws IOException {
		for (int i = 0; i < 256; i++) {
			grafR.add(0);
			grafG.add(0);
			grafB.add(0);
		}

		BufferedImage image = ImageIO.read(new File(path_image));
		newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

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

				grafR.set(red, grafR.get(red) + 1);
				grafG.set(green, grafG.get(green) + 1);
				grafB.set(blue, grafB.get(blue) + 1);

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
		newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

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

	public void rotate() throws IOException {
		BufferedImage image = ImageIO.read(new File(path_image));
		newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

		// Color color = null;
		int pixel, alpha, red, green, blue;
		int x, y;

		for (int width = 0; width < image.getWidth(); width++) {
			for (int height = 0; height < image.getHeight(); height++) {
				// color = new Color(image.getRGB(width, height));

				x = (int) (((width - (image.getWidth() / 2)) * Math.cos(scr_rotate.getValue() * (Math.PI / 180)))
						- ((height - (image.getHeight() / 2)) * Math.sin(scr_rotate.getValue() * (Math.PI / 180)))
						+ (image.getWidth() / 2));
				y = (int) (((width - (image.getWidth() / 2)) * Math.sin(scr_rotate.getValue() * (Math.PI / 180)))
						+ ((height - (image.getHeight() / 2)) * Math.cos(scr_rotate.getValue() * (Math.PI / 180)))
						+ (image.getHeight() / 2));

				if ((x > 0 && x < image.getWidth()) && (y > 0 && y < image.getHeight())) {
					pixel = image.getRGB(x, y);

					alpha = (pixel >> 24) & 0xff;
					red = (pixel >> 16) & 0xff;
					green = (pixel >> 8) & 0xff;
					blue = (pixel >> 0) & 0xff;

					newImage.setRGB(width, height, new Color(255 - red, 255 - green, 255 - blue, alpha).getRGB());
				}
			}
		}
		Image card = SwingFXUtils.toFXImage(newImage, null);

		imageV_2.setImage(card);
	}

	public void save(ActionEvent e) throws IOException {
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(null);
		FileWriter fileWriter;
		String path = null;
		try {
			fileWriter = new FileWriter(file);
			fileWriter.close();

			path = file.getAbsolutePath();

		} catch (Exception ex) {
		}

		ImageIO.write(newImage, "png", new File(path));
	}

	public void graf() {
		LineChart chart = new LineChart(new NumberAxis(), new NumberAxis());
		XYChart.Series series = new XYChart.Series();
		series.setName("Red");

		LineChart chartG = new LineChart(new NumberAxis(), new NumberAxis());
		XYChart.Series seriesG = new XYChart.Series();
		seriesG.setName("Green");

		LineChart chartB = new LineChart(new NumberAxis(), new NumberAxis());
		XYChart.Series seriesB = new XYChart.Series();
		seriesB.setName("Blue");

		for (int i = 0; i < grafR.size(); i++) {
			series.getData().add(new XYChart.Data(i, grafR.get(i)));
			seriesG.getData().add(new XYChart.Data(i, grafG.get(i)));
			seriesB.getData().add(new XYChart.Data(i, grafB.get(i)));
		}

		chart.getData().add(series);
		vBoxR.getChildren().add(chart);

		chartG.getData().add(seriesG);
		vBoxG.getChildren().add(chartG);

		chartB.getData().add(seriesB);
		vBoxB.getChildren().add(chartB);

	}
}
