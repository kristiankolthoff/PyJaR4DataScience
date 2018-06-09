package com.roqua.wumpusworld.ui;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.roqua.wumpusworld.utils.Loader;
import com.roqua.wumpusworld.world.Agent;
import com.roqua.wumpusworld.world.AgentKB;
import com.roqua.wumpusworld.world.Sensor;
import com.roqua.wumpusworld.world.WWorld;


public class WumpusGame extends StateBasedGame{

	public static final int S_WIDTH = 700;
	public static final int S_HEIGHT = 500;
	public static final boolean FULLSCREEN_MODE = false;
	
	public WumpusGame(String name) {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new MainGameState());
	}
	
	public static void main(String[] args) {
		try{
		Agent a = new AgentKB(3,0);
		WWorld w = new WWorld.Builder().
				setEqualDimension(6).
				addWumpus(1, 1).
				addGold(2,2).
				addAgent(a).
				addPit(0, 5).
				addPits(4,5,3,3).
				build();
		WWorld w1  = Loader.loadWWorld("world-test-4x4.world", a);
		a.setSensor(new Sensor(w1));
		System.out.println(w1.toString());
		AppGameContainer container = new AppGameContainer(new WumpusGame("Wumpus World"));
		container.setDisplayMode(S_WIDTH, S_HEIGHT, FULLSCREEN_MODE);
		container.start();
	} catch (SlickException e) {
		e.printStackTrace();
	}
	}

}
