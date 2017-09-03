# TDA.JAVA
Console application that can be used to generate persistence diagrams based on points cloud by means of TDA.  
Program generates diagrams from collection of points in JSON and writes results files to the specified folder.  
Program produces persistence diagrams (png), intervals (txt) and diagrams entropy values (json array in separate file).   
Program execution for dimensions larger than 2 might be very memory consuming.   
Program is based on Javaplex library for TDA related computing (https://github.com/appliedtopology/javaplex).  

## Building from sources
TDA.JAVA is maven based so building a package is as simple as:

*mvn clean compile package assembly:single -e*

## Executing / Input

java -jar tdathesis-1.0-SNAPSHOT.jar  <input_file_name> <max_dimension> <max_filtration> <use_landmarks> <max_landmarks> <output_folder>

e.g. *java -jar C:\tda.java\tdathesis-1.0-SNAPSHOT.jar  "C:\program_data\points_cloud.json" 2 118 True 200 "C:\program_data_output"*

* *<input_file_name>* - path to JSON file with array of coordinates (points).
* *<max_dimension>* - integer value setting max value for dimensions to compute
* *<max_filtration>* - max value of parameter p (circle diameter) in which simplicial complexes are constructed
* *<use_landmarks>* - allows to replace large amount of input points by their landmarks reducing compute time
* *<max_landmarks>* - integer value setting how many points should be taken by landmarks
* *<output_folder>* - folder in which program should write its computing results

## Output
Program produces serveral files in specified output folder
Barcodes_<num_dimension>.png where <num_dimension> is number of the dimension processed
persistent_homology_summary.txt - all discovered persistence intervals saved in text file
entropy.json - JSON array with all barcodes entropy. 

