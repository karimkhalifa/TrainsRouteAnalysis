package PresentationTier.util;

public class StringUtil {

    public static String nvl(Object value,String nullReplacedValue)
    {
        return value==null ?nullReplacedValue:value.toString();
    }
}
