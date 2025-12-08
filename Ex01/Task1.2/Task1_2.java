import java.util.LinkedList;
import java.util.Random;

// Simple class to represent a node
class Node{
    // Number of the node
    public int number;
    // Time it takes to reach this node.
    public long time;

    public Node(int number, long time){
        this.number = number;
        this.time = time;
    }
}

// Class to simulate a network based on a graph
class NetworkSimulator{

    // Adjacency matrix of the graph
    int[][] graph;
    // How many nodes the graph has
    int dim;

    // List of nodes that have already bee visited for the current simulation.
    LinkedList<Integer> visited;
    // Number of iterations to do for the simulation
    int iterations;

    public NetworkSimulator(int[][] graph, int dim, int iterations){
        this.graph = graph;
        this.dim = dim;
        this.iterations = iterations;
    }

    // Simulate the network
    public double simulate(){
        Random r = new Random();

        long totalTime = 0;

        for(int i = 0; i < iterations; i++){
            // Chose two random nodes
            int a = r.nextInt(dim);
            int b = r.nextInt(dim);

            // Simulate the signal
            long thisTime = simulate(a, b);

            // Collect runtime
            totalTime += thisTime;

        }
        
        // Calculate average of runtime
        return totalTime / (double)iterations;
    }

    // Simulate a signal between two nodes
    public long simulate(int from, int to){

        // Clear the visited nodes
        visited = new LinkedList<Integer>();

        // List of nodes that need to be checked
        LinkedList<Node> nodesToCheck = new LinkedList<Node>();

        // Add the start node with a initial signal travel time of 0
        nodesToCheck.add(new Node(from, 0l));

        // Process all nodes that still need to be checked
        while(!nodesToCheck.isEmpty()){
            
            Node current = nodesToCheck.pop();

            // If the current node is the target
            if(current.number == to){
                // Return the time taken to get there
                return current.time;
            } else {
                // Calculate the time the signal needs to the next nodes
                long nextTime = current.time + 5;

                // Loop over all neighbors
                for(int neighbor : graph[current.number]){
                    // If the neighbor has not yet been visited
                    if(visited.contains(neighbor)){
                        continue;
                    }

                    // Add it to the nodes that need to be checked
                    nodesToCheck.add(new Node(neighbor, nextTime));
                }
            }

            // Mark the current node as visited
            visited.add(current.number);
        }

        // In case the target could not be reached, return -1
        return -1;
    }
}

// Task 1.2
public class Task1_2{

    public static void main(String[] args){

        // Define the two netwok graphs
        int[][] graph_SimpleNetwork = new int[][] {
            /*0 */ {1},
            /*1 */ {0, 2},
            /*2 */ {1, 3},
            /*3 */ {2, 4},
            /*4 */ {3, 5, 12, 13},
            /*5 */ {4, 6},
            /*6 */ {5, 7},
            /*7 */ {6, 8},
            /*8 */ {7},
            /*9 */ {10},
            /*10*/ {9, 11},
            /*11*/ {10, 12},
            /*12*/ {11, 4},
            /*13*/ {4, 14},
            /*14*/ {13, 15},
            /*15*/ {14, 16},
            /*16*/ {15}
        };

        int[][] graph_AdvancedNetwork = new int[][] {
            /*0 */ {1, 9, 16},
            /*1 */ {0, 2},
            /*2 */ {1, 3},
            /*3 */ {2, 4},
            /*4 */ {3, 5, 12, 13},
            /*5 */ {4, 6},
            /*6 */ {5, 7},
            /*7 */ {6, 8},
            /*8 */ {7, 9, 16},
            /*9 */ {10, 0, 8},
            /*10*/ {9, 11},
            /*11*/ {10, 12},
            /*12*/ {11, 4},
            /*13*/ {4, 14},
            /*14*/ {13, 15},
            /*15*/ {14, 16},
            /*16*/ {15, 0, 8}
        };

        // Create the network simulators
        NetworkSimulator simpleNetwork = new NetworkSimulator(graph_SimpleNetwork, 17, 5000);

        NetworkSimulator advancedNetwork = new NetworkSimulator(graph_SimpleNetwork, 17, 5000);

        // Perform simulation and print results
        System.out.printf("Simple Network: %fms; Advanced Network: %fms\n", simpleNetwork.simulate(), advancedNetwork.simulate());
    }
}