# Wumpus World #

The wumpus world is an example environment for logical decision making processes based on a tiled map.
There is a wumpus - a pretty ugly and dangerous monster, beware! - and several pits where you can easily stumble.
The overall goal is to find the gold on the tile map using a logic based agent. Since the wumpus is a really ugly monster, his stench can be smelled four tiles around him (above, beneath, left, right). Since in addition the pits are very low, the breeze can be sensed also at the four surrounding tiles. The only measures you have is the sensors input feeding you with environmental information of the current tile the agent is standing on. That means that you agent only knows if there is a breeze or stench where he is currently standing at.

### The challenge ###

The challenge is now to implement the agent by yourself! To do so simply create your own agent and implement the agent class. In every call cycle you must return on of the possible actions: DEFAULT, UP, DOWN, RIGHT, LEFT.

```java
public class LogicAgent extends Agent{

	@Override
	public Action onNextAction() {
		return Action.DEFAULT;
	}

}
```

This method is invoked in every sampling update by the sensor. The Agent class provides you with the sensor object, 
which is used for the current available tile information. Use the methods isBreezeOn() and isStenchOn(). You can simply build your own tile worlds for testing by using the builder class of the world:

```java 
    Agent a = new LogicAgent(3,0);
    WWorld w = new WWorld.Builder().
		setEqualDimension(6).
		addWumpus(1, 1).
		addGold(2,2).
		addAgent(a).
		addPit(0, 5).
		addPits(4,5,3,3).
		build();
```
### SAT Solver ###

There is also a sample implementation of the agent called AgentKB. This solves the problem using propositional logic. With the environmental information provided by the sensor input, it builds up a knowledge base about the tile map. This knowledge is used to deduce new information. In each decision cycle the agent can therefore derive if a specific action like going up is save, meaning we can deduce that on the tile above there is no wumpus and also no pit. To solve this SAT problem we use a sat solver, which can be easily queried for the desired knowledge.

```java
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
```
We use the ISolver and IProblem interfaces and get a default solver, which is sufficient for our logical problem. To add propositional clauses in disjunctive normal form (provided in DIMACS format), we must first create specific identifier for each tile on the map. Therefore we map the current tile position with two simple methods for the breeze and stench to a single integer.

```java
public int tileToIntMapperB(int x, int y) {
		return (x >= 0 && y >= 0 && x < s.getWorldWidth() && y < s.getWorldHeight()) 
				? ((y * s.getWorldWidth()) + x + 1) : -1;
	}
	
	public int tileToIntMapperS(int x, int y) {
		final int S_DEFAULT = 100;
		return (x >= 0 && y >= 0 && x < s.getWorldWidth() && y < s.getWorldHeight()) 
				? ((y * s.getWorldWidth() + x + 1 + S_DEFAULT)) : -1;
	}
```

For example, the first method maps (0,0) => 1, (0,1) => and so on. The second method maps (0,0) => 1 + S_DEFAULT and so on. This constant is a must to ensure that the logical clauses not intermingle. Notice that we use two operators in the following explanations:

+ **TELL** : write an observation in form of a logical clause in the knowledge base. Hence we add knowledge to the knwoledge base.
+ **ASK** : ask the knowledge base if a clause can be satisfied.

After we have IDs for each tile, we can add the clauses to our knowledebase (solver). There are mainly four distinct cases which can occur. Assume that the agent is currently in (0,0):

1. Breeze:  if the agent observes breeze than the breeze can be in (0,1) or (1,0) ===> **TELL** b(0,1) or b(1,0)
2. Stench: if the agent observes stench than the breeeze can be in (0,1) or (1,0) ===> **TELL** s(0,1) or s(1,0)
3. NoBreeze: if the agent observes no breeze than ===> **TELL** -b(0,1), **TELL** -b(1,0)
4. NoStench: if the agent observes no stench than ===> **TELL** -s(0,1), **TELL** -s(1,0)

For example for case one we would do the following:


```java
    final int upCoor = tileToIntMapperB(x, y + 1);
    final int rightCoor = tileToIntMapperB(x + 1, y);
    solver.addClause(new VecInt(new int[]{upCoor, rightCoor}));

```
If all clauses have been added to the knowledge base in the current action cycle, the agent then computes the possible actions. A possible action is an action which can be performed without dying i.e. falling in a pit or getting eaten by the wumpus. Notice that the DEFAULT action therefore can be performed in any case and is a place marker for an actual action. To the action list, we add all actions that are possible in the current state. Assume the agent is in (0,0) and we want to compute the possible actions. We ask the knowledge base if we can go up safely by performing **ASK** -b(0,1) and -s(0,1) and if we can go safely right by performing **ASK** -b(1,0) and -s(1,0). In code it looks like the following:



```java
private Action decideNextAction() {
	actions.clear();
	problem = solver;
	if(!problem.isSatisfiable(new VecInt(new int[]{tileToIntMapperB(x, y+1)}))
		&& !problem.isSatisfiable((new VecInt(new int[]{tileToIntMapperS(x, y+1)})))) {
		actions.add(Action.UP);
	}
        //Code for other directions removed for brevity
        return actions.get(r.nextInt(actions.size()));

```
Notice that we turn around the statement. We achieve this by giving the knowledge base the assumption that in the upper tile there is a breeze and a stench, and then we look if the problem is satisfsyable. If it is not satisfyable that there is a stench or a breeze we know that this tile is safe and we add it to the possible actions list. Since it is a more or less stateless agent and non-deterministic in his decisions, we randomly choose one of the possible acitons. In fair fields, this agent is able to find the gold and stays alive.
