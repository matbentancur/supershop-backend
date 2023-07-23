package grupo1.supershop.fabricas;

import grupo1.supershop.controladores.ControladorCarrito;
import grupo1.supershop.interfaces.controladores.IControladorCarrito;

public class FabricaCarrito {
    
    private FabricaCarrito() {
    }
    
    public static FabricaCarrito getInstance() {
        return FabricaCarritoHolder.INSTANCE;
    }
    
    private static class FabricaCarritoHolder {
        private static final FabricaCarrito INSTANCE = new FabricaCarrito();
    }
    
    public static IControladorCarrito getIControladorCarrito() {
            return new ControladorCarrito();
    }
}
