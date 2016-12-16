package DataAccessTier;

import org.junit.Test;

import java.util.Map;

/**
 * Created by karim on 12/16/2016.
 */
public class DirectTripsGraphTest {

    DirectTripsGraph directTripsGraph =new DirectTripsGraph();
    @Test
    public void testReadInputData() throws Exception {

        Map<String,Map<String,Integer>> directTripGraphMap= directTripsGraph.readInputData();
        System.out.println(directTripGraphMap);
    }
}