package BusinessTier;

import BusinessTier.dto.Inequality;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class RouteAnalysisTest {

    RouteAnalysis routeAnalysis;
    {
        try {
            routeAnalysis = new RouteAnalysis();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testGetRouteDistance() throws Exception {
        String [] routePoints_1={"A", "B", "C"};
        Integer totalDistance_1=routeAnalysis.getRouteDistance(routePoints_1);
        if(totalDistance_1!=null)
            System.out.println("Total Distance: "+routeAnalysis.getRouteDistance(routePoints_1));
        else
            System.out.println("NO SUCH ROUTE");

        String [] routePoints_2={"A", "D"};
        Integer totalDistance_2=routeAnalysis.getRouteDistance(routePoints_2);
        if(totalDistance_2!=null)
            System.out.println("Total Distance: "+routeAnalysis.getRouteDistance(routePoints_2));
        else
            System.out.println("NO SUCH ROUTE");

        String [] routePoints_3={"A", "D", "C"};
        Integer totalDistance_3=routeAnalysis.getRouteDistance(routePoints_3);
        if(totalDistance_3!=null)
            System.out.println("Total Distance: "+routeAnalysis.getRouteDistance(routePoints_3));
        else
            System.out.println("NO SUCH ROUTE");

        String [] routePoints_4={"A", "E", "B","C","D"};
        Integer totalDistance_4=routeAnalysis.getRouteDistance(routePoints_4);
        if(totalDistance_4!=null)
            System.out.println("Total Distance: "+routeAnalysis.getRouteDistance(routePoints_4));
        else
            System.out.println("NO SUCH ROUTE");


        String [] routePoints_5={"A", "E", "D"};
        Integer totalDistance_5=routeAnalysis.getRouteDistance(routePoints_5);
        if(totalDistance_5!=null)
            System.out.println("Total Distance: "+routeAnalysis.getRouteDistance(routePoints_5));
        else
            System.out.println("NO SUCH ROUTE");


        System.out.println("");
    }


    @Test
    public void testGetTripCountByStopLimit() throws Exception {
        System.out.println(" Num of Trips by Stop: "+ routeAnalysis.getTripCountByStopLimit("C","C",3, Inequality.LESS_THAN_OR_EQUAL));
        System.out.println(" Num of Trips by Stop: "+ routeAnalysis.getTripCountByStopLimit("A","C",4, Inequality.EXACT_EQUAL));
    }



    @Test
    public void testGetTripCountByDistanceLimit() throws Exception {
        System.out.println(" Num of Trips by Distance: "+ routeAnalysis.getTripCountByDistanceLimit("C","C",30));
    }
}