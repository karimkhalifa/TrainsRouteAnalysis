package BusinessTier;

import DataAccessTier.DirectTripsGraph;

import java.io.IOException;
import java.util.Map;

/**
 * Created by karim on 12/16/2016.
 */
public class RouteAnalysis {

    private DirectTripsGraph directTripsGraph;

    // Direct Trip Graph
    // Map< Start Point, Map<endPoint, Distance to endPoint>>
    private final Map<String,Map<String,Integer>> directTripGraphMap;

    public RouteAnalysis() throws IOException {

        directTripsGraph =new DirectTripsGraph();

        directTripGraphMap = directTripsGraph.readInputData();
    }

    private Integer getDirectTripDistance(String startPoint,String endPoint)
    {
        if(directTripGraphMap.get(startPoint)!=null && directTripGraphMap.get(startPoint).get(endPoint)!=null)
            return directTripGraphMap.get(startPoint).get(endPoint);
        else
            return null;
    }

    public Integer getRouteDistance(String [] routePoints)
    {
        if(routePoints.length>1) {
            Integer totalDistance=0;
            Integer distance;
            for (int i=0;i<routePoints.length-1;i++) {
                distance=getDirectTripDistance(routePoints[i],routePoints[i+1]);
                if(distance!=null)
                    totalDistance=totalDistance + distance;
                else
                    return null;
            }
            return totalDistance;
        }
        else return null;
   }


}
