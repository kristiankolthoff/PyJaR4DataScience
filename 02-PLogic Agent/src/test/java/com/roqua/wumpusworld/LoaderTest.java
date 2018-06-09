package com.roqua.wumpusworld;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.roqua.wumpusworld.utils.Loader;
import com.roqua.wumpusworld.world.Agent;
import com.roqua.wumpusworld.world.AgentKB;
import com.roqua.wumpusworld.world.WWorld;



public class LoaderTest {

	@Test
	public void testLoadWWorld() {
		Agent a = new AgentKB();
		WWorld w = Loader.loadWWorld("world-ex-1-6x6.world", a);
		assertEquals(6, w.gettHeight());
		assertEquals(6, w.gettWidth());
		assertEquals(3, w.getAgent().getX());
		assertEquals(0, w.getAgent().getY());
		assertEquals(1, (int)w.getGoldPos().x);
		assertEquals(4, (int)w.getGoldPos().getY());
	}

}
