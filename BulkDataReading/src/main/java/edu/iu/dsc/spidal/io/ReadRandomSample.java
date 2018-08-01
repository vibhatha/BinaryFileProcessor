package edu.iu.dsc.spidal.io;

import java.io.*;
import java.nio.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadRandomSample {

    /*****
     * Reads the full sample and write the subset into a bin file.
     * *****/

    public static void toBin(String inputFile, String outputFile, long position, long size, ByteOrder endianness) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        FileChannel out = new FileOutputStream(outputFile).getChannel();
        Path path = Paths.get(inputFile);
        FileChannel fc = FileChannel.open(path);
        fc.position(position);
        int numOfPoints = (int)Math.sqrt(fc.size()/2);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect((int)size);
        byteBuffer.order(endianness);
        fc.read(byteBuffer, position);
        byteBuffer.flip();

        Buffer buffer = null;
        buffer = byteBuffer.asShortBuffer();
        short[] shortArray = new short[(int)size/2];
        ((ShortBuffer)buffer).get(shortArray);

       /* for (int i = 0; i < shortArray.length; i++) {
            if(i <10){
                System.out.print(" " + (double)shortArray[i]/Short.MAX_VALUE);
            }
            String line = "";
            if(i % numOfPoints != 0) {
                line += " " + (double)shortArray[i]/Short.MAX_VALUE; writer.close();
            } else {
                line+="\n";
            }
            writer.write(line);
        }*/

       ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
       shortBuffer.put(shortArray);
       out.write(byteBuffer);
       out.close();
       fc.close();
    }
}
