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

    public static void main(String[] args) throws ParseException {
        Options options = genOpts();
        parser(options, args);

        //compress(file, gzipFile);

        //decompress(gzipFile, newFile);

        decompressByByteRange(inputGzipFile, outputFile, offset, length);

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
            byte[] buffer = new byte[length-offset];
            int len = gis.read(buffer, offset, length);
            fos.write(buffer, 0, len);
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
                .required(true)
                .hasArg()
                .type(Integer.class)
                .desc("Starting position of the file, ex: 0 (in byte level)")
                .build();
        final Option lengthOpt = Option.builder("length")
                .required(true)
                .hasArg()
                .type(Integer.class)
                .desc("Chunk Size to be toText, ex : 2400 (in byte level)")
                .build();

        final Options options = new Options();
        options.addOption(inputGzipFileOpt);
        options.addOption(outputFileOpt);
        options.addOption(offsetOpt);
        options.addOption(lengthOpt);

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

        System.out.println(inputGzipFile + ", " + outputFile +", " + offset + ", " + length);
    }
}
