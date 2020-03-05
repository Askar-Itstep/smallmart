package smallmart.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role  implements GrantedAuthority {
    USER, MANAGER, EMPLOYEE, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
