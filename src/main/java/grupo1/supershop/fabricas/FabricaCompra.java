package grupo1.supershop.fabricas;

import grupo1.supershop.controladores.ControladorCompra;
import grupo1.supershop.interfaces.controladores.IControladorCompra;

public class FabricaCompra {
    
    private FabricaCompra() {
    }
    
    public static FabricaCompra getInstance() {
        return FabricaCompraHolder.INSTANCE;
    }
    
    private static class FabricaCompraHolder {
        private static final FabricaCompra INSTANCE = new FabricaCompra();
    }
    
    public static IControladorCompra getIControladorCompra() {
            return new ControladorCompra();
    }
}
