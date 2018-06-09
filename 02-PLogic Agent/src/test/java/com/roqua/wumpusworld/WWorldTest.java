package com.roqua.wumpusworld;

import static org.junit.Assert.*;

import org.junit.Test;

import com.roqua.wumpusworld.world.WWorld;

public class WWorldTest {

	@Test
	public void addPitsTest() {
		WWorld w = new WWorld.Builder().
				setEqualDimension(6).
				addPits(1,2,3).
				build();
		assertEquals(0, w.getPitsPos().size());
		WWorld w1 = new WWorld.Builder().
				setEqualDimension(6).
				addPits(1,2,3,4).
				build();
		assertEquals(2, w1.getPitsPos().size());
		WWorld w2 = new WWorld.Builder().
				setEqualDimension(6).
				addPits(1,2,3,4).
				build();
		assertEquals(1, (long)w2.getPitsPos().get(0).x);
		assertEquals(2, (long)w2.getPitsPos().get(0).y);
		assertEquals(3, (long)w2.getPitsPos().get(1).x);
		assertEquals(4, (long)w2.getPitsPos().get(1).y);
	}
}
