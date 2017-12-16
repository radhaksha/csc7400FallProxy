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

import java.io.File;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;

/**
 * Show the contents of all the files in a specific directory
 */
public class ShowFileContents {

	/**
     * Accepts a file directory and then prints out the contents of
     * all the files in that directory
     * @param args single arg with directory path
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ShowFileContents <directory>");
        }

        String dirname = args[0];

        File dir = new File(dirname);
        if (!dir.exists()) {
            System.err.println(dirname + " does not exist");
            return;
        }
        if (!dir.isDirectory()) {
            System.err.println(dirname + " is not a directory");
            return;
        }

        File[] files = dir.listFiles();
        int noOfFilesToProcess = 0;
        for (final File file : files) {
            if (!file.isDirectory()) {
                noOfFilesToProcess++;
            }
        }
        
        //CountDownLatch is a type of synchronizer which allows one Thread to wait for one or more  Threads to be completed    
        final CountDownLatch startSignal = new CountDownLatch(1);  //thread count down from 1 to 0
        final CountDownLatch doneSignal = new CountDownLatch(noOfFilesToProcess);
        final PrintWriter outWriter = new PrintWriter(System.out);
        for (final File file : files) {
            if (file.isDirectory())
                continue;
            Thread thread = new Thread() {
                public void run() {
                    FileStringifier fd = new FileStringifierProxy(
                            file.getPath(), startSignal, doneSignal);
                    fd.display(outWriter);
                }
            };
            thread.start();
        }
        startSignal.countDown(); // let all threads proceed
        try {
            doneSignal.await(); // main thread waits until thread count reaches zero.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}



