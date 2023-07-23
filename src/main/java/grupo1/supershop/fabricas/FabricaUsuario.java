package grupo1.supershop.fabricas;

import grupo1.supershop.controladores.ControladorUsuario;
import grupo1.supershop.interfaces.controladores.IControladorUsuario;

public class FabricaUsuario {

    private FabricaUsuario() {
    }

    public static FabricaUsuario getInstance() {
        return FabricaUsuario.FabricaUsuarioHolder.INSTANCE;
    }

    private static class FabricaUsuarioHolder {
        private static final FabricaUsuario INSTANCE = new FabricaUsuario();
    }

    public static IControladorUsuario getIControladorUsuario() {
        return new ControladorUsuario();
    }
}