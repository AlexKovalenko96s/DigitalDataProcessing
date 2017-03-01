package ua.kas.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class Controller {

	@FXML
	ColorPicker colorPickerFirst;
	@FXML
	ColorPicker colorPickerSecond;

	@FXML
	TextField tf_enterText;

	private ArrayList<String> alphabet = new ArrayList<>(Arrays.asList("а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и",
			"й", "к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь", "э",
			"ю", "я", " ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"));

	public void encript() {

		Color colorFirst = colorPickerFirst.getValue();
		Color colorSecond = colorPickerSecond.getValue();

		int redFirst = (int) Math.round(colorFirst.getRed() * 255);
		int greenFirst = (int) Math.round(colorFirst.getGreen() * 255);
		int blueFirst = (int) Math.round(colorFirst.getBlue() * 255);

		int redSecond = (int) Math.round(colorSecond.getRed() * 255);
		int greenSecond = (int) Math.round(colorSecond.getGreen() * 255);
		int blueSecond = (int) Math.round(colorSecond.getBlue() * 255);

		double redCounts = Math.abs(redFirst - redSecond);
		double greenCounts = Math.abs(greenFirst - greenSecond);
		double blueCounts = Math.abs(blueFirst - blueSecond);

		if (redCounts >= alphabet.size() || greenCounts >= alphabet.size() || blueCounts >= alphabet.size()) {
			double redCoeff = redCounts / alphabet.size();
			double greenCoeff = greenCounts / alphabet.size();
			double blueCoeff = blueCounts / alphabet.size();

			int redLess = (redFirst > redSecond) ? redSecond : redFirst;
			int greenLess = (greenFirst > greenSecond) ? greenSecond : greenFirst;
			int blueLess = (blueFirst > blueSecond) ? blueSecond : blueFirst;

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
							new java.awt.Color((int) (redLess + (index * redCoeff)),
									(int) (greenLess + (index * greenCoeff)), (int) (blueLess + (index * blueCoeff)))
											.getRGB());

					System.out.println((int) (redLess + (index * redCoeff)) + " "
							+ (int) (greenLess + (index * greenCoeff)) + " " + (int) (blueLess + (index * blueCoeff)));

				}

				ImageIO.write(image, "png", new File("res/image.png"));

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Enter text!");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Select other color!");
		}
	}
}
