package com.roqua.wumpusworld;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.roqua.wumpusworld.world.AgentKB;
import com.roqua.wumpusworld.world.Sensor;
import com.roqua.wumpusworld.world.WWorld;


public class AgentKBTest {

	private AgentKB a;
	private WWorld w;
	
	@Before
	public void init() {
		this.a = new AgentKB();
		this.w = new WWorld.Builder().
				setEqualDimension(5).build();
		this.a.setSensor(new Sensor(w));
	}
	
	@Test
	public void tileToIntMapperBTest() {
		assertEquals(5, a.tileToIntMapperB(4, 0));
		assertEquals(6, a.tileToIntMapperB(0, 1));
		assertEquals(3, a.tileToIntMapperB(2, 0));
		assertEquals(-1, a.tileToIntMapperB(-1, 10));
		assertEquals(-1, a.tileToIntMapperB(4, -1));
		assertEquals(-1, a.tileToIntMapperB(5, 0));
		assertEquals(-1, a.tileToIntMapperB(0, 5));
	}
	
	@Test
	public void tileToIntMapperSTest() {
		assertEquals(105, a.tileToIntMapperS(4, 0));
		assertEquals(106, a.tileToIntMapperS(0, 1));
		assertEquals(103, a.tileToIntMapperS(2, 0));
		assertEquals(-1, a.tileToIntMapperS(-1, 10));
		assertEquals(-1, a.tileToIntMapperS(4, -1));
		assertEquals(-1, a.tileToIntMapperS(5, 0));
		assertEquals(-1, a.tileToIntMapperS(0, 5));
	}
	
	@Test
	public void addClauseObservedBreezeTest() {
		a.setPosition(2, 1);
		a.addClauseObservedBreeze();
		assertArrayEquals(new int[]{13, 3, 9, 7}, a.getKb().get(a.getKb().size()-1));
		a.setPosition(0, 0);
		a.addClauseObservedBreeze();
		assertArrayEquals(new int[]{6, 2}, a.getKb().get(a.getKb().size()-1));
		a.setPosition(4, 4);
		a.addClauseObservedBreeze();
		assertArrayEquals(new int[]{20, 24}, a.getKb().get(a.getKb().size()-1));
	}
	
	@Test
	public void addClauseObservedStenchTest() {
		a.setPosition(2, 1);
		a.addClauseObservedStench();
		assertArrayEquals(new int[]{113, 103, 109, 107}, a.getKb().get(a.getKb().size()-1));
		a.setPosition(0, 0);
		a.addClauseObservedStench();
		assertArrayEquals(new int[]{106, 102}, a.getKb().get(a.getKb().size()-1));
		a.setPosition(4, 4);
		a.addClauseObservedStench();
		assertArrayEquals(new int[]{120, 124}, a.getKb().get(a.getKb().size()-1));
	}
	
	@Test
	public void addClauseNotObservedBreeze() {
		this.w.setEqualDimension(4);
		a.setPosition(1, 1);
		a.addClauseNotObservedBreeze();
		assertArrayEquals(new int[]{-5}, a.getKb().get(a.getKb().size()-1));
		assertArrayEquals(new int[]{-7}, a.getKb().get(a.getKb().size()-2));
		assertArrayEquals(new int[]{-2}, a.getKb().get(a.getKb().size()-3));
		assertArrayEquals(new int[]{-10}, a.getKb().get(a.getKb().size()-4));
	}
	
	@Test
	public void addClauseNotObservedStench() {
		this.w.setEqualDimension(4);
		a.setPosition(1, 1);
		a.addClauseNotObservedStench();
		assertArrayEquals(new int[]{-105}, a.getKb().get(a.getKb().size()-1));
		assertArrayEquals(new int[]{-107}, a.getKb().get(a.getKb().size()-2));
		assertArrayEquals(new int[]{-102}, a.getKb().get(a.getKb().size()-3));
		assertArrayEquals(new int[]{-110}, a.getKb().get(a.getKb().size()-4));
	}
	
	@After
	public void clean() {
		a.getKb().clear();
	}
}
