package land.bruinkool.garzweiler.api.model;

import lombok.Data;

@Data
public class UserModel {
    private Integer id;
    private String emailAddress;
    private String name;

    public UserModel(Integer id, String emailAddress, String name) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.name = name;
    }
}
