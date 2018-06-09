package com.roqua.wumpusworld.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.util.Log;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

public class AgentKB extends Agent{

	private List<Action> actions;
	private List<int[]> kb;
	private ISolver solver;
	private IProblem problem;
	private Random r;
	
	final int MAXVAR = 1000;
	final int NBCLAUSES = 50000;
	final boolean DEBUG = true;
	
	private final static Logger log = Logger.getLogger(AgentKB.class.getName());
	
	public AgentKB(int x, int y) {
		super(x, y);
		this.actions = new ArrayList<>();
		this.kb = new ArrayList<>();
		this.solver = SolverFactory.newDefault();
		this.solver.newVar(MAXVAR);
		this.solver.setExpectedNumberOfClauses(NBCLAUSES);
		this.r = new Random();
	}
	
	public AgentKB() {
		super();
		this.actions = new ArrayList<>();
		this.kb = new ArrayList<>();
		this.solver = SolverFactory.newDefault();
		this.solver.newVar(MAXVAR);
		this.solver.setExpectedNumberOfClauses(NBCLAUSES);
		this.r = new Random();
	}
	
	/**
	 * Tell the KB that at position (x,y) there is no wumpus and no pit,
	 * hence the tile is save to walk on
	 */
	private void addSaveTile(int x, int y) {
		try {
			this.solver.addClause(new VecInt(new int[]{-tileToIntMapperB(x, y)}));
			this.solver.addClause(new VecInt(new int[]{-tileToIntMapperS(x, y)}));
			this.kb.add(new int[]{-tileToIntMapperB(x, y)});
			this.kb.add(new int[]{-tileToIntMapperS(x, y)});
		} catch (ContradictionException e) {
			Log.error("Initialization of the KB failed");
		}
	}

	@Override
	public Action onNextAction() {
		addSaveTile(x, y);
		if(s.isBreezeOn()) {
			addClauseObservedBreeze();
		} else {
			addClauseNotObservedBreeze();
		}
		if(s.isStenchOn()) {
			addClauseObservedStench();
		} else {
			addClauseNotObservedStench();
		}
		
		if(DEBUG) {
			printKB();
		}
		return decideNextAction();
	}
	
	public void addClauseObservedBreeze() {
		final int upCoor = tileToIntMapperB(x, y + 1);
		final int downCoor = tileToIntMapperB(x, y - 1);
		final int rightCoor = tileToIntMapperB(x + 1, y);
		final int leftCoor = tileToIntMapperB(x - 1, y);
		List<Integer> clauses = new ArrayList<>();
		if(upCoor >= 0) {
			clauses.add(upCoor);
		}
		if(downCoor >= 0) {
			clauses.add(downCoor);
		}
		if(rightCoor >= 0) {
			clauses.add(rightCoor);
		}
		if(leftCoor >= 0) {
			clauses.add(leftCoor);
		}
		int[] dimacsClause = new int[clauses.size()];
		for (int i = 0; i < dimacsClause.length; i++) {
			dimacsClause[i] = clauses.get(i).intValue();
		}
		try {
			solver.addClause(new VecInt(dimacsClause));
			kb.add(dimacsClause);
		} catch (ContradictionException e) {
			log.log(Level.SEVERE, "Error in addClauseObservedBreeze while updating knowledge base");
		}
	}
	
	public void addClauseObservedStench() {
		final int upCoor = tileToIntMapperS(x, y + 1);
		final int downCoor = tileToIntMapperS(x, y - 1);
		final int rightCoor = tileToIntMapperS(x + 1, y);
		final int leftCoor = tileToIntMapperS(x - 1, y);
		List<Integer> clauses = new ArrayList<>();
		if(upCoor >= 0) {
			clauses.add(upCoor);
		}
		if(downCoor >= 0) {
			clauses.add(downCoor);
		}
		if(rightCoor >= 0) {
			clauses.add(rightCoor);
		}
		if(leftCoor >= 0) {
			clauses.add(leftCoor);
		}
		int[] dimacsClause = new int[clauses.size()];
		for (int i = 0; i < dimacsClause.length; i++) {
			dimacsClause[i] = clauses.get(i).intValue();
		}
		try {
			solver.addClause(new VecInt(dimacsClause));
			kb.add(dimacsClause);
		} catch (ContradictionException e) {
			log.log(Level.SEVERE, "Error in addClauseObservedStench while updating knowledge base");
		}
	}
	
	public void addClauseNotObservedBreeze() {
		try {
			final int upCoor = tileToIntMapperB(x, y + 1);
			final int downCoor = tileToIntMapperB(x, y - 1);
			final int rightCoor = tileToIntMapperB(x + 1, y);
			final int leftCoor = tileToIntMapperB(x - 1, y);
			if(upCoor >= 0) {
				solver.addClause(new VecInt(new int[]{-upCoor}));
				kb.add(new int[]{-upCoor});
			}
			if(downCoor >= 0) {
				solver.addClause(new VecInt(new int[]{-downCoor}));
				kb.add(new int[]{-downCoor});
			}
			if(rightCoor >= 0) {
				solver.addClause(new VecInt(new int[]{-rightCoor}));
				kb.add(new int[]{-rightCoor});
			}
			if(leftCoor >= 0) {
				solver.addClause(new VecInt(new int[]{-leftCoor}));
				kb.add(new int[]{-leftCoor});
				}
		} catch (ContradictionException e) {
			log.log(Level.SEVERE, "Error in addClauseNotObservedBreeze while updating knowledge base");
		}	
	}
	
	public void addClauseNotObservedStench() {
		try {
		final int upCoorS = tileToIntMapperS(x, y + 1);
		final int downCoorS = tileToIntMapperS(x, y - 1);
		final int rightCoorS = tileToIntMapperS(x + 1, y);
		final int leftCoorS = tileToIntMapperS(x - 1, y);
		if(upCoorS >= 0) {
				solver.addClause(new VecInt(new int[]{-upCoorS}));
			kb.add(new int[]{-upCoorS});
		}
		if(downCoorS >= 0) {
			solver.addClause(new VecInt(new int[]{-downCoorS}));
			kb.add(new int[]{-downCoorS});
		}
		if(rightCoorS >= 0) {
			solver.addClause(new VecInt(new int[]{-rightCoorS}));
			kb.add(new int[]{-rightCoorS});
		}
		if(leftCoorS >= 0) {
			solver.addClause(new VecInt(new int[]{-leftCoorS}));
			kb.add(new int[]{-leftCoorS});
		}
		} catch (ContradictionException e) {
			log.log(Level.SEVERE, "Error in addClauseNotObservedStench while updating knowledge base");
		}
	}
	
	private Action decideNextAction() {
		actions.clear();
		problem = solver;
		try {
			if(!problem.isSatisfiable(new VecInt(new int[]{tileToIntMapperB(x, y+1)}))
					&& !problem.isSatisfiable((new VecInt(new int[]{tileToIntMapperS(x, y+1)})))) {
				actions.add(Action.UP);
			}
			if(!problem.isSatisfiable(new VecInt(new int[]{tileToIntMapperB(x, y-1)}))
					&& !problem.isSatisfiable((new VecInt(new int[]{tileToIntMapperS(x, y-1)})))) {
				actions.add(Action.DOWN);
			}
			if(!problem.isSatisfiable(new VecInt(new int[]{tileToIntMapperB(x+1, y)}))
					&& !problem.isSatisfiable((new VecInt(new int[]{tileToIntMapperS(x+1, y)})))) {
				actions.add(Action.RIGHT);
			}
			if(!problem.isSatisfiable(new VecInt(new int[]{tileToIntMapperB(x-1, y)}))
					&& !problem.isSatisfiable((new VecInt(new int[]{tileToIntMapperS(x-1, y)})))) {
				actions.add(Action.LEFT);
			}
			printPossibleActions();
			return actions.get(r.nextInt(actions.size()));
		} catch (TimeoutException e) {
			Log.error("Error during decision making " + e.getMessage());
		}
		return Action.DEFAULT;
	}
	
	public int tileToIntMapperB(int x, int y) {
		return (x >= 0 && y >= 0 && x < s.getWorldWidth() && y < s.getWorldHeight()) 
				? ((y * s.getWorldWidth()) + x + 1) : -1;
	}
	
	public int tileToIntMapperS(int x, int y) {
		final int S_DEFAULT = 100;
		return (x >= 0 && y >= 0 && x < s.getWorldWidth() && y < s.getWorldHeight()) 
				? ((y * s.getWorldWidth() + x + 1 + S_DEFAULT)) : -1;
	}
	
	public void printKB() {
		for(int[] clause : kb) {
			for (int i = 0; i < clause.length; i++) {
				System.out.print(clause[i] + " ");
			}
			System.out.print(" --- ");
		}
	}
	
	public void printPossibleActions() {
		System.out.println("Possible valid actions:");
		for(Action a : actions) {
			System.out.print(a.toString() + " + ");
		}
		System.out.println();
	}

	public List<int[]> getKb() {
		return kb;
	}

	public void setKb(List<int[]> kb) {
		this.kb = kb;
	}

	public ISolver getSolver() {
		return solver;
	}

	public void setSolver(ISolver solver) {
		this.solver = solver;
	}

	@Override
	public void setSensor(Sensor s) {
		super.setSensor(s);
		this.addSaveTile(x, y);
	}
}
