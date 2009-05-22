package test.org.tigris.mbt.generators;

import org.apache.log4j.Logger;
import org.tigris.mbt.Util;
import org.tigris.mbt.conditions.EdgeCoverage;
import org.tigris.mbt.conditions.StopCondition;
import org.tigris.mbt.generators.RandomPathGenerator;
import org.tigris.mbt.generators.PathGenerator;
import org.tigris.mbt.io.GraphML;
import org.tigris.mbt.machines.FiniteStateMachine;

import junit.framework.TestCase;

public class RandomPathGeneratorTest extends TestCase {

	private Logger logger = Util.setupLogger(RandomPathGeneratorTest.class);
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void test_WeightedRandomGeneration()
    {
		logger.info( "TEST: test_WeightedRandomGeneration" );
		logger.info( "=======================================================================" );
		GraphML gml = new GraphML();
		gml.load("graphml/weight/FSM.graphml");
		FiniteStateMachine FSM = new FiniteStateMachine(gml.getModel());
		FSM.setWeighted(true);
		
		StopCondition sc = new EdgeCoverage(1.0);
		PathGenerator pathGenerator = new RandomPathGenerator();
		pathGenerator.setMachine(FSM);
		pathGenerator.setStopCondition(sc);
		
		while(pathGenerator.hasNext())
		{
			String[] stepPair = pathGenerator.getNext();

			int stats[] = FSM.getStatistics();
			int ec = 100*stats[1]/stats[0];

			logger.debug("call( "+ stepPair[0] + " ) then verify( " + stepPair[1] + " ) --> Edge coverage @ " + ec +"%" );
		}
		logger.debug("==============================");
    }

	public void test_RandomGeneration()
    {
		logger.info( "TEST: test_RandomGeneration" );
		logger.info( "=======================================================================" );
		GraphML gml = new GraphML();
		gml.load("graphml/weight/FSM.graphml");
		FiniteStateMachine FSM = new FiniteStateMachine(gml.getModel());
		FSM.setWeighted(false);
		StopCondition sc = new EdgeCoverage(1.0);
		PathGenerator pathGenerator = new RandomPathGenerator();
		pathGenerator.setMachine(FSM);
		pathGenerator.setStopCondition(sc);
		
		while(pathGenerator.hasNext())
		{
			String[] stepPair = pathGenerator.getNext();

			int stats[] = FSM.getStatistics();
			int ec = 100*stats[1]/stats[0];

			logger.debug("call( "+ stepPair[0] + " ) then verify( " + stepPair[1] + " ) --> Edge coverage @ " + ec +"%" );
		}
		logger.debug("==============================");
    }
}