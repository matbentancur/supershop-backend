package grupo1.supershop.datatypes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public final class DtInicioSesion {

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

    public DtInicioSesion(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
