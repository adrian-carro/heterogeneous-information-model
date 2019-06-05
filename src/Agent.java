import java.util.Random;
import java.util.ArrayList;
import java.lang.Math;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * INET Research Trinity Term 2019
 *
 * @author Ryan Lauderback, Adrian Carro
 */
public class Agent{
	int state;
	float a;
	float b;
	float priceFund;
	Random r;

public Agent(int initialState, float price){
	this.state = initialState;
	this.priceFund = price;
	this.r = new Random();
}
// accessor methods
public int getState(){return this.state;}
public float getPrice(){return this.priceFund;}

public void setState(int newState){
	this.state = newState;
}

public void setPrice(float newPrice){
	this.priceFund = newPrice;
}
// Method for STEP 2 of Agent asynchronous update
public void updateState(float marketPrice, float temp){
	float probBuy;
	// check for division by 0
	if ((temp == 0) & (marketPrice == this.priceFund)){
		probBuy = 0.5f;
	} else {
		probBuy = 1 / (1 + (float) Math.exp( (marketPrice - this.priceFund)/temp ) );
	}
	// determine position change with random float
	if (this.r.nextFloat() <= probBuy){
		this.setState(1);
	}
	else this.setState(0);
}

public static void main(String[] args) throws InterruptedException{
	Agent a = new Agent(0, 10);
	System.out.println(a.getState());
	a.setState(1);
	System.out.println(a.getState());
}
}