package BusinessTier.Exceptions;

import java.text.MessageFormat;


public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 8012755528143879802L;

    public SystemException() {
    }

    public SystemException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SystemException(final String message) {
        super(message);
    }

    public SystemException(final Throwable cause) {
        super(cause);
    }

    public SystemException(final MessageFormat mf, final String... args) {
        super(mf.format(args));
    }

    public SystemException(final Throwable ex, final MessageFormat mf, final String... args) {
        super(mf.format(args), ex);
    }
}