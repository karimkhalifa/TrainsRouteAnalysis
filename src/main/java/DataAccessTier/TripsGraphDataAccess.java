package DataAccessTier;

import Config.ConfigManager;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

//Class Description: This class is used for Extracting the graph route for direct trips from input data file
public class TripsGraphDataAccess {


    public void DirectedGraph()
    {

    }

    //This Function Description: This function reads the input data and load it into HashMap
    // Function Output: Graph HashMap  in the form of Map<StartPoint, Map<EndPoint,Distance>>
    public Map<String,Map<String,Integer>> readInputData() throws IOException {


        final Map<String,Map<String,Integer>> directTripGraphMap=new HashMap<String, Map<String, Integer>>();
        Map<String,Integer> endPointTripMap;

        String line;
        String startPoint;
        String endPoint;
        Integer distance;
        //Create InputStream
        InputStream inputStream = new FileInputStream(ConfigManager.getInstance().getConfig().getInputGraphFilePath());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        //Read the Input Data file in which each line is a startPoint,EndPoint & Distance Entry
        while ((line = bufferedReader.readLine()) != null) {
                //Extract the Points, Distance Info
                startPoint= line.substring(0,1);
                endPoint= line.substring(1,2);
                distance= Integer.parseInt(line.substring(2,line.length()));
                //Load the extract info into the HashMap
               if( directTripGraphMap.containsKey(startPoint))
                   endPointTripMap=directTripGraphMap.get(startPoint);
               else
                   endPointTripMap=new HashMap<String, Integer>();

                endPointTripMap.put(endPoint,distance);
            directTripGraphMap.put(startPoint,endPointTripMap );
        }
        return directTripGraphMap;
    }
}
