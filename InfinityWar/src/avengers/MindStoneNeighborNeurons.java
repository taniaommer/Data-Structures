package avengers;

import java.util.HashMap;

/**
 * Given a Set of Edges representing Vision's Neural Network, identify all of the 
 * vertices that connect to the Mind Stone. 
 * List the names of these neurons in the output file.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * MindStoneNeighborNeuronsInputFile name is passed through the command line as args[0]
 * Read from the MindStoneNeighborNeuronsInputFile with the format:
 *    1. v (int): number of neurons (vertices in the graph)
 *    2. v lines, each with a String referring to a neuron's name (vertex name)
 *    3. e (int): number of synapses (edges in the graph)
 *    4. e lines, each line refers to an edge, each line has 2 (two) Strings: from to
 * 
 * Step 2:
 * MindStoneNeighborNeuronsOutputFile name is passed through the command line as args[1]
 * Identify the vertices that connect to the Mind Stone vertex. 
 * Output these vertices, one per line, to the output file.
 * 
 * Note 1: The Mind Stone vertex has out degree 0 (zero), meaning that there are 
 * no edges leaving the vertex.
 * 
 * Note 2: If a vertex v connects to the Mind Stone vertex m then the graph has
 * an edge v -> m
 * 
 * Note 3: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut:
 *     StdOut.setFile(outputfilename);
 *     //Call StdOut.print() for EVERY neuron (vertex) neighboring the Mind Stone neuron (vertex)
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/MindStoneNeighborNeurons mindstoneneighborneurons.in mindstoneneighborneurons.out
 *
 * @author Yashas Ravi
 * 
 */


public class MindStoneNeighborNeurons {
    
    public static void main (String [] args) {
        
    	if ( args.length < 2 ) {
            StdOut.println("Execute: java MindStoneNeighborNeurons <INput file> <OUTput file>");
            return;
        }
    	

    	// STEP 1:

        String MindStoneNeighborNeuronsInputFile = args[0];
        String MindStoneNeighborNeuronsOutputFile = args[1];

        // set the input file
        StdIn.setFile(MindStoneNeighborNeuronsInputFile);

        int neurons = StdIn.readInt();  // number of vertices
        HashMap<String, Integer> neuronName = new HashMap<>();
        HashMap<Integer, String> flippedNeuronName = new HashMap<>(); // to return final key
        // needs to be Integer as key to be returned later

        // storing names of neurons in HashMaps
        for(int i = 0; i < neurons; i++){
            String temp = StdIn.readString();
            neuronName.put(temp, i);
            flippedNeuronName.put(i, temp);
        }

        int synapses = StdIn.readInt();
        int[][] edges = new int[neurons][neurons];
        int neuronOutDegree = -1; // stores index of any neuron that has an outdegree of 0, no axons

        for(int i = 0; i < synapses; i++){
            String vFrom = StdIn.readString(); // first string
            String vTo = StdIn.readString(); // second string
            
            int rFrom = neuronName.get(vFrom); // returns index value of the specified neuron
            int cTo = neuronName.get(vTo); // returns index value of the specified neuron

            edges[rFrom][cTo] = 1; // use the returned index values
            // set the neuron pair to one axon (every nerve has one axon)
            // note: every axon connects to only one dendrite of another nerve
        }


        // STEP 2:

        for(int i = 0; i < neurons; i++){
            int outDegree = 0;
            for(int j = 0; j < neurons; j++){ // traverse through all possible neuron pairs
                if(edges[i][j] == 1){
                    outDegree++; // increase outdegree by one everytime there is an axon (when value for the pair is 1)
                }
            }
            if(outDegree == 0){ //  no axons
                neuronOutDegree = i;
                break; // breaks loop so it can check next index for possible axons
            }
        }

        // set the output file
        StdOut.setFile(MindStoneNeighborNeuronsOutputFile);

        for(int i = 0; i < neurons; i++){
            if(edges[i][neuronOutDegree] == 1){ // neurons that connect to Mind Stone
                StdOut.println(flippedNeuronName.get(i));
            }
        }

    }
}
