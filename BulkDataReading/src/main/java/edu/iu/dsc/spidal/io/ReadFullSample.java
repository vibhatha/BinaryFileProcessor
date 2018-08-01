package edu.iu.dsc.spidal.io;

import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel;

public class ReadFullSample {

    /**
     * Reading Binary Files in Little Endian or Big Endian formats.
     * ***/
    public static void toText(String inputFile, String outputFile, ByteOrder endianness) throws IOException {

        FileChannel out = new FileOutputStream(outputFile).getChannel();
        File aFile = new File(inputFile);
        FileInputStream inFile = null;
        inFile = new FileInputStream(aFile);
        FileChannel fc = inFile.getChannel();
        long fileSize = fc.size();
        int numOfPoints = (int)Math.sqrt(fileSize/2);
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) fileSize);
        byteBuffer.order(endianness);
        fc.read(byteBuffer);
        byteBuffer.flip();

        Buffer buffer = null;
        buffer = byteBuffer.asShortBuffer();
        short[] shortArray = new short[(int)fc.size()/2];
        ((ShortBuffer)buffer).get(shortArray);
        System.out.println("Length of Array : " + shortArray.length);
        for (int i = 0; i < shortArray.length; i++) {
            if(i <10){
                System.out.print(" " + (double)shortArray[i]/Short.MAX_VALUE);
            }
            String line = "";
            if(i == 0){
                line += (double)shortArray[0]/Short.MAX_VALUE + " ";
            }

            if(i % numOfPoints != 0) {
                line += (double)shortArray[i]/Short.MAX_VALUE + " ";
            } else if(i>0) {
                line+="\n";
            }
            //writer.write(line);
        }
        out.write(byteBuffer);
        fc.close();
        //genBinFile(byteBuffer, numOfPoints, shortArray ,outBinFile);
    }
}
