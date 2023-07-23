package grupo1.supershop.datatypes;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public final class DtMensajeSistema {

    @Getter
    @Setter
    private boolean exitoso;

    @Getter
    @Setter
    private boolean erroneo;

    @Getter
    @Setter
    private boolean alerta;

    @Getter
    @Setter
    private boolean informacion;

    @Getter
    @Setter
    private String tipo;

    @Getter
    @Setter
    private List<String> mensajes;

    public DtMensajeSistema(boolean exitoso, boolean erroneo, boolean alerta, boolean informacion, String tipo) {
        this.exitoso = exitoso;
        this.erroneo = erroneo;
        this.alerta = alerta;
        this.informacion = informacion;
        this.tipo = tipo;
    }

    public DtMensajeSistema(boolean exitoso, boolean erroneo, boolean alerta, boolean informacion, String tipo, String mensaje) {
        this.exitoso = exitoso;
        this.erroneo = erroneo;
        this.alerta = alerta;
        this.informacion = informacion;
        this.tipo = tipo;
        this.mensajes = new ArrayList<>();
        this.mensajes.add(mensaje);
    }

    public DtMensajeSistema(boolean exitoso, boolean erroneo, boolean alerta, boolean informacion, String tipo, List<String> mensajes) {
        this.exitoso = exitoso;
        this.erroneo = erroneo;
        this.alerta = alerta;
        this.informacion = informacion;
        this.tipo = tipo;
        this.mensajes = new ArrayList<>();
        this.mensajes = mensajes;
    }

}
