package land.bruinkool.garzweiler.api.response;

import lombok.Data;

@Data
public class ErrorResponse {
    private int code;

    public ErrorResponse(int code) {
        this.code = code;
    }
}
