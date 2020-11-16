import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI implements ActionListener {
	JLabel newImagePromptText;
	static JTextField newImageFilenamePrompt;
	JLabel oldImagePromptText;
	JLabel oldImagePromptHintText;
	static JTextField oldImageFilenamePrompt;
	static JLabel successText;
	
	public GUI() {
		JFrame frame = new JFrame("Lock Screen Image Changer");
		frame.setSize(400,245);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.add(panel);
		
		newImagePromptText = new JLabel("What's the name of the file you want to set?");
		newImagePromptText.setBounds(10, 10, 270, 25);
		panel.add(newImagePromptText);
		
		newImageFilenamePrompt = new JTextField();
		newImageFilenamePrompt.setBounds(10, 35, 270, 25);
		panel.add(newImageFilenamePrompt);
		
		oldImagePromptText = new JLabel("What's the name of the default image?");
		oldImagePromptText.setBounds(10, 70, 270, 25);
		panel.add(oldImagePromptText);
		
		oldImagePromptHintText = new JLabel("Usually \"1.jpg\", but sometimes it's different.");
		oldImagePromptHintText.setBounds(10, 95, 270, 25);
		panel.add(oldImagePromptHintText);
		
		oldImageFilenamePrompt = new JTextField();
		oldImageFilenamePrompt.setBounds(10, 130, 270, 25);
		panel.add(oldImageFilenamePrompt);
		
		JButton button = new JButton("Apply");
		button.addActionListener(this);
		button.setBounds(285, 10, 95, 190);
		panel.add(button);
		
		successText = new JLabel();
		successText.setBounds(10, 165, 270, 25);
		panel.add(successText);
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new GUI();
	}
	
	public void actionPerformed(ActionEvent e) {
		String newFilename = newImageFilenamePrompt.getText();
		String oldFilename = oldImageFilenamePrompt.getText();
		
		boolean newFilenameCorrectBoolean = newFilename.endsWith(".jpg") || newFilename.endsWith(".jpeg") || newFilename.endsWith(".png") || newFilename.endsWith(".bmp");
		String newFilenameCorrect = String.valueOf(newFilenameCorrectBoolean);
		if(newFilenameCorrect == "true") {
			System.out.print("The new image is a JPEG, PNG, or Bitmap! + ");
		} else {
			System.out.print("The new image is the incorrect file type. Please try again with a JPEG, PNG, or Bitmap. + ");
		}
		
		boolean oldFilenameCorrectBoolean = oldFilename.endsWith(".jpg") || oldFilename.endsWith(".jpeg") || oldFilename.endsWith(".png") || newFilename.endsWith(".bmp");
		String oldFilenameCorrect = String.valueOf(oldFilenameCorrectBoolean);
		if(oldFilenameCorrect == "true") {
			System.out.println("The old image is a JPEG, PNG, or Bitmap!");
		} else {
			System.out.println("The old image is the incorrect file type. Please try again with a JPEG, PNG, or Bitmap.");
		}
		
		if(newFilenameCorrect == "true" && oldFilenameCorrect == "true") {
			createTempBatch();
			writeCommandToBatch();
			try {
				Runtime.getRuntime().exec("cmd /c start \"\" run.bat");
				successText.setText("File run successfully. You can close the app.");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			System.out.println("At least one of the files specified are of incorrect syntax. Try again with a .jpg, .png, or .bmp file.");
			successText.setText("Invalid filetype for one or more files.");
		}
	}
		
	private static void createTempBatch() {	// This is code ripped straight from my batch file generator (Toydotgame/batFileGenerator)
		try {
			File outputFile = new File("run.bat");
			if(outputFile.createNewFile()) {
				successText.setText("File successfully created.");
			} else {
				successText.setText("File already exists.");
			}
		} catch (IOException e) {
			successText.setText("Java could not create the file.");
			e.printStackTrace();
		}
	}
	
	private static void writeCommandToBatch() { // This method is also from Toydotgame/batFileGenerator
		String newFilename = newImageFilenamePrompt.getText();
		String oldFilename = oldImageFilenamePrompt.getText();
		
		try {
			FileWriter myWriter = new FileWriter("run.bat");
			
			myWriter.write("@echo off\n");
			myWriter.write(":: BatchGotAdmin\n");
			myWriter.write("REM --> Check for permissions\n");
			myWriter.write(">nul 2>&1 \"%SYSTEMROOT%\\system32\\cacls.exe\" \"%SYSTEMROOT%\\system32\\config\\system\"\n");
			myWriter.write("REM --> If error flag set, we do not have admin.\n");
			myWriter.write("if '%errorlevel%' NEQ '0' (\n");
			myWriter.write("	echo Requesting administrative privileges...\n");
			myWriter.write("	goto UACPrompt\n");
			myWriter.write(") else (\n");
			myWriter.write("	goto gotAdmin\n");
			myWriter.write(")\n");
			myWriter.write(":UACPrompt\n");
			myWriter.write("echo Set UAC = CreateObject^(\"Shell.Application\"^) > \"%temp%\\getadmin.vbs\"\n");
			myWriter.write("echo UAC.ShellExecute \"%~s0\", \"\", \"\", \"runas\", 1 >> \"%temp%\\getadmin.vbs\"\n");
			myWriter.write("\"%temp%\\getadmin.vbs\"\n");
			myWriter.write("exit /B\n");
			myWriter.write(":gotAdmin\n");
			myWriter.write("if exist \"%temp%\\getadmin.vbs\" (\n");
			myWriter.write("	del \"%temp%\\getadmin.vbs\"\n");
			myWriter.write(")\n");
			myWriter.write("pushd \"%CD%\"\n");
			myWriter.write("CD /D \"%~dp0\"\n");
			myWriter.write(":: BatchGotAdmin (Run as Admin code ends)\n");
			myWriter.write("\n");
			myWriter.write("xcopy " + newFilename + " \"C:\\Program Files\\\" /y\n");
			myWriter.write("rename \"C:\\Program Files\\" + newFilename + "\" " + oldFilename + "\n");
			myWriter.write("xcopy \"C:\\Program Files\\" + oldFilename + "\" \"C:\\Program Files\\images\\\" /y\n");
			myWriter.write("del \"C:\\Program Files\\" + oldFilename + "\"\n");
			myWriter.write("(goto) 2>nul & del \"%~f0\"");
			
			myWriter.close();
		} catch (IOException e) {
			successText.setText("Could not write to output file.");
			e.printStackTrace();
		}
	}
}
