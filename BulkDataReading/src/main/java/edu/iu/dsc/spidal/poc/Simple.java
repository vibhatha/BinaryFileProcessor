package edu.iu.dsc.spidal.poc;

public class Simple {
    public static void main(String[] args) {
        int size = 10000;
        int buffer = 24;
        int remSize = size;
        int start = 0;
        while(remSize != 0) {

            if(remSize > buffer) {
                if(remSize == size){
                    start = 0;
                } else{
                    start += buffer;
                }

            } else {
                start = size - remSize;
                buffer = remSize;
            }
            remSize = remSize - buffer;
            System.out.println("FIle Size : " + size +", Start : " + start +  ", buffer : "
                    + buffer + ", remSize : " + remSize);

        }
    }
}
