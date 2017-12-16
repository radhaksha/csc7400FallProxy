/**
 * Class: Object-Oriented Design and Analysis
 * Professor: Orlando Montalvo
 * Assignment: HW 10
 * 
 * @author Radha K Sharma
 * @1379877
 * 
 * Date: 2017-12-15
 */


package edu.fitchburgstate.csc7400.f2017fall.proxy;

import java.io.*;
import java.util.ArrayList;

/**
 * Simple file reader that turns a file into a string
 */
public class SlowFileStringifier implements FileStringifier {

    /**
     * Create a file stringifier with a file name
     * @param filename file name of file
     */
    public SlowFileStringifier(String filename) {
        this.fileName = filename;
    }

    /**
     * Writes out the file contents to the print writer
     * @param out the output print writer
     */
    public void display(PrintWriter out) {
    	out.println(this.stringify());
    	out.flush();
    }
    
    /**
     * Returns a string with all the files contents
     */
    public String stringify() {
        FileReader f;
		try {
			f = new FileReader(this.fileName);
	        BufferedReader bf = new BufferedReader(f);
	
	        ArrayList<String> lines = new ArrayList<String>();
	        String line = null;
	        while ((line = bf.readLine()) != null) {
	            Thread.sleep(2000);
	            lines.add(line);
	        }
	        bf.close();
	        return String.join("\n", lines);
        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("File "+this.fileName+" was not found.");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Problem reading file "+this.fileName+".");
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.err.println("File reading interrupted: "+this.fileName);
		}
		return "";
    }

    private final String fileName;
}
