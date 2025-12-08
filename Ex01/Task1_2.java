import java.util.LinkedList;
import java.util.Random;

class NetworkSimulator{

    boolean[][] graph;
    int dim;
    LinkedList<Integer> visited = new LinkedList<Integer>();

    public NetworkSimulator(boolean[][] graph, int dim){
        this.graph = graph;
        this.dim = dim;
    }

    public void printConnections(){
        for(int i = 0; i < dim; i++){
            System.out.printf("Node %d: ", i + 1);
            for(int j = 0; j < dim; j++){
                if(graph[i][j]){
                    System.out.printf("%d, ", j + 1);
                }
            }
            System.out.println();
        }
    }

    public LinkedList<Integer> getNeighbors(int node){
        LinkedList<Integer> neighbors = new LinkedList<Integer>();
        for(int i = 0; i < graph.length; i++){
            if(graph[i][node]){
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    public double simulate(){
        Random r = new Random();

        long totalTime = 0;

        int tries = 5000;

        for(int i = 0; i < tries; i++){
            int a = r.nextInt(dim);
            int b = r.nextInt(dim);
            long thisTime = simulate(a, b);

            if(thisTime < 0) System.out.printf("%d, %d, %d", thisTime, a, b);

            totalTime += thisTime;

        }

        return totalTime / (double)tries;
    }

    public long simulate(int from, int to){
        LinkedList<Integer> toCheck_nodes = new LinkedList<Integer>();
        LinkedList<Long> toCheck_time = new LinkedList<Long>();

        toCheck_nodes.add(from);
        toCheck_time.add(0l);

        while(!toCheck_nodes.isEmpty()){
            int currentNode = toCheck_nodes.pop();
            long currentTime = toCheck_time.pop();

            if(currentNode == to){
                return currentTime;
            } else {
                LinkedList<Integer> neighbors = getNeighbors(currentNode);

                long nextTime = currentTime + 5;

                for(int neighbor : neighbors){
                    if(visited.contains(neighbor)){
                        continue;
                    }
                    toCheck_nodes.add(neighbor);
                    toCheck_time.add(nextTime);
                }
            }

            visited.add(currentNode);
        }

        return -1;
    }
}

public class Task1_2{

    public static void main(String[] args){
        // 17 x 17
        boolean[][] graph = new boolean[][] {
            // 1.   2.     3.     4.     5.     6.     7.     8.     9.     10.    11.    12.    13.    14.    15.    16.    17
            {false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}, // 1
            {true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false}, // 2
            {false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false}, // 3
            {false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false}, // 4
            {false, false, false, true, false, true, false, false, false, false, false, false, true, true, false, false, false}, // 5
            {false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false}, // 6
            {false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false}, // 7
            {false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false}, // 8
            {false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false}, // 9
            {false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false}, // 10
            {false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false}, // 11
            {false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false}, // 12
            {false, false, false, false, true, false, false, false, false, false, false, true, false, false, false, false, false}, // 13
            {false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false}, // 14
            {false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false}, // 15
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true}, // 16
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false}  // 17
        };

        NetworkSimulator sim = new NetworkSimulator(graph, 17);

        // sim.printConnections();

        double time = sim.simulate();

        System.out.printf("Time: %f\n", time);

        //long time = sim.simulate(0, 3);

    }
}