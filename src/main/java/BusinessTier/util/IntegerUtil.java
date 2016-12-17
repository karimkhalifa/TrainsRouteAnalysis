package BusinessTier.util;
public class IntegerUtil {

    public static Integer nvl(Integer value,Integer nullReplacedValue)
    {
        return value==null?nullReplacedValue:value;
    }
}
