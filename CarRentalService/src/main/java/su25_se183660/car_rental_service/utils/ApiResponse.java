package su25_se183660.car_rental_service.utils;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private int code;

    private String message;

    private T data;
}
