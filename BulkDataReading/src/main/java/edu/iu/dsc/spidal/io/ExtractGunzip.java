package edu.iu.dsc.spidal.io;

import org.apache.commons.cli.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ExtractGunzip {

    private static String inputGzipFile = "";
    private static String outputFile = "";
    private static int offset = 0;
    private static int length = 0;
    private static boolean isByteRange = false;
    private static boolean isBromberg = false;

    public static void main(String[] args) throws ParseException {
        Options options = genOpts();
        parser(options, args);

        //compress(file, gzipFile);

        //decompress(gzipFile, newFile);

        if (isByteRange) {
            decompressByByteRange(inputGzipFile, outputFile, offset, length);
        }

        if (isBromberg) {
            decompressBromberg(inputGzipFile, outputFile);
        }
    }

    private static void decompress(String gzipFile, String newFile) {
        try {
            FileInputStream fis = new FileInputStream(gzipFile);
            GZIPInputStream gis = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int len;
            while((len = gis.read(buffer)) != -1){
                fos.write(buffer, 0, len);
            }
            //close resources
            fos.close();
            gis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void decompressByByteRange(String gzipFile, String newFile, int offset, int length) {
        try {
            FileInputStream fis = new FileInputStream(gzipFile);
            GZIPInputStream gis = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(newFile);
            int bufSize = 1024;
            byte[] buffer = new byte[1024];
            int totalLength = 0;
            while(totalLength != (length - offset)){
                int len = gis.read(buffer, offset, bufSize);
                fos.write(buffer, 0, len);
                totalLength += bufSize;
            }

            //close resources
            fos.close();
            gis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * >>> 636301201408/1024/1024/1024
     * 592
     * >>> 636301201408/1500000*200000
     * 84840000000
     * >>> 84840000000/1024
     * 82851562
     * TODO change the range of the file in a randomized way
     * */

    private static void decompressBromberg(String gzipFile, String newFile) {
        try {
            FileInputStream fis = new FileInputStream(gzipFile);
            GZIPInputStream gis = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(newFile);
            int bufSize = 1024;
            byte[] buffer = new byte[bufSize];
            int len;
            int counter = 0;
            while((len = gis.read(buffer)) != -1 && counter != 82851562){
                fos.write(buffer, 0, len);
                counter++;
            }

            //close resources
            fos.close();
            gis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void compress(String file, String gzipFile) {
        try {
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(gzipFile);
            GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
            byte[] buffer = new byte[1024];
            int len;
            while((len=fis.read(buffer)) != -1){
                gzipOS.write(buffer, 0, len);
            }
            //close resources
            gzipOS.close();
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static Options genOpts() {
        final Option inputGzipFileOpt = Option.builder("inputGzipFile")
                .required()
                .hasArg()
                .type(String.class)
                .desc("Input Gunzip File : distance-matrix.gz")
                .build();
        final Option outputFileOpt = Option.builder("outputFile")
                .required()
                .hasArg()
                .type(String.class)
                .desc("Extracted Output File : output-sample.txt")
                .build();
        final Option offsetOpt = Option.builder("offset")
                .required(false)
                .hasArg()
                .type(Integer.class)
                .desc("Starting position of the file, ex: 0 (in byte level)")
                .build();
        final Option lengthOpt = Option.builder("length")
                .required(false)
                .hasArg()
                .type(Integer.class)
                .desc("Chunk Size to be toText, ex : 2400 (in byte level)")
                .build();
        final Option byteRangeStateOpt = Option.builder("isByteRange")
                .required(true)
                .hasArg()
                .type(Boolean.class)
                .desc("Byte Range Configuration, ex : true or false")
                .build();
        final Option brombergStateOpt = Option.builder("isBromberg")
                .required(true)
                .hasArg()
                .type(Boolean.class)
                .desc("Bromberg Configuration, ex : true or false")
                .build();

        final Options options = new Options();
        options.addOption(inputGzipFileOpt);
        options.addOption(outputFileOpt);
        options.addOption(offsetOpt);
        options.addOption(lengthOpt);
        options.addOption(byteRangeStateOpt);
        options.addOption(brombergStateOpt);

        return options;
    }

    public static void parser(Options options, String[] cmds) throws ParseException {
        final CommandLineParser cmdLineParser = new DefaultParser();
        CommandLine commandLine = null;
        commandLine = cmdLineParser.parse(options, cmds);
        inputGzipFile = commandLine.getOptionValue("inputGzipFile");
        outputFile = commandLine.getOptionValue("outputFile");
        if (commandLine.getOptionValue("offset") != null) {
            offset = Integer.parseInt(commandLine.getOptionValue("offset"));
        }

        if (commandLine.getOptionValue("length") != null) {
            length = Integer.parseInt(commandLine.getOptionValue("length"));
        }

        if (commandLine.getOptionValue("isBromberg") != null) {
            isBromberg = Boolean.parseBoolean(commandLine.getOptionValue("isBromberg"));
        }

        if (commandLine.getOptionValue("isByteRange") != null) {
            isByteRange = Boolean.parseBoolean(commandLine.getOptionValue("isByteRange"));
        }

        System.out.println(inputGzipFile + ", " + outputFile +", " + offset + ", " + length + ", " + isBromberg
                + ", " + isByteRange);
    }
}
