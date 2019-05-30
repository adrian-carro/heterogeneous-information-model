import java.util.Random;
import java.io.IOException;
import java.lang.Math;
import java.lang.Object;
import java.util.Arrays;

/**
 * Heterogeneous Information Market Model
 *
 * Network simulation where agent position on asset is determinant of market price and positions of neighbors (graph
 * based interaction <V,E>). Perfect information access in market is defined as a fully connected graph.
 *
 * INET Research Trinity Term 2019
 *
 * @author Ryan Lauderback, Adrian Carro
 */

public class HetInfoSimulation {
    Graph g;
    Agent [] agents;
    int population;
    float delta;
    float fundPrice0;
    float marketPrice;
    Random r;
    int n;
    // constructor for the simulation
    public HetInfoSimulation(int graphType, int population, int state0, float priceInit, float nbrInfluence){
        this.g = new Graph(population,graphType);
        this.agents = new Agent[population];
        this.r = new Random();
        // populate list of agents
        for (int i=0;i<state0 ;i++ ) {
            this.agents[i] = new Agent(0,0.5f,0.5f, priceInit + (float) Math.abs(r.nextGaussian()) );

        }
        for (int i=state0;i<population;i++ ) {
            this.agents[i] = new Agent(1,0.5f,0.5f,priceInit - (float) Math.abs(r.nextGaussian()) );
        }
        // fill remaining fields
        this.population = population;
        this.delta = nbrInfluence;
        this.fundPrice0 = priceInit;
        this.marketPrice = priceInit;
        this.n = state0;
    }
    // Accessor Methods
    public int getState0(){
        return this.n;
    }
    public int getState1(){
        return this.population - this.n;
    }
    public float getMarketPrice(){
        return this.marketPrice;
    }

    // updating the price of individual agent with given formulation:
    // 		fundamental price = Pf0 + ∂(Pf0 - avgPf ) + (1 - ∂ / 2)(Pf0 - marketPrice)
    public void asynchronousUpdate(){
        int agent = r.nextInt(this.population);
        Integer[] neighbors = this.g.getNeighbors(agent);
        int[] intArray = Arrays.stream(neighbors).mapToInt(Integer::intValue).toArray();
        float totalPrice = 0;
        Agent a = this.agents[agent];
        // get neighbors prices and sum
        for (int i=0; i < neighbors.length; i++ ) {
            totalPrice += this.agents[neighbors[i]].getPrice();
        }
        // calculate new price
        float initPrice = a.getPrice();
        System.out.println("initial fundamental price of Agent " + agent + " with state "+ a.getState() +": " + initPrice);
        float newPrice = initPrice + ( ((totalPrice/neighbors.length) - initPrice)*(this.delta))
                + ( ((1-this.delta)/2) * (this.marketPrice - initPrice) );
        // set agent's new price
        a.setPrice(newPrice);
        System.out.println("Current Market Price: "+ this.marketPrice);
        System.out.println("new fundamental price of Agent " + agent + ": " + newPrice);
        // get previous state and set new state with new price
        int initState = a.getState();
        a.updateState(this.marketPrice);
        // update overall state of population (n->optimist) (population-n -> pessimist)
        // if (a.getState() - initState == 1){
        // 	this.n -=1;
        // }
        // else if(a.getState() - initState == -1){
        // 	this.n +=1;
        // }
        int newN = 0;
        for (Agent ag:this.agents ) {
            if (ag.getState() == 0) {
                newN++;
            }
        }
        System.out.println(newN);
        this.n = newN;
        System.out.println("optimists in the Market of 20: " + this.n);
    }

    // update market price
    public void priceUpdate(){
        System.out.println( ((2*(float)this.n)/(float)this.population)-1 ) ;
        this.marketPrice = this.fundPrice0 * (float) Math.exp((double) ((2*(float)this.n)/(float)this.population)-1 );
        System.out.println("New Market Price:" + this.marketPrice +"\n----------------------------");
    }


    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Hello World!");

        HetInfoSimulation sim = new HetInfoSimulation(1,200,100,10.0f,0.9f);
        for (int i=0;i<20; i++) {
            sim.asynchronousUpdate();
            sim.priceUpdate();
        }

    }
}
