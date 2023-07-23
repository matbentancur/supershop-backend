package grupo1.supershop.fabricas;

import grupo1.supershop.controladores.ControladorMensaje;
import grupo1.supershop.interfaces.controladores.IControladorMensaje;

public class FabricaMensaje {
    
    private FabricaMensaje() {
    }
    
    public static FabricaMensaje getInstance() {
        return FabricaMensajeHolder.INSTANCE;
    }
    
    private static class FabricaMensajeHolder {
        private static final FabricaMensaje INSTANCE = new FabricaMensaje();
    }
    
    public static IControladorMensaje getIControladorMensaje() {
            return new ControladorMensaje();
    }
}
