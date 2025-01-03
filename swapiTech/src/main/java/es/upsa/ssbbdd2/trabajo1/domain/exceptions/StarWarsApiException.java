package es.upsa.ssbbdd2.trabajo1.domain.exceptions;


public class StarWarsApiException extends Exception
{
    public StarWarsApiException() { }

    public StarWarsApiException(String message) {
        super(message);
    }

    public StarWarsApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public StarWarsApiException(Throwable cause) {
        super(cause);
    }

    public StarWarsApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
