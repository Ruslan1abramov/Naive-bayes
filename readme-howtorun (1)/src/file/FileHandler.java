package file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

public class FileHandler {
	File myFile;

	public FileHandler(String dataBasePath) {
		this.myFile = new File(dataBasePath);
	}

	/*** Reading the file **/
	protected String readFile() {
		try {

			return new String(Files.readAllBytes(myFile.toPath()));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*** Saving the new data **/
	protected void addData(String dataToAdd) {
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(myFile, true)))) {
			out.println(dataToAdd);
			out.close();
		} catch (IOException e) {

			e.printStackTrace();

		}
	}
}
