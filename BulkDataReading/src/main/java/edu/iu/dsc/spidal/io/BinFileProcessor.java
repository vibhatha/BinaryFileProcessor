package edu.iu.dsc.spidal.io;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.ByteOrder;

public class BinFileProcessor {
    private static String inputBinFile = "";
    private static String outputBinFile = "";
    private static int position = 0;
    private static int chunkSize = 1024;
    private static boolean isRandomSample = false;
    private static ByteOrder endianness = ByteOrder.LITTLE_ENDIAN;

    public static void main(String[] args) throws ParseException, IOException {
        Options options = genOpts();
        parser(options, args);

        if(isRandomSample == true) {
            ReadRandomSample.toBin(inputBinFile, outputBinFile, position, chunkSize, endianness);
        } else {
            ReadFullSample.toText(inputBinFile, outputBinFile, endianness);
        }
    }

    public static Options genOpts() {
        final Option inputBinFileOpt = Option.builder("inputBinFile")
                .required()
                .hasArg()
                .type(String.class)
                .desc("Input Binary File : distance-matrix.bin")
                .build();
        final Option outputBinFileOpt = Option.builder("outputBinFile")
                .required()
                .hasArg()
                .type(String.class)
                .desc("Output Binary File : output-sample.bin")
                .build();
        final Option positionOpt = Option.builder("position")
                .required(false)
                .hasArg()
                .type(Integer.class)
                .desc("Starting position of the file, ex: 0 (in byte level)")
                .build();
        final Option chunkSizeOpt = Option.builder("chunksize")
                .required(false)
                .hasArg()
                .type(Integer.class)
                .desc("Chunk Size to be toText, ex : 2400 (in byte level)")
                .build();
        final Option randomSampleOpt = Option.builder("randomSample")
                .required(false)
                .hasArg()
                .type(Boolean.class)
                .desc("Random Sampling is True or False, ex : true (boolean)")
                .build();
        final Option endiannessOpt = Option.builder("endianness")
                .required()
                .hasArg()
                .type(String.class)
                .desc("Endianness of data, ex: LITTLE_ENDIAN (ByteOrder)")
                .build();

        final Options options = new Options();
        options.addOption(inputBinFileOpt);
        options.addOption(outputBinFileOpt);
        options.addOption(positionOpt);
        options.addOption(chunkSizeOpt);
        options.addOption(randomSampleOpt);
        options.addOption(endiannessOpt);

        return options;
    }

    public static void parser(Options options, String[] cmds) throws ParseException {
        final CommandLineParser cmdLineParser = new DefaultParser();
        CommandLine commandLine = null;
        commandLine = cmdLineParser.parse(options, cmds);
        inputBinFile = commandLine.getOptionValue("inputBinFile");
        outputBinFile = commandLine.getOptionValue("outputBinFile");
        if (commandLine.getOptionValue("position") != null) {
            position = Integer.parseInt(commandLine.getOptionValue("position"));
        }

        if (commandLine.getOptionValue("chunksize") != null) {
            chunkSize = Integer.parseInt(commandLine.getOptionValue("chunksize"));
        }

        if (commandLine.getOptionValue("randomSample") != null) {
            isRandomSample = Boolean.parseBoolean(commandLine.getOptionValue("randomSample"));
        }

        if (commandLine.getOptionValue("endianness") != null) {
            endianness = commandLine.getOptionValue("endianness").equals("BIG_ENDIAN") ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
        }

        System.out.println(inputBinFile + ", " + outputBinFile +", " + position + ", " + chunkSize + ", " + isRandomSample + ", " + endianness);
    }
}
