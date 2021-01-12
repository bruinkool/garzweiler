package land.bruinkool.garzweiler.security;

import land.bruinkool.garzweiler.entity.User;

public interface Authenticatable {
    User getUser();
}