package DataAccessTier;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by karim on 12/16/2016.
 */
public class DirectedGraphTest {

    DirectedGraph directedGraph=new DirectedGraph();
    @Test
    public void testReadInputData() throws Exception {

        Map<String,Map<String,Integer>> directTripGraphMap= directedGraph.readInputData();
        System.out.println(directTripGraphMap);
    }
}