package BusinessTier;

import BusinessTier.dto.Inequality;
import BusinessTier.util.IntegerUtil;
import DataAccessTier.TripsGraphDataAccess;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//Class Description: This class is used for Analysis of the graph route and provide some insight analysis on it
public class RouteAnalysis {

    //Data Access Layer for Trip Graph Data extracting
    private TripsGraphDataAccess tripsGraphDataAccess;

    // Trip Graph HashMap Variable-->  Map< Start Point, Map<endPoint, Distance to endPoint>>
    private final Map<String,Map<String,Integer>> directTripGraphMap;
    //Total Number of Trip variable
    private int numOfTrips;

    //Class contractor
    public RouteAnalysis() throws IOException {

        tripsGraphDataAccess =new TripsGraphDataAccess();
        //reads the input data and load it into HashMap
        directTripGraphMap = tripsGraphDataAccess.readInputData();
    }

    // Function Description: This function get Distance between two points from the graph HashMap
    // Input: Start Point, End Point
    // Output: distance Value
    private Integer getDirectTripDistance(String startPoint,String destinationEndPoint)
    {   //Check if start & end Point exits, if yes then return distance
        if(directTripGraphMap.get(startPoint)!=null && directTripGraphMap.get(startPoint).get(destinationEndPoint)!=null)
            return directTripGraphMap.get(startPoint).get(destinationEndPoint);
        else
            return null;
    }

    // Function Description: This function get the total Distance given the path of a given route
    // Input: route Points path
    // Output: distance Value (in case no route, it returns NULL)
    public Integer getRouteDistance(String [] routePoints)
    {   //Check if input points is more than 1 route points
        if(routePoints.length>1) {
            Integer totalDistance=0;
            Integer distance;
            //Loop to get the distance value through each start/end point in the route
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

    // Function Description: This function get the total number of trips given a number of stops
    // Input: Start Point , destination point, number of stop limit & the inequality condition (e.g. less Than or Equal, Exact Equal,..)
    // Output: Total number of trips
    public Integer getTripCountByStopLimit(String startPoint, String destinationEndPoint, int stopLimit, Inequality inequality)
    {
        numOfTrips=0;
        //Call the recursive function to calculate the total number of trips given a number of stops
        return calculateNumOfTripByStops(startPoint,destinationEndPoint,0,stopLimit,inequality);
    }

    // Function Description: This function is recursive function to calculate total number of trips given a number of stops
    // Input: Current Point , final destination point, incremental iteration number,stop limit & the inequality condition (e.g. less Than or Equal, Exact Equal,..)
    // Output: Total number of trips
    private Integer calculateNumOfTripByStops(String currentPoint,String destinationEndPoint,int iterationNum,int stopLimit,Inequality inequality)
    {
        // Exit if iteration reached the stopLimit
        if(iterationNum>=stopLimit)  return 0;
        //Loop the Graph HashMap to Navigate through route paths
        for (Map.Entry<String, Integer> entry : directTripGraphMap.get(currentPoint).entrySet())
        {
            //if target destination point is reached then increment number of trips by 1
            if(destinationEndPoint.equals(entry.getKey().toString())) {
                //Check the Inequality operator
                // if Inequality operator is equal (exactly)
                if (inequality==Inequality.EXACT_EQUAL && (iterationNum+1)==stopLimit) numOfTrips = numOfTrips + 1;
                //if Inequality operator is Less Than or equal (Maximum)
                else if(inequality==Inequality.LESS_THAN_OR_EQUAL) numOfTrips = numOfTrips + 1;
            }
            //recursive function to move to the next node
            calculateNumOfTripByStops(entry.getKey(), destinationEndPoint, iterationNum+1,stopLimit,inequality);
        }
        return numOfTrips;
    }

    // Function Description: This function get the total number of trips given distance covered limit
    // Input: Start Point , destination point, distance covered limit
    // Output: Total number of trips
    public Integer getTripCountByDistanceLimit(String startPoint, String destinationEndPoint, int distanceLimit)
    {
        numOfTrips=0;
        //Call the recursive function to calculate the total number of trips given distance covered limit
        return calculateNumOfTripByDistance(startPoint,destinationEndPoint,0,distanceLimit);
    }

    // Function Description: This function is recursive function to calculate total number of trips given distance covered limit
    // Input: Current Point , final destination point, distance covered so far ,distance limit
    // Output: Total number of trips
    private Integer calculateNumOfTripByDistance(String currentPoint,String destinationEndPoint,int distanceCovered,int distanceLimit)
    {
        // Exit if distance Covered reached the limit
        if(distanceLimit<=distanceCovered)  return 0;
        //Loop the Graph HashMap to Navigate through route paths
        for (Map.Entry<String, Integer> entry : directTripGraphMap.get(currentPoint).entrySet())
        {
            int totalDistanceToTravel=   distanceCovered +  entry.getValue()  ;
            //if target destination point is reached then increment number of trips by 1
            if(destinationEndPoint.equals(entry.getKey().toString()) && distanceLimit>totalDistanceToTravel )  numOfTrips = numOfTrips + 1;
            //recursive function to move to the next node
            calculateNumOfTripByDistance(entry.getKey(), destinationEndPoint, totalDistanceToTravel,distanceLimit);
        }
        return numOfTrips;
    }

    // Function Description: This function mainly used to get the shortest distanced given the start and end point
    // Input: Start Point , destination point
    // Output: Shortest distance value (in case no route, it returns NULL)
    private Integer shortestDistance;
    public Integer getShortestRouteDistance(String startPoint, String destinationEndPoint)
    {
        shortestDistance= null;
        //Call the recursive function to calculate the Shortest distance value given the start and end point
        return calculateShortestRouteDistance(startPoint,destinationEndPoint,0,new HashMap<String, Map<String, Integer>>() );
    }

    // Function Description: This function is recursive function to calculate shortest distance given the start and end point
    // Input: Current Point , final destination point, distance covered so far , all Points visited during moving in this path
    // Output: Total number of trips
    private Integer calculateShortestRouteDistance(String currentPoint,String destinationEndPoint,int distanceCovered,Map<String,Map<String,Integer>> visitedPointsMap)
    {
        //Loop into the graph map for the direct trip to search for the shortest path
        for (Map.Entry<String, Integer> entry : directTripGraphMap.get(currentPoint).entrySet())
        {
            int newDistanceToCover=   distanceCovered +  entry.getValue()  ;
            boolean isCyclic=isCyclicPoints(currentPoint,entry.getKey(),visitedPointsMap);
            //Check if the new distance covered is greater than or equal the length of the shortest path
            // Check if the current start & end point causes Cyclic (have been visited before in this path)
            if(newDistanceToCover>= IntegerUtil.nvl(shortestDistance,Integer.MAX_VALUE)  || isCyclic )
                break;
            else
            {   //Check if the next point reached is the final destination point then break to stop and reset shortest distance value
                if (destinationEndPoint.equals(entry.getKey().toString()))
                {
                    shortestDistance = newDistanceToCover;
                    break;
                } else    //recursive function to move to the next node
                    calculateShortestRouteDistance(entry.getKey(), destinationEndPoint, newDistanceToCover,addPointsToMap(visitedPointsMap,currentPoint,entry.getKey(),entry.getValue()));
            }
        }
        return shortestDistance;
    }

    // Function Description: This function mainly used to check if there is a cyclic while navigating through route path
    // Input: Start Point , destination point , visited points so far
    // Output: boolean value to indicate if there is a cyclic or not
    private boolean isCyclicPoints(String startPoint,String endPoint,Map<String,Map<String,Integer>> visitedPointsMap)
    {
        //Check if the start point and end point have been visited before in this path or not
        if(visitedPointsMap.get(startPoint)!=null &&  visitedPointsMap.get(startPoint).get(endPoint)!=null)
            return true;
        else
            return  false;
    }

    // Function Description: This function mainly used to add Start,End & Distance value to HashMap
    // Input: Start Point , destination point , visited points HashMap
    // Output: new Appended visited points HashMap
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
