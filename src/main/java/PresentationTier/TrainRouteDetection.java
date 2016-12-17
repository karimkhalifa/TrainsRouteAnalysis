package PresentationTier;

import BusinessTier.RouteAnalysis;
import BusinessTier.dto.Inequality;
import PresentationTier.dto.ErrorMessage;
import PresentationTier.util.StringUtil;

import java.io.IOException;

//Class Description: This class is used as presentation interface layer and includes the provided 10 test cases for route
public class TrainRouteDetection {

// Function Description: This function The Main Function while includes the provided 10 test cases for route
    public static void main(String [] args) throws IOException {

        RouteAnalysis routeAnalysis  = new RouteAnalysis();

        //Test #1
        String [] routePoints_1={"A", "B", "C"};
        String output1=StringUtil.nvl(routeAnalysis.getRouteDistance(routePoints_1), ErrorMessage.NO_SUCH_ROUTE.getTextValue());
        System.out.println("Output #1: "+output1);
        //Test #2
        String [] routePoints_2={"A", "D"};
        String output2=StringUtil.nvl(routeAnalysis.getRouteDistance(routePoints_2), ErrorMessage.NO_SUCH_ROUTE.getTextValue());
        System.out.println("Output #2: "+output2);
        //Test #3
        String [] routePoints_3={"A", "D", "C"};
        String output3=StringUtil.nvl(routeAnalysis.getRouteDistance(routePoints_3), ErrorMessage.NO_SUCH_ROUTE.getTextValue());
        System.out.println("Output #3: "+output3);
        //Test #4
        String [] routePoints_4={"A", "E", "B","C","D"};
        String output4=StringUtil.nvl(routeAnalysis.getRouteDistance(routePoints_4), ErrorMessage.NO_SUCH_ROUTE.getTextValue());
        System.out.println("Output #4: "+output4);
        //Test #5
        String [] routePoints_5={"A", "E", "D"};
        String output5=StringUtil.nvl(routeAnalysis.getRouteDistance(routePoints_5), ErrorMessage.NO_SUCH_ROUTE.getTextValue());
        System.out.println("Output #5: "+output5);

        //Test #6
        System.out.println("Output #6: "+ routeAnalysis.getTripCountByStopLimit("C","C",3, Inequality.LESS_THAN_OR_EQUAL));
        //Test #7
        System.out.println("Output #7: "+ routeAnalysis.getTripCountByStopLimit("A","C",4, Inequality.EXACT_EQUAL));

        //Test #8
        System.out.println("Output #8: "+ routeAnalysis.getShortestRouteDistance("A","C"));
        //Test #9
        System.out.println("Output #9: "+ routeAnalysis.getShortestRouteDistance("B","B"));

        //Test #10
        System.out.println("Output #10: "+ routeAnalysis.getTripCountByDistanceLimit("C","C",30));

    }
}
