package org.example.app.network;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {
    private String status;
    private boolean success;
    private Object data;
}
