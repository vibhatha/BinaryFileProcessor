package edu.iu.dsc.spidal.poc;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.channels.FileChannel;

public class SampleBin {
    private static String bigExcelFile = "/home/vibhatha/github/dsc-spidal-forks/applications/fungi-gene-sequence/data/distance-matrix.bin";
    public static void main(String[] args) throws Exception {
        File aFile = new File(bigExcelFile);
        FileInputStream inFile = new FileInputStream(aFile);
        FileChannel inChannel = inFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate((int)inChannel.size());
        buf.order(ByteOrder.LITTLE_ENDIAN);

        double[] primes = new double[(int)inChannel.size()/8];
        int count = 0;
        while (inChannel.read(buf) != -1) {
           DoubleBuffer doubleBuffer =  ((ByteBuffer) (buf.flip())).asDoubleBuffer().get(primes);
           double d = doubleBuffer.get(2);
           System.out.println(d);
        }
        buf.clear();
        inFile.close();
    }
}
