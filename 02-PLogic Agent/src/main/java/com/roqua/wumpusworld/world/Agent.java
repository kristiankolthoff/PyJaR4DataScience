package com.roqua.wumpusworld.world;

public abstract class Agent {

	protected int x;
	protected int y;
	protected Sensor s;
	public enum Action {
		DEFAULT, LEFT, RIGHT, UP, DOWN,
	};
	
	public Agent(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Agent() {
		this.x = -1;
		this.y = -1;
	}
	
	public abstract Action onNextAction();

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}	
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Sensor getSensor() {
		return s;
	}

	public void setSensor(Sensor s) {
		this.s = s;
	}
	
}
