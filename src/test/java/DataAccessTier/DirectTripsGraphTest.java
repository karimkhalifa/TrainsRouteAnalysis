package DataAccessTier;

import org.junit.Test;

import java.util.Map;


public class DirectTripsGraphTest {

    DirectTripsGraph directTripsGraph =new DirectTripsGraph();
    @Test
    public void testReadInputData() throws Exception {

        Map<String,Map<String,Integer>> directTripGraphMap= directTripsGraph.readInputData();
        System.out.println(directTripGraphMap);
    }
}