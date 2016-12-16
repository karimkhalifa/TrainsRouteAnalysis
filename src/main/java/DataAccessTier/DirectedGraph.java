package DataAccessTier;

import Config.ConfigManager;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by karim on 12/16/2016.
 */
public class DirectedGraph {

    //InputFile Path $$$$$$$$$$$$





    public void DirectedGraph()
    {

    }


    public Map<String,Map<String,Integer>> readInputData() throws IOException {

        // Map< Start Point, Map<endPoint, Distance to endPoint>>
        final Map<String,Map<String,Integer>> directTripGraphMap=new HashMap<String, Map<String, Integer>>();
        Map<String,Integer> endPointTripMap;

        String line;
        String startPoint;
        String endPoint;
        Integer distance;

        InputStream inputStream = new FileInputStream(ConfigManager.getInstance().getConfig().getInputGraphFilePath());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        while ((line = bufferedReader.readLine()) != null) {
                startPoint= line.substring(0,1);
                endPoint= line.substring(1,2);
                distance= Integer.parseInt(line.substring(2,line.length()));

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
