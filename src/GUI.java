import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI implements ActionListener {
	JLabel newImagePromptText;
	JTextField newImageFilenamePrompt;
	JLabel oldImagePromptText;
	JLabel oldImagePromptHintText;
	JTextField oldImageFilenamePrompt;
	
	public GUI() {
		JFrame frame = new JFrame("ABPC Lock Screen Image Changer");
		frame.setSize(400,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.add(panel);
		
		newImagePromptText = new JLabel("What's the name of the file you want to set?");
		newImagePromptText.setBounds(10, 10, 275, 25);
		panel.add(newImagePromptText);
		
		newImageFilenamePrompt = new JTextField();
		newImageFilenamePrompt.setBounds(10, 35, 275, 25);
		panel.add(newImageFilenamePrompt);
		
		oldImagePromptText = new JLabel("What's the name of the default image?");
		oldImagePromptText.setBounds(10, 70, 275, 25);
		panel.add(oldImagePromptText);
		
		oldImagePromptHintText = new JLabel("Usually \"1.jpg\", but sometimes it's different.");
		oldImagePromptHintText.setBounds(10, 95, 275, 25);
		panel.add(oldImagePromptHintText);
		
		oldImageFilenamePrompt = new JTextField();
		oldImageFilenamePrompt.setBounds(10, 130, 275, 25);
		panel.add(oldImageFilenamePrompt);
		
		JButton button = new JButton("Apply");
		button.addActionListener(this);
		button.setBounds(285, 10, 95, 145);
		panel.add(button);
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new GUI();
	}
	
	public void actionPerformed(ActionEvent e) {
		String newFilename = newImageFilenamePrompt.getText();
		String oldFilename = oldImageFilenamePrompt.getText();
		
		boolean newFilenameCorrectBoolean = newFilename.endsWith(".jpg");
		String newFilenameCorrect = String.valueOf(newFilenameCorrectBoolean);
		if(newFilenameCorrect == "true") {
			System.out.println("The new image is a JPEG!");
		} else {
			System.out.println("The new image is the incorrect file type. Please try again with a JPEG.");
		}
		
		boolean oldFilenameCorrectBoolean = oldFilename.endsWith(".jpg");
		String oldFilenameCorrect = String.valueOf(oldFilenameCorrectBoolean);
		if(oldFilenameCorrect == "true") {
			System.out.println("The old image is a JPEG!");
		} else {
			System.out.println("The old image is the incorrect file type. Please try again with a JPEG.");
		}
		
		/* Write this command to the batch file you create:
		 * rename 2.jpg 1.jpg && xcopy 1.jpg test\1.jpg /y
		 * Where 2.jpg is the new image, and 1.jpg is the default one to be overwritten.
		 */
	}
}
