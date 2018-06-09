package com.roqua.wumpusworld.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.roqua.wumpusworld.utils.Loader;
import com.roqua.wumpusworld.world.Agent;
import com.roqua.wumpusworld.world.AgentKB;
import com.roqua.wumpusworld.world.Sensor;
import com.roqua.wumpusworld.world.WWorld;



public class MainGameState extends BasicGameState{

	private Agent a;
	
	@Override
	public void init(GameContainer gc, StateBasedGame s) throws SlickException {
		a = new AgentKB();
		WWorld w = Loader.loadWWorld("world-ex-1-6x6.world", a);
		System.err.println("Wumpus World initialized");
		System.out.println(w.toString());
		a.setSensor(new Sensor(w));
	}

	@Override
	public void render(GameContainer gc, StateBasedGame s, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame s, int delta) throws SlickException {
		a.getSensor().update(delta);
	}

	@Override
	public int getID() {
		return 0;
	}

}
