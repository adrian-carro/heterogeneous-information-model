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

public Agent(int initialState, float price){
	this.state = initialState;
	this.priceFund = price;
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
public void updateState(float marketPrice){
	if ( (this.priceFund - marketPrice) > 0 ) {
		setState(0);
	}
	else setState(1);
}

public static void main(String[] args) throws InterruptedException{
	Agent a = new Agent(0, 10);
	System.out.println(a.getState());
	a.setState(1);
	System.out.println(a.getState());
}
}