package uz.ilmnajot.registration.apiResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ApiResponse {

    private String message;

    private boolean success;

    private Object date;

    public ApiResponse(String message, Object date){
        this.message = message;
        this.date = date;
    }
    public ApiResponse(String message, boolean success){
        this.message = message;
        this.success = success;
    }
    public ApiResponse(String message, boolean success, Object date){
        this.message = message;
        this.success = success;
        this.date = date;
    }
    public ApiResponse(boolean success){
        this.success = success;
    }
}
