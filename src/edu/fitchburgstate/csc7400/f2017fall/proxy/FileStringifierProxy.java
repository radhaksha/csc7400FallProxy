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

import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;

/**
 * Proxy class for SlowFileStringifier class
 * Implements FileStringifier interface
 * @author rsharma
 *
 */
public class FileStringifierProxy implements FileStringifier {
	
	private final String fileName;
	private final CountDownLatch startSignal;
	private final CountDownLatch doneSignal;

	
	
	/**
	 * Create a fileStringifier proxy with below parameters
	 * @param filename
	 * @param startSignal
	 * @param doneSignal
	 */
	public FileStringifierProxy(String filename, CountDownLatch startSignal, CountDownLatch doneSignal) {
		this.fileName = filename;
		this.startSignal = startSignal;
		this.doneSignal = doneSignal;
	}

	
	/**
	 * Writes out the file contents to the print writer
	 * @param out the output print writer
	 */
	public void display(final PrintWriter out) {
		System.out.println("Reading " + fileName);
		final SlowFileStringifier slowfilestringifier = new SlowFileStringifier(fileName);
		try {
			startSignal.await(); // Wait for start signal from main thread
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		slowfilestringifier.display(out); // Display file content
		doneSignal.countDown(); // Send done signal to main thread
	}

	
	 /**
     * Returns a string with all the file contents
     */
	public String stringify() {
		// TODO Auto-generated method stub
		return null;
	}

}
