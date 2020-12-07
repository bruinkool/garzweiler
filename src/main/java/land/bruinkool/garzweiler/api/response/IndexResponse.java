package land.bruinkool.garzweiler.api.response;

import lombok.Data;

@Data
public class IndexResponse {
    private Boolean running;
    private String host;

    public IndexResponse(Boolean running, String host) {
        this.running = running;
        this.host = host;
    }
}
