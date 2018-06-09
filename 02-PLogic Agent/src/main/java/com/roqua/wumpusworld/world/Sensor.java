package com.roqua.wumpusworld.world;

import java.util.logging.Logger;

import org.newdawn.slick.geom.Vector2f;

import com.roqua.wumpusworld.world.Agent.Action;


public class Sensor {

	private WWorld w;
	private float currSample;
	static final float SAMPLE_RATE = 1.0f;
	private static final Logger log = Logger.getLogger(Sensor.class.getName());
	
	public Sensor(WWorld world) {
		this.w = world;
		this.currSample = 0;
	}
	
	public boolean isBreezeOn() {
		for(Vector2f v : w.getPitsPos()) {
			int pitX = (int) v.x;
			int pitY = (int) v.y;
			int aX = w.getAgent().getX();
			int aY = w.getAgent().getY();
			if(aX == (pitX-1) && aY == pitY) {
				return true;
			} else if(aX == (pitX+1) && aY == pitY) {
				return true;
			} else if(aX == pitX && aY == (pitY-1)) {
				return true;
			} else if(aX == pitX && aY == (pitY+1)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isStenchOn() {
		int sX = (int) w.getWumpusPos().x;
		int sY = (int) w.getWumpusPos().y;
		int aX = w.getAgent().getX();
		int aY = w.getAgent().getY();
		if(aX == (sX-1) && aY == sY) {
			return true;
		} else if(aX == (sX+1) && aY == sY) {
			return true;
		} else if(aX == sX && aY == (sY-1)) {
			return true;
		} else if(aX == sX && aY == (sY+1)) {
			return true;
		}
		return false;
	}
	
	public boolean isGoldOn() {
		int gX = (int) w.getGoldPos().x;
		int gY = (int) w.getGoldPos().y;
		int aX = w.getAgent().getX();
		int aY = w.getAgent().getY();
		if(gX == aX && gY == aY) {
			return true;
		} else {
			return false;
		}
	}
	
	public void update(int delta) {
		currSample += delta / 1000f;
		if(currSample >= SAMPLE_RATE) {
			Action ac = w.getAgent().onNextAction();
			updateAgent(ac);
			validateAction();
			System.out.println(w.toString());
			currSample -= SAMPLE_RATE;
		}
	}

	private void updateAgent(Action ac) {
		int x = w.getAgent().getX();
		int y = w.getAgent().getY();
		if(ac == Action.DOWN) {
			y -= 1;
		} else if(ac == Action.UP) {
			y += 1;
		} else if(ac == Action.LEFT) {
			x -= 1;
		} else if(ac == Action.RIGHT) {
			x += 1;
		}
		if(w.inField(x, y)) {
			w.getAgent().setPosition(x, y);
			log.info("Action performed : " + ac.toString());
		} else {
			log.info("No action performed. Bumped into the wall!");
		}
	}
	
	private void validateAction() {
		int x = w.getAgent().getX();
		int y = w.getAgent().getY();
		if(!w.noPit(x, y)) {
			log.info("That was a pit on [" + x + "," + y + "]. You died!");
			System.exit(0);
		} else if(!w.noWumpus(x, y)) {
			log.info("That was a wumpus on [" + x + "," + y + "]. You died!");
			System.exit(0);
		} else if(!w.noGold(x, y)) {
			log.info("You have the gold. AWEEESOME!");
			System.exit(0);
		}
	}
	
	public int getWorldWidth() {
		return w.gettWidth();
	}
	
	public int getWorldHeight() {
		return w.gettHeight();
	}

}
