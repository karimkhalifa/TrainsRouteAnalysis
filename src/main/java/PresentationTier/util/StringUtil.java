package PresentationTier.util;

//Class Description: This Class is mainly used for Integer Utilities and special handling and manipulation (e.g. NVL,..)
public class StringUtil {

// Function Description: This function mainly used for handling the null value if exists then replace it with a value
// Input: value to check if it has null &  the other value to use if null found
// Output: new derived value
    public static String nvl(Object value,String nullReplacedValue)
    {
        return value==null ?nullReplacedValue:value.toString();
    }
}
