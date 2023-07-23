package grupo1.supershop.fabricas;

import grupo1.supershop.controladores.ControladorProducto;
import grupo1.supershop.interfaces.controladores.IControladorProducto;

public class FabricaProducto {
    
    private FabricaProducto() {
    }
    
    public static FabricaProducto getInstance() {
        return FabricaProductoHolder.INSTANCE;
    }
    
    private static class FabricaProductoHolder {
        private static final FabricaProducto INSTANCE = new FabricaProducto();
    }
    
    public static IControladorProducto getIControladorProducto() {
            return new ControladorProducto();
    }
}
