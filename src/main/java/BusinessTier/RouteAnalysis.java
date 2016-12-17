package BusinessTier;

import BusinessTier.dto.Inequality;
import DataAccessTier.DirectTripsGraph;
import java.io.IOException;
import java.util.Map;


public class RouteAnalysis {

    private DirectTripsGraph directTripsGraph;

    // Direct Trip Graph
    // Map< Start Point, Map<endPoint, Distance to endPoint>>
    private final Map<String,Map<String,Integer>> directTripGraphMap;
    private int numOfTrips;

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

    public Integer getTripCountByStopLimit(String startPoint, String endPoint, int stopLimit, Inequality inequality)
    {
        numOfTrips=0;
        return calculateNumOfTripByStops(startPoint,endPoint,0,stopLimit,inequality);
    }
    private Integer calculateNumOfTripByStops(String currentPoint,String endPoint,int iterationNum,int stopLimit,Inequality inequality)
    {
        // Exit if iteration reached the stopLimit
        if(iterationNum>=stopLimit)  return 0;
        for (Map.Entry<String, Integer> entry : directTripGraphMap.get(currentPoint).entrySet())
        {
            //if target destination point is reached then increment number of trips by 1
            if(endPoint.equals(entry.getKey().toString())) {
                //Check the Inequality operator
                if (inequality==Inequality.EXACT_EQUAL && (iterationNum+1)==stopLimit) numOfTrips = numOfTrips + 1;
                else if(inequality==Inequality.LESS_THAN_OR_EQUAL) numOfTrips = numOfTrips + 1;
            }
            //recursive function
            calculateNumOfTripByStops(entry.getKey(), endPoint, iterationNum+1,stopLimit,inequality);
        }
        return numOfTrips;
    }

    public Integer getTripCountByDistanceLimit(String startPoint, String endPoint, int distanceLimit)
    {
        numOfTrips=0;
        return calculateNumOfTripByDistance(startPoint,endPoint,0,distanceLimit);
    }
    private Integer calculateNumOfTripByDistance(String currentPoint,String endPoint,int distanceCovered,int distanceLimit)
    {
        // Exit if distance Covered reached the limit
        if(distanceLimit<=distanceCovered)  return 0;
        for (Map.Entry<String, Integer> entry : directTripGraphMap.get(currentPoint).entrySet())
        {
            int totalDistanceToTravel=   distanceCovered +  entry.getValue()  ;
            //if target destination point is reached then increment number of trips by 1
            if(endPoint.equals(entry.getKey().toString()) && distanceLimit>totalDistanceToTravel )  numOfTrips = numOfTrips + 1;
            //recursive function
            calculateNumOfTripByDistance(entry.getKey(), endPoint, totalDistanceToTravel,distanceLimit);
        }
        return numOfTrips;
    }






}
