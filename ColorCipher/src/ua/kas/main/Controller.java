package ua.kas.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class Controller {

	@FXML
	ColorPicker colorPickerFirst;
	@FXML
	ColorPicker colorPickerSecond;
	@FXML
	ColorPicker colorPickerDecFirst;
	@FXML
	ColorPicker colorPickerDecSecond;

	@FXML
	TextField tf_enterText;

	private String path_txt;
	private String path_image;

	private ArrayList<String> alphabet = new ArrayList<>(Arrays.asList("а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и",
			"й", "к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь", "э",
			"ю", "я", " ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"));

	public void encript() {

		Color colorFirst = colorPickerFirst.getValue();
		Color colorSecond = colorPickerSecond.getValue();

		double redFirst = colorFirst.getRed() * 255;
		double greenFirst = colorFirst.getGreen() * 255;
		double blueFirst = colorFirst.getBlue() * 255;

		double redSecond = colorSecond.getRed() * 255;
		double greenSecond = colorSecond.getGreen() * 255;
		double blueSecond = colorSecond.getBlue() * 255;

		double redCounts = Math.abs(redFirst - redSecond);
		double greenCounts = Math.abs(greenFirst - greenSecond);
		double blueCounts = Math.abs(blueFirst - blueSecond);

		if (redCounts >= alphabet.size() || greenCounts >= alphabet.size() || blueCounts >= alphabet.size()) {
			double redCoeff = redCounts / (alphabet.size() - 1);
			double greenCoeff = greenCounts / (alphabet.size() - 1);
			double blueCoeff = blueCounts / (alphabet.size() - 1);

			double redLess = (redFirst > redSecond) ? redSecond : redFirst;
			double greenLess = (greenFirst > greenSecond) ? greenSecond : greenFirst;
			double blueLess = (blueFirst > blueSecond) ? blueSecond : blueFirst;

			try {
				String text = tf_enterText.getText();
				text.toLowerCase();

				String letter = "";
				int index;

				BufferedImage image = new BufferedImage(text.length(), 1, BufferedImage.TYPE_INT_ARGB);

				for (int i = 0; i < text.length(); i++) {
					int j = i + 1;

					letter = text.substring(i, j);

					index = alphabet.indexOf(letter);

					image.setRGB(i, 0,
							new java.awt.Color((int) Math.round(redLess + (index * redCoeff)),
									(int) Math.round(greenLess + (index * greenCoeff)),
									(int) Math.round(blueLess + (index * blueCoeff))).getRGB());
				}

				ImageIO.write(image, "png", new File("image.png"));

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Enter text! \n (а-я, \" \", 0-9) ");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Select other color!");
		}
	}

	public void decod() throws IOException {
		if (!(path_image.length() < 5) || !(path_txt.length() < 5)) {
			Color colorFirst = colorPickerDecFirst.getValue();
			Color colorSecond = colorPickerDecSecond.getValue();

			double redFirst = colorFirst.getRed() * 255;
			double greenFirst = colorFirst.getGreen() * 255;
			double blueFirst = colorFirst.getBlue() * 255;

			double redSecond = colorSecond.getRed() * 255;
			double greenSecond = colorSecond.getGreen() * 255;
			double blueSecond = colorSecond.getBlue() * 255;

			double redCounts = Math.abs(redFirst - redSecond);
			double greenCounts = Math.abs(greenFirst - greenSecond);
			double blueCounts = Math.abs(blueFirst - blueSecond);

			if (redCounts >= alphabet.size() || greenCounts >= alphabet.size() || blueCounts >= alphabet.size()) {
				double redCoeff = redCounts / (alphabet.size() - 1);
				double greenCoeff = greenCounts / (alphabet.size() - 1);
				double blueCoeff = blueCounts / (alphabet.size() - 1);

				double redLess = (redFirst > redSecond) ? redSecond : redFirst;
				double greenLess = (greenFirst > greenSecond) ? greenSecond : greenFirst;
				double blueLess = (blueFirst > blueSecond) ? blueSecond : blueFirst;

				BufferedImage image = ImageIO.read(new File(path_image));
				int pixel, red, green, blue;
				int indexRed, indexGreen, indexBlue, biggest;
				String text = "";

				try {
					for (int width = 0; width < image.getWidth(); width++) {
						pixel = image.getRGB(width, 0);

						// alpha = (pixel >> 24) & 0xff;
						red = (pixel >> 16) & 0xff;
						green = (pixel >> 8) & 0xff;
						blue = (pixel >> 0) & 0xff;

						indexRed = (int) Math.round((red - redLess) / redCoeff);
						indexGreen = (int) Math.round((green - greenLess) / greenCoeff);
						indexBlue = (int) Math.round((blue - blueLess) / blueCoeff);

						biggest = (indexRed > indexGreen) ? indexRed : indexGreen;
						biggest = (biggest > indexBlue) ? biggest : indexBlue;

						text += alphabet.get(biggest);
					}

					writeFile(text);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Enter text!");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Select other color!");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Select image and txt file!");
		}
	}

	public void openImage(ActionEvent e) throws IOException {
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image (*.png)", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showOpenDialog(null);

		path_image = file.getAbsolutePath();
	}

	public void selectTxt(ActionEvent e) throws IOException {
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(null);
		FileWriter fileWriter;

		try {
			fileWriter = new FileWriter(file);
			fileWriter.close();

			path_txt = file.getAbsolutePath();

		} catch (Exception ex) {
		}
	}

	private void writeFile(String text) throws IOException {
		FileWriter fileWriter;
		fileWriter = new FileWriter(new File(path_txt));

		fileWriter.write(text);

		fileWriter.flush();
		fileWriter.close();
	}
}
