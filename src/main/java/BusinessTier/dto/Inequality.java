package BusinessTier.dto;

import BusinessTier.Exceptions.SystemException;
import com.google.common.base.Joiner;


import java.text.MessageFormat;
//Enum Description: This enum is mainly used for setting the inequality operator using within the code
public enum Inequality  {

    LESS_THAN_OR_EQUAL("less-than-or-equal"),
    EXACT_EQUAL("exactly-equal");
    final static String ERROR_FORMAT =  "Enum Value {0} doesn't exist, values available are {1}";
    final String StringValue;

    private Inequality(String name) {
        StringValue = name;
    }

    public static Inequality getEnumValue(final String StringValue) {
        for (final Inequality value : values()) {
            if (value.StringValue.equals(StringValue)) {
                return value;
            }
        }
        throw new SystemException(MessageFormat.format(ERROR_FORMAT, StringValue, Joiner.on(",").join(values())));
    }
}
