/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.core;

/**
 *
 * @author bbelt
 */
public class Response<T> {
    private final StatusCode statusCode;
    private final String message;
    private final T data;

    public Response(StatusCode statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public StatusCode getStatusCode() { 
        return statusCode; 
    }
    
    public String getMessage() { 
        return message; 
    }
    public T getData() { 
        return data; 
    }
}
