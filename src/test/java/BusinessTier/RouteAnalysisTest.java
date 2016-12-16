package BusinessTier;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by karim on 12/16/2016.
 */
public class RouteAnalysisTest {

    RouteAnalysis routeAnalysis;
    {
        try {
            routeAnalysis = new RouteAnalysis();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void testGetDirectTripDistance() throws Exception {
//        System.out.println(routeAnalysis.getDirectTripDistance("D","C"));
//        System.out.println("");
//    }


    @Test
    public void testGetRouteDistance() throws Exception {
        String [] routePoints={"A", "B", "X"};

        Integer totalDistance=routeAnalysis.getRouteDistance(routePoints);
        if(totalDistance!=null)
            System.out.println("Total Distance: "+routeAnalysis.getRouteDistance(routePoints));
        else
            System.out.println("NO SUCH ROUTE");
        System.out.println("");
    }


}