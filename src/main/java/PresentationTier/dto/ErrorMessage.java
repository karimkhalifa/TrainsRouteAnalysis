package PresentationTier.dto;

import BusinessTier.Exceptions.SystemException;
import com.google.common.base.Joiner;


import java.text.MessageFormat;

public enum ErrorMessage  {

    NO_SUCH_ROUTE("NO SUCH ROUTE");

    final static String ERROR_FORMAT =  "Enum Value {0} doesn't exist, values available are {1}";
    final String StringValue;

    private ErrorMessage(String name) {
        StringValue = name;
    }
    public String getTextValue() {
        return StringValue;
    }
    public static ErrorMessage getEnumValue(final String StringValue) {
        for (final ErrorMessage value : values()) {
            if (value.StringValue.equals(StringValue)) {
                return value;
            }
        }
        throw new SystemException(MessageFormat.format(ERROR_FORMAT, StringValue, Joiner.on(",").join(values())));
    }
}
