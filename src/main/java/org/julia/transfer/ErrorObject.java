package org.julia.transfer;

/**
 * User: Миша
 * Date: 03.11.14
 */

public class ErrorObject  {
    String errorMessage;
    int status;

    public ErrorObject(String errorMessage, int status) {
        this.errorMessage = errorMessage;
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getStatus() {
        return status;
    }
}
