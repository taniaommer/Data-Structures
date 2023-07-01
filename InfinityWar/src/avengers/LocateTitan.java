package avengers;
/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the matrix)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */



public class LocateTitan {

    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }


// STEP 1:

        String locateTitanInputFile = args[0];
        String locateTitanOutputFile = args[1];

	    // set the input file
        StdIn.setFile(locateTitanInputFile);

        int totalGens = StdIn.readInt();
        int genNum = 0;
        double func = 0;

        double[] gens = new double[totalGens];
        int[][] matrix = new int[totalGens][totalGens];

        // populates array that holds funcionality value for each generator
        // i and genNum are both the generator number
        for(int i = 0; i < totalGens; i++){
            genNum = StdIn.readInt();   // must read this int first to access func
            func = StdIn.readDouble();
            gens[i] = func;
        }

        // populate array with energy costs from generator to generator
        for(int i = 0; i < totalGens; i++){
            for(int j = 0; j < totalGens; j++){
                matrix[i][j] = StdIn.readInt();
            }
        }


// STEP 2:
        
        // update matrix to represent total cost to travel from one generator to another
        for(int i = 0; i < totalGens; i++){
            for(int j = 0; j < totalGens; j++){
                double edge = matrix[i][j];
                matrix[i][j] = (int)(edge / (gens[i] * gens[j]));
            }
        }


// STEP 3:

        StdOut.setFile(locateTitanOutputFile);

        int[] minCost = new int[totalGens]; // totalGens = num of vertices
        boolean[] DijkstraSet = new boolean[totalGens]; 

        // sets minCost values to infinity for all vertices except 1
        // sets all vertices to false (not visited)
        minCost[0] = 0;
        for(int vertex = 1; vertex < totalGens; vertex++){
            minCost[vertex] = Integer.MAX_VALUE;
        }

        int currentSource = 0;
        int minVertex = 0;
        int minCostValue = Integer.MAX_VALUE;

        for(int i = 0; i < totalGens; i++){
            currentSource = getMinCostNode(minCost, DijkstraSet); // helper method below
            DijkstraSet[currentSource] = true; // mark as visited

            // update distance if it can be lowered
            for(int j = 0; j < totalGens; j++){
                minCostValue = minCost[currentSource] + matrix[currentSource][j];
                if(!DijkstraSet[j] && minCostValue < minCost[j] && matrix[currentSource][j] != 0){
                    minCost[j] = minCostValue;
                    minVertex = j;
                }
            }

        }

        int lowestCost = minCost[totalGens - 1];
        StdOut.print(lowestCost);

    }

    // helper method for Dijkstra's Algorithm
    private static int getMinCostNode(int[] minCost, boolean[] DijkstraSet){
        int min = Integer.MAX_VALUE;
        int minNode = 0;

        for(int i = 0; i < minCost.length; i++){
            if(!DijkstraSet[i] && minCost[i] < min){
                min = minCost[i];
                minNode = i;
            }
        }
        return minNode;
    }
}
