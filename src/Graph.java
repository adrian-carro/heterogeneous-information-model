import java.util.Random;
import java.io.IOException;
import java.lang.Math;
import java.lang.Object;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Graph Generation for asymmetric simulation
 *
 * INET Research Trinity Term 2019
 *
 * @author Ryan Lauderback, Adrian Carro
  */
// TODO: Add get Neighbors method returning a list
public class Graph {
	int [][]graph;
	int population;
	int gType;
	Random r;

	public Graph(int population, int graphType){
		this.graph = new int [population][population];
		this.population = population;
		this.gType = graphType;
		this.r = new Random();
		// generate the network
		if (graphType==0) {
			fullyConnected();
		}
		else if (graphType==1) {
			erdosRenyi(0.4f);
		}
		else if (graphType==2) {
			one1Dimensional(4);
		}
		else if (graphType==3) {
			twoDimensional();
		}
	}
	// returns graph 
	public int[][] getGraph(){
		return this.graph;
	}
	// fully connected graph
	public void fullyConnected(){
		for (int i=0;i<this.population;i++) {
			for (int j=0;j<this.population ;j++ ) {
			this.graph[i][j] = 1;
			}
		}	
	}
	// ER: Erdos-Renyi random network with <k> = avDeg
	public void erdosRenyi(float psuedoDegree){
		// populate the graph
		for (int i=0;i<this.population;i++) {
			for (int j=0;j<this.population ;j++ ) {
				// randomly seeds the connections of individuals in the market
				// always no connection bc it is 0 -> 0
				if(i == j) {
					this.graph[i][j] = 0;
				}
				else if(this.r.nextFloat() <= psuedoDegree) {
					this.graph[i][j] = 1;
					this.graph[j][i] = 1;
				}
				else{this.graph[i][j] = 0;
					 this.graph[j][i] = 0;
				}
			}
		}
	}
	public void one1Dimensional(int avDeg){
		// make sure avg deg is less than population
		if (avDeg >= this.population) {
			System.out.println("average Degree of network must be less than population");
			return;
		}
		// each node has edges equal to the average degree
		int current = 1;
		for (int i=0;i<this.population;i++ ) {
			if (i < 2) {
				this.graph[i][current] = 1;
				this.graph[current][i] = 1;
				this.graph[i][current+1] = 1;
				this.graph[current+1][i] = 1;
				this.graph[i][current+2] = 1;
				this.graph[current+2][i] = 1;
				this.graph[i][current+3] = 1;
				this.graph[current+3][i] = 1;
			}
			else if (i >= this.population - 3){
				this.graph[i][current-2] = 1;
				this.graph[current-2][i] = 1;
				this.graph[i][current-3] = 1;
				this.graph[current-3][i] = 1;
				this.graph[i][current-4] = 1;
				this.graph[current-4][i] = 1;
				this.graph[i][current-5] = 1;
				this.graph[current-5][i] = 1;
				
			}
			else{
				this.graph[i][current] = 1;
				this.graph[current][i] = 1;

				this.graph[i][current+1] = 1;
				this.graph[current+1][i] = 1;

				this.graph[i][current-2] = 1;
				this.graph[current-2][i] = 1;
				this.graph[i][current-3] = 1;
				this.graph[current-3][i] = 1;
			}
			current++;
		}

	}
	// 1D: One-dimensional regular network with <k> = avDeg
	public void oneDimensional(){
		for (int i=0;i<this.population;i++ ) {
			for (int j=0;j<this.population ;j++ ) {
				if (Math.abs(i - j) == 1) {
					this.graph[i][j] = 1;
				}
				else this.graph[i][j] = 0;
			}
		}
	}
	// 2D: Two-dimensional regular network with <k> = 4
	public void twoDimensional(){
		for (int i=0;i<this.population;i++ ) {
			for (int j=0;j<this.population ;j++ ) {
				if (Math.abs(i - j) == 1) {
					this.graph[i][j] = 1;
				}
				else if (Math.abs(i - j) == 2){
					this.graph[i][j] = 1;
				}
				else this.graph[i][j] = 0;
			}
		}
	}
	// return indices of neighbors of given agent
	public Integer[] getNeighbors(int neighbor){
		ArrayList<Integer> al = new ArrayList<Integer>();
		for (int i=0;i<this.population;i++ ) {
			if (this.graph[neighbor][i] == 1) {
				al.add(i);
			}
		}
		Integer[] arr = new Integer[al.size()]; 
		arr = al.toArray(arr);
		return arr;
	}


	public static void main(String[] args) throws InterruptedException, IOException {
		Graph s = new Graph(10,1);

		// System.out.println(Arrays.toString(s.graph));
	}
}
