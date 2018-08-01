package edu.iu.dsc.spidal.fileprocessor;

import edu.iu.dsc.spidal.constant.Constant;

import java.io.*;
import java.nio.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class BinaryFileProcess {
    private static final Logger LOG = Logger.getLogger(BinaryFileProcess.class.getName());

    private static String DISTANCE_MATRIX_FILE = Constant.DUMMY_DISTANCE_MATRIX;
    private static ByteOrder endianness = ByteOrder.BIG_ENDIAN;
    private static int dataTypeSize = Short.BYTES;

    public static void main(String[] args) {
        String ens = "little";
        endianness =  ens.equals("big") ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;

        try {
            readBinFile(endianness);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readBinFile(ByteOrder endianness) throws IOException{

        FileChannel fc = (FileChannel) Files
                .newByteChannel(Paths.get(DISTANCE_MATRIX_FILE), StandardOpenOption.READ);

        ByteBuffer byteBuffer = ByteBuffer.allocate((int)fc.size());

        if(endianness.equals(ByteOrder.BIG_ENDIAN)){
            byteBuffer.order(ByteOrder.BIG_ENDIAN);
        }else{
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        }


        Buffer buffer = null;

        System.out.println("FC Size : " + fc.size());

        while(fc.read(byteBuffer) != -1) {
            System.out.println("String read : " + ((ByteBuffer) (byteBuffer.flip())).asShortBuffer().get(0));
            byteBuffer.clear();

        }


        /*short [] arr = (short[]) buffer.array();
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }

        short[] shortArray = new short[(int)fc.size()/2];
        ((ShortBuffer)buffer).get(shortArray);

        byteBuffer.clear();
        byteBuffer = endianness.equals(ByteOrder.BIG_ENDIAN) ? byteBuffer.order(ByteOrder.LITTLE_ENDIAN) :
                byteBuffer.order(ByteOrder.BIG_ENDIAN);
        ShortBuffer shortOutputBuffer = byteBuffer.asShortBuffer();

        shortOutputBuffer.put(shortArray);
        System.out.println(shortOutputBuffer.toString());*/


    }
}
