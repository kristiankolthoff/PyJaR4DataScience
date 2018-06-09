package com.roqua.wumpusworld;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.roqua.wumpusworld.utils.Loader;
import com.roqua.wumpusworld.world.Agent;
import com.roqua.wumpusworld.world.AgentKB;
import com.roqua.wumpusworld.world.Sensor;
import com.roqua.wumpusworld.world.WWorld;



public class SensorTest {

	private Agent a;
	private WWorld w;
	
	@Before
	public void init() {
		this.a = new AgentKB();
		this.w = Loader.loadWWorld("world-ex-1-6x6.world", a);
	}
	
	@Test
	public void breezeOnTest() {
		a.setSensor(new Sensor(w));
		assertFalse(a.getSensor().isBreezeOn());
		a.setPosition(0, 2);
		assertTrue(a.getSensor().isBreezeOn());
		a.setPosition(1, 3);
		assertTrue(a.getSensor().isBreezeOn());
		a.setPosition(4, 3);
		assertTrue(a.getSensor().isBreezeOn());
		a.setPosition(4, 4);
		assertFalse(a.getSensor().isBreezeOn());
	}
	
	@Test
	public void goldOnTest() {
		a.setSensor(new Sensor(w));
		assertFalse(a.getSensor().isGoldOn());
		a.setPosition(1, 4);
		assertTrue(a.getSensor().isGoldOn());
		a.setPosition(4, 4);
		assertFalse(a.getSensor().isGoldOn());
	}
	
	@Test
	public void stenchOnTest() {
		a.setSensor(new Sensor(w));
		assertFalse(a.getSensor().isStenchOn());
		a.setPosition(4, 3);
		assertTrue(a.getSensor().isStenchOn());
		a.setPosition(5, 4);
		assertTrue(a.getSensor().isStenchOn());
		a.setPosition(5, 5);
		assertFalse(a.getSensor().isStenchOn());
	}

}
