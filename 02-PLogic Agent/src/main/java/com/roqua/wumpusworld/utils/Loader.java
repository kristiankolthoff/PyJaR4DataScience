package com.roqua.wumpusworld.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.roqua.wumpusworld.world.Agent;
import com.roqua.wumpusworld.world.WWorld;
import com.roqua.wumpusworld.world.WWorld.Builder;



public class Loader {

	public static final char FILL_SPACE = 'X';
	public static final char PIT = 'P';
	public static final char GOLD = 'G';
	public static final char WUMPUS = 'W';
	public static final char AGENT = 'A';
	
	public Loader(){}
	
	public static WWorld loadWWorld(String worldName, Agent a) {
		File file = new File("./src/resources/models/" + worldName);
		try {
			BufferedReader b = new BufferedReader(new FileReader(file));
			String line = b.readLine();
			int rowC = 0;
			while(line != null) {
				rowC++;
				line = b.readLine();
			}
			b = new BufferedReader(new FileReader(file));
			line = b.readLine();
			Builder builder = new WWorld.Builder();
			builder.setDimension(line.length(), rowC);
			int row = 0;
			while(line != null) {
				for (int col = 0; col < line.length(); col++) {
					int r = rowC - row - 1;
					char c = line.charAt(col);
					if(c == PIT) {
						builder.addPit(col, r);
					} else if(c == GOLD) {
						builder.addGold(col, r);
					} else if(c == WUMPUS) {
						builder.addWumpus(col, r);
					} else if(c == AGENT) {
						a.setX(col);
						a.setY(r);
						builder.addAgent(a);
					}
				}
				row++;
				line = b.readLine();
			}
			return builder.build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
