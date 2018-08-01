package edu.iu.dsc.spidal.poc;

import java.io.*;
import java.nio.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ReadFromMemoryMaps {
    private static int position = 0;
    private static int size = 2400*2;
    private static String inputFile = "/home/vibhatha/github/dsc-spidal-forks/applications/fungi-gene-sequence/data/distance-matrix.bin";
    private static String outputFile = "/home/vibhatha/Sandbox/bio/distance_matrices_csv/distance_matrix_2400.txt";
    private static String outBinFile = "/home/vibhatha/Sandbox/bio/distance_matrices_csv/distance_matrix_2400.bin";
    private static String outBinSampleFile = "/home/vibhatha/Sandbox/bio/distance_matrices_csv/sample_distance_matrix_2400.bin";

    public static void main(String[] args) throws Exception {
        //Create file object
        File file = new File(inputFile);
        //Get file channel in readonly mode
        FileChannel fileChannel = new RandomAccessFile(file, "r").getChannel();
        long bufSize = 4096;
        long fileSize = fileChannel.size();
        //readbin(inputFile, outputFile, ByteOrder.LITTLE_ENDIAN);
        readbinRandomSample(inputFile, outBinSampleFile, position, size, ByteOrder.LITTLE_ENDIAN);

    }

/**
 * Can be used to toText files in non binary format giving a starting and end point of the file byte stream.
 * **/
    public static void readPortion(long startPosition, long maxSize, long bufSize, long fileSize, FileChannel fileChannel) throws IOException {

        MappedByteBuffer buffer = null;
        long remSize = fileSize;
        long maxSizeCount = 0;
        while (remSize != 0 && maxSizeCount != maxSize) {
            if (remSize > bufSize) {
                if (remSize == fileSize) {
                    startPosition = startPosition;
                } else {
                    startPosition += bufSize;
                }

            } else {
                startPosition = fileSize - remSize;
                bufSize = remSize;
            }
            remSize = remSize - bufSize;

            buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startPosition, bufSize);
            buffer.order(ByteOrder.BIG_ENDIAN);
            for (int i = 0; i < buffer.limit(); i++) {
                System.out.print((char) buffer.get()); //Print the content of file
            }
            maxSizeCount += bufSize;
        }
    }

    /**
     * can be used to toText non binary files fully. Not recommended for larger files.
     * **/
    public static void fullread(long fileSize, long bufSize, FileChannel fileChannel) throws IOException {
        MappedByteBuffer buffer = null;
        long startPosition = 0;
        long remSize = fileSize;
        while (remSize != 0) {

            if (remSize > bufSize) {
                if (remSize == fileSize) {
                    startPosition = 0;
                } else {
                    startPosition += bufSize;
                }

            } else {
                startPosition = fileSize - remSize;
                bufSize = remSize;
            }
            remSize = remSize - bufSize;
            /*System.out.println("FIle Size : " + fileSize + ", Start : " + startPosition + ", buffer : "
                    + bufSize + ", remSize : " + remSize);*/
            buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startPosition, bufSize);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            for (int i = 0; i < buffer.limit(); i++) {
                System.out.print((char) buffer.get()); //Print the content of file
            }
        }
    }

    /**
     * Reading Binary Files in Little Endian or Big Endian formats.
     * ***/


    public static void readbin(String inputFile, String outputFile,  ByteOrder endianness) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
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

        for (int i = 0; i < shortArray.length; i++) {
            if(i <10){
                System.out.print(" " + (double)shortArray[i]/Short.MAX_VALUE);
            }

            String line = "";
            if(i % numOfPoints != 0) {
                 line += " " + (double)shortArray[i]/Short.MAX_VALUE;
            } else {
                line+="\n";
            }
            writer.write(line);
        }
        writer.close();
        fc.close();

        //genBinFile(byteBuffer, numOfPoints, shortArray ,outBinFile);
    }

    /**
     * reading random samples from a bin file
     * */

    public static void readbinRandomSample(String inputFile, String outputFile, long position, long size, ByteOrder endianness) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
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

        for (int i = 0; i < shortArray.length; i++) {
            if(i <10){
                System.out.print(" " + (double)shortArray[i]/Short.MAX_VALUE);
            }
            String line = "";
            if(i % numOfPoints != 0) {
                line += " " + (double)shortArray[i]/Short.MAX_VALUE;
            } else {
                line+="\n";
            }
            writer.write(line);
        }
        writer.close();
        fc.close();
    }


    public static void genBinFile(ByteBuffer byteBuffer, int numOfPoints, short [] input, String outBinFile ) throws IOException {
        //byteBuffer.clear();
        ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
        shortBuffer.put(input);
        FileChannel out = new FileOutputStream(outBinFile).getChannel();
        out.write(byteBuffer);
        out.close();
    }
}

