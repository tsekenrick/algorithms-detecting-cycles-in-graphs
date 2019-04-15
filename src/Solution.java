import java.io.*;
import java.util.*;

public class Solution {
	static Graph graph; // global variable representing the graph

	static int[] color; // global variable storing the color
	                    // of each node during DFS: 
	                    //    0 for white, 1 for gray, 2 for black
	
	static int[] parent;  // global variable representing the parent 
	                       //of each node in the DFS forest
	static int flag = 0;
	static int[] start;
	static int[] finish;
	static void recDFS(Graph graph, int u, int time) {
		color[u] = 1;
		//start[u] = ++time;
		int hasCycle = 0;
		
		Edge curr = graph.A[u];
		while(curr != null && flag == 0){
			
			//edge goes to white
			if(color[curr.succ] == 0){
				parent[curr.succ] = u;
				recDFS(graph, curr.succ, time);
			}
			
			//edge goes to gray
			else if(color[curr.succ] == 1){
				flag = 1;
				int trace = u;
				ArrayList<Integer> cycle = new ArrayList<>();
				cycle.add(u);
				System.out.println(1);
				while(trace != curr.succ){
					trace = parent[trace];
					cycle.add(trace);
				
				}
				for(int i = cycle.size(); i > 0; i--){
					System.out.print((cycle.get(i-1) + 1) + " ");
				}
				System.out.println();
				hasCycle = 1;
				return;
			}
			
			curr = curr.next;
		}
				
		color[u] = 2;
		//finish[u] = ++time;		
				
	}
	
	static void DFS(Graph graph) {
	   // performs a "full" DFS on graph
		//initialization
		int time = 0;		
		for(int i = 0; i < graph.A.length; i++) {
			color[i] = 0;
			parent[i] = -1;
		}
		
		//gets value of 1 if any calls on recDFS detect a cycle
		for(int i=0; i<color.length; i++){
			if (color[i] == 0) {
				recDFS(graph, i, time);
			}
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out, "ASCII"), 4096);
        String raw = br.readLine();
        int[] data = {Integer.parseInt(raw.split("\\s")[0]), Integer.parseInt(raw.split("\\s")[1])};
        graph = new Graph(data[0]);
        parent = new int[data[0]];
        color = new int[data[0]];
        //start = new int[data[0]];
        //finish = new int[data[0]];
        
        for(int i=0; i<data[1]; i++){	
        	String[] edgeInfo = br.readLine().split("\\s");
        	int[] edge = {Integer.parseInt(edgeInfo[0]), Integer.parseInt(edgeInfo[1])};
        	graph.addEdge(edge[0] - 1, edge[1] - 1);
        }
        
        DFS(graph);
        if (flag == 0) System.out.println(0);
	}


	
}

// Data structures for representing a graph

class Edge {
   int succ; //index of the node that this edge points to
   Edge next; //next edge that belongs to this node

   Edge(int succ, Edge next) {
      this.succ = succ;
      this.next = next;
   }
}

class Graph {
   Edge[] A; 
   // A[u] points to the head of a liked list;
   // p in the list corresponds to an edge u -> p.succ in the graph

   Graph(int n) {
      // initialize a graph with n vertices and no edges
      A = new Edge[n];
   }

   void addEdge(int u, int v) {
      // add an edge i -> j to the graph

      A[u] = new Edge(v, A[u]);
   }
}
