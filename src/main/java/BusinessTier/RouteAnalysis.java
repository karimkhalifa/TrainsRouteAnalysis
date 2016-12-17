package BusinessTier;

import BusinessTier.dto.Inequality;
import BusinessTier.util.IntegerUtil;
import DataAccessTier.DirectTripsGraph;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class RouteAnalysis {

    private DirectTripsGraph directTripsGraph;

    // Direct Trip Graph
    // Map< Start Point, Map<endPoint, Distance to endPoint>>
    private final Map<String,Map<String,Integer>> directTripGraphMap;
    //    Map< StartPoint,EndPoint>

    private int numOfTrips;

    public RouteAnalysis() throws IOException {
        directTripsGraph =new DirectTripsGraph();
        directTripGraphMap = directTripsGraph.readInputData();
    }

    private Integer getDirectTripDistance(String startPoint,String destinationEndPoint)
    {
        if(directTripGraphMap.get(startPoint)!=null && directTripGraphMap.get(startPoint).get(destinationEndPoint)!=null)
            return directTripGraphMap.get(startPoint).get(destinationEndPoint);
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

    public Integer getTripCountByStopLimit(String startPoint, String destinationEndPoint, int stopLimit, Inequality inequality)
    {
        numOfTrips=0;
        return calculateNumOfTripByStops(startPoint,destinationEndPoint,0,stopLimit,inequality);
    }
    private Integer calculateNumOfTripByStops(String currentPoint,String destinationEndPoint,int iterationNum,int stopLimit,Inequality inequality)
    {
        // Exit if iteration reached the stopLimit
        if(iterationNum>=stopLimit)  return 0;
        for (Map.Entry<String, Integer> entry : directTripGraphMap.get(currentPoint).entrySet())
        {
            //if target destination point is reached then increment number of trips by 1
            if(destinationEndPoint.equals(entry.getKey().toString())) {
                //Check the Inequality operator
                if (inequality==Inequality.EXACT_EQUAL && (iterationNum+1)==stopLimit) numOfTrips = numOfTrips + 1;
                else if(inequality==Inequality.LESS_THAN_OR_EQUAL) numOfTrips = numOfTrips + 1;
            }
            //recursive function
            calculateNumOfTripByStops(entry.getKey(), destinationEndPoint, iterationNum+1,stopLimit,inequality);
        }
        return numOfTrips;
    }

    public Integer getTripCountByDistanceLimit(String startPoint, String destinationEndPoint, int distanceLimit)
    {
        numOfTrips=0;
        return calculateNumOfTripByDistance(startPoint,destinationEndPoint,0,distanceLimit);
    }
    private Integer calculateNumOfTripByDistance(String currentPoint,String destinationEndPoint,int distanceCovered,int distanceLimit)
    {
        // Exit if distance Covered reached the limit
        if(distanceLimit<=distanceCovered)  return 0;
        for (Map.Entry<String, Integer> entry : directTripGraphMap.get(currentPoint).entrySet())
        {
            int totalDistanceToTravel=   distanceCovered +  entry.getValue()  ;
            //if target destination point is reached then increment number of trips by 1
            if(destinationEndPoint.equals(entry.getKey().toString()) && distanceLimit>totalDistanceToTravel )  numOfTrips = numOfTrips + 1;
            //recursive function
            calculateNumOfTripByDistance(entry.getKey(), destinationEndPoint, totalDistanceToTravel,distanceLimit);
        }
        return numOfTrips;
    }

    private Integer shortestDistance;
    public Integer getShortestRouteDistance(String startPoint, String destinationEndPoint)
    {
        shortestDistance= null;
        return calculateShortestRouteDistance(startPoint,destinationEndPoint,0,new HashMap<String, Map<String, Integer>>() );
    }
    private Integer calculateShortestRouteDistance(String currentPoint,String destinationEndPoint,int distanceCovered,Map<String,Map<String,Integer>> visitedPointsMap)
    {
        //Loop into the graph map for the direct trip to seach for the shortest path
        for (Map.Entry<String, Integer> entry : directTripGraphMap.get(currentPoint).entrySet())
        {
            int newDistanceToCover=   distanceCovered +  entry.getValue()  ;
            boolean isCyclic=isCyclicPoints(currentPoint,entry.getKey(),visitedPointsMap);
            //Check if the new distance covered is greater than or equal the length of the shortest path
            // Check if the current start & end point causes Cyclic (have been visited before in this path)
            if(newDistanceToCover>= IntegerUtil.nvl(shortestDistance,Integer.MAX_VALUE)  || isCyclic )
                break;
            else //if(distanceCovered<shortestDistance)
            {
                if (destinationEndPoint.equals(entry.getKey().toString()))
                {
                    shortestDistance = newDistanceToCover;
                    break;
                } else
                    calculateShortestRouteDistance(entry.getKey(), destinationEndPoint, newDistanceToCover,addPointsToMap(visitedPointsMap,currentPoint,entry.getKey(),entry.getValue()));
            }
        }
        return shortestDistance;
    }

    private boolean isCyclicPoints(String startPoint,String endPoint,Map<String,Map<String,Integer>> visitedPointsMap)
    {
        //Check if the start point and end point have been visited before in this path or not
        if(visitedPointsMap.get(startPoint)!=null &&  visitedPointsMap.get(startPoint).get(endPoint)!=null)
            return true;
        else
            return  false;
    }
    private Map<String,Map<String,Integer>>  addPointsToMap(Map<String,Map<String,Integer>> graphMap,String startPoint,String endPoint,Integer distance)
    {
        Map<String,Map<String,Integer>> newGraphMap=new HashMap<String, Map<String, Integer>>();
        Map<String,Integer> endPointTripMap=new HashMap<String, Integer>();
        newGraphMap.putAll(graphMap);
        graphMap=null;

        //Check if Start Point (key) or end point (Value) exit before
        if( newGraphMap.containsKey(startPoint))
            endPointTripMap.putAll(newGraphMap.get(startPoint));
        else
            endPointTripMap=new HashMap<String, Integer>();
        //Fill the GraphMap with the new Values
        endPointTripMap.put(endPoint,distance);
        newGraphMap.put(startPoint,endPointTripMap );

      return newGraphMap;
    }



}
