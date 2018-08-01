install:
	mvn install

clean:
	mvn clean

package:
	mvn package

runBinFileProcessor:
	java -cp target/BulkDataReading-1.0-SNAPSHOT-jar-with-dependencies.jar  edu.iu.dsc.spidal.io.BinFileProcessor -inputBinFile input.txt -outputBinFile output.txt -position 0 -chunksize 10 -randomSample true -endianness LITTLE_ENDIAN

runSample:
	java -cp target/BulkDataReading-1.0-SNAPSHOT-jar-with-dependencies.jar  edu.iu.dsc.spidal.io.BinFileProcessor -inputBinFile /home/vibhatha/github/dsc-spidal-forks/applications/fungi-gene-sequence/data/distance-matrix.bin -outputBinFile /home/vibhatha/Sandbox/bio/distance_matrices_csv/sample_0_2400_distance_matrix_2400.bin  -position 0 -chunksize 4800 -randomSample true -endianness LITTLE_ENDIAN

runText:
	java -cp target/BulkDataReading-1.0-SNAPSHOT-jar-with-dependencies.jar  edu.iu.dsc.spidal.io.BinFileProcessor -outputBinFile /home/vibhatha/Sandbox/bio/distance_matrices_csv/sample_0_2400_distance_matrix_2400.txt -inputBinFile /home/vibhatha/Sandbox/bio/distance_matrices_csv/sample_0_2400_distance_matrix_2400.bin  -position 0 -chunksize 4800 -randomSample false -endianness LITLLE_ENDIAN

runFullText:
	java -cp target/BulkDataReading-1.0-SNAPSHOT-jar-with-dependencies.jar  edu.iu.dsc.spidal.io.BinFileProcessor -outputBinFile /home/vibhatha/Sandbox/bio/distance_matrices_csv/full_0_2400_distance_matrix_2400.txt -inputBinFile /home/vibhatha/github/dsc-spidal-forks/applications/fungi-gene-sequence/data/distance-matrix.bin  -position 0 -chunksize 4800 -randomSample false -endianness LITTLE_ENDIAN
