package com.roqua.wumpusworld.world;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.newdawn.slick.geom.Vector2f;

import com.roqua.wumpusworld.utils.Loader;


public class WWorld {

	private int tWidth;
	private int tHeight;
	private ArrayList<Vector2f> pitsPos;
	private Vector2f wumpusPos;
	private Vector2f goldPos;
	private Agent agent;
	
	
	public WWorld(int tWidth, int tHeight,ArrayList<Vector2f> pitsPos, Vector2f wumpusPos, 
			Vector2f goldPos,Agent agent) {
		this.tWidth = tWidth;
		this.tHeight = tHeight;
		this.pitsPos = pitsPos;
		this.wumpusPos = wumpusPos;
		this.goldPos = goldPos;
		this.agent = agent;
	}

	public ArrayList<Vector2f> getPitsPos() {
		return pitsPos;
	}

	public void setPitsPos(ArrayList<Vector2f> pitsPos) {
		this.pitsPos = pitsPos;
	}

	public Vector2f getWumpusPos() {
		return wumpusPos;
	}

	public void setWumpusPos(Vector2f wumpusPos) {
		this.wumpusPos = wumpusPos;
	}

	public Vector2f getGoldPos() {
		return goldPos;
	}

	public void setGoldPos(Vector2f goldPos) {
		this.goldPos = goldPos;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
	public int gettWidth() {
		return tWidth;
	}

	public void settWidth(int tWidth) {
		this.tWidth = tWidth;
	}

	public int gettHeight() {
		return tHeight;
	}

	public void settHeight(int tHeight) {
		this.tHeight = tHeight;
	}
	
	public void setEqualDimension(int size) {
		this.tHeight = size;
		this.tWidth = size;
	}
	
	public boolean noPit(int x, int y) {
		for(Vector2f v : pitsPos) {
			if(v.x == x && v.y == y){
				return false;
			}
		}
		return true;
	}
	
	public boolean noWumpus(int x, int y) {
		return (x != wumpusPos.x || y != wumpusPos.y);
	}
	
	public boolean noAgent(int x, int y) {
		return  (x != agent.getX() || y != agent.getY());
	}
	
	public boolean noGold(int x, int y) {
		return  (x != goldPos.x || y != goldPos.y);
	}
	
	public boolean inField(int x, int y) {
		return (x < tWidth && x >= 0) && (y < tHeight && y >= 0);
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int row = tWidth - 1; row >= 0; row--) {
			for (int col = 0; col < tHeight; col++) {
				int len = sb.length();
				if(agent.getX() == col && agent.getY() == row) {
					sb.append(Loader.AGENT);
				} else if(wumpusPos.x == col && wumpusPos.y == row) {
					sb.append(Loader.WUMPUS);
				} else if(goldPos.x == col && goldPos.y == row) {
					sb.append(Loader.GOLD);
				} else {
					for(int l = 0; l < pitsPos.size(); l++) {
						Vector2f v = pitsPos.get(l);
						if(v.x == col && v.y == row) {
							sb.append(Loader.PIT);
							break;
						}
					}
				}
				if(sb.length() == len) {
					sb.append(Loader.FILL_SPACE);
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}



	public static class Builder {
		
		private int tWidth;
		private int tHeight;
		private ArrayList<Vector2f> p;
		private Vector2f w;
		private Vector2f g;
		private Agent a;
		
		public static final Logger log = Logger.getLogger(Builder.class.getName());
		
		public Builder() {
			this.tWidth = -1;
			this.tHeight = -1;
			this.p = new ArrayList<>();
			this.w = new Vector2f(-1,-1);
			this.g = new Vector2f(-1,-1);
			this.a = new Agent() {
				
				@Override
				public Action onNextAction() {
					return null;
				}
			};
		}
		
		public Builder setWidth(int width) {
			if(width <= 0) {
				return this;
			}
			this.tWidth = width;
			return this;
		}
		
		public Builder setHeight(int height) {
			if(height <= 0) {
				return this;
			}
			this.tHeight = height;
			return this;
		}
		
		public Builder setDimension(int width, int height) {
			if(width <= 0 || height <= 0) {
				return this;
			}
			this.tWidth = width;
			this.tHeight = height;
			return this;
		}
		
		public Builder setEqualDimension(int size) {
			if(size <= 0) {
				return this;
			}
			this.tWidth = size;
			this.tHeight = size;
			return this;
		}
		
		public Builder addPit(int x, int y) {
			if(!nonNegative(x, y)) {
				return this;
			}
			if(noAgent(x, y) && noGold(x, y) && noWumpus(x, y) && inDimension(x, y)) {
				p.add(new Vector2f(x, y));
				return this;
			}
			this.log(x, y, "pit");
			return this;
		}
		
		public Builder addPits(int... pitCoor) {
			if(pitCoor.length % 2 != 0) {
				return this;
			}
			for (int i = 0; i < pitCoor.length - 1; i+=2) {
				addPit(pitCoor[i], pitCoor[i+1]);
			}
			return this;
		}
		
		public Builder addAgent(Agent a) {
			if(!nonNegative(a.getX(), a.getY())) {
				return this;
			}
			int x = a.getX();
			int y = a.getY();
			if(noGold(x, y) && noWumpus(x, y) && noPit(x, y) && inDimension(x, y)) {
				this.a = a;
				return this;
			}
			this.log(x, y, "agent");
			return this;
		}
			
		
		public Builder addWumpus(int x, int y) {
			if(!nonNegative(x, y)) {
				return this;
			}
			if(noAgent(x, y) && noGold(x, y) && noPit(x, y) && inDimension(x, y)) {
				w = new Vector2f(x,y);
				return this;
			}
			this.log(x, y, "wumpus");
			return this;	
		}
		
		public Builder addGold(int x, int y) {
			if(!nonNegative(x, y)) {
				return this;
			}
			if(noAgent(x, y) && noWumpus(x, y) && noPit(x, y) && inDimension(x, y)) {
				g = new Vector2f(x,y);
				return this;
			}
			this.log(x, y, "gold");
			return this;
			
		}
		
		private boolean nonNegative(int x, int y) {
			return x >= 0 && y >= 0;
		}
		
		private boolean noPit(int x, int y) {
			for(Vector2f v : p) {
				if(v.x == x && v.y == y){
					return false;
				}
			}
			return true;
		}
		
		private boolean noWumpus(int x, int y) {
			return (x != w.x || y != w.y);
		}
		
		private boolean noAgent(int x, int y) {
			return  (x != a.getX() || y != a.getY());
		}
		
		private boolean noGold(int x, int y) {
			return  (x != g.x || y != g.y);
		}
		
		private boolean inDimension(int x, int y) {
			return x < tWidth && y < tHeight;
		}
		
		public WWorld build() {
			return new WWorld(tWidth, tHeight, p, w, g, a);
		}
		
		private void log(int x, int y, String entity) {
			log.info("Position [" + x + "," + y + "] already occupied. Adding " + entity +  " refused.");
		}
		
	}
}
