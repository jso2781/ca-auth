package kr.or.kids.domain.ca.external.core;

public class ExternalApiException extends RuntimeException {
    private static final long serialVersionUID = 7949845611958993494L;

    private final int statusCode;
    private final String responseBody;

    public ExternalApiException(String message, int statusCode, String responseBody, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public ExternalApiException(String message, int statusCode, String responseBody) {
        this(message, statusCode, responseBody, null);
    }

    public int getStatusCode() { return statusCode; }
    public String getResponseBody() { return responseBody; }
}
