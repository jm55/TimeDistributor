package files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileHandler {
	public String[] readFile() {
		ArrayList<String> raw_input = new ArrayList<String>();
		JFileChooser jfc = new JFileChooser();
		boolean valid = false;
		while(!valid) {
			if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File f = jfc.getSelectedFile();
				String extension = f.getName();
				
				if(extension.contains("."))
					extension = extension.substring(extension.lastIndexOf('.'));
				else
					extension = "";
				
				if(!extension.equalsIgnoreCase(".csv"))
					JOptionPane.showMessageDialog(null, "File selected not allowed, please select .csv files containing names only.");
				else {
					valid = true;
					Scanner fscan;
					try {
						fscan = new Scanner(f);
						while(fscan.hasNext())
							raw_input.add(fscan.nextLine());
						String[] input_arr = new String[raw_input.size()];
						input_arr = raw_input.toArray(input_arr);
						fscan.close();
						return input_arr;
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(null, "Error reading file...");
					}
				}
			}else
				return null;
		}
		return null;
	}
	
	public void writeFile(String[][] input) {
		JFileChooser jfc = new JFileChooser();
		if(jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File f = jfc.getSelectedFile();
			String filename = f.getName();
			
			f = new File(f.getAbsolutePath() + ".csv");
			
			System.out.println("Saving as: " + f.getAbsolutePath());
			
			try {
				if(!f.exists())
					f.createNewFile();
				FileWriter fwriter = new FileWriter(f.getAbsolutePath());
				for(String[] i: input)
					fwriter.write(i[0] + ", " + i[1] + ","  + i[2] + ","  + i[3] + "\n");
				fwriter.close();
				
				JOptionPane.showMessageDialog(null, "Please do view/open the newly save file in MS Excel and save as a spreadsheet when needed.");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error creating new file!");
			}
		}
	}
}