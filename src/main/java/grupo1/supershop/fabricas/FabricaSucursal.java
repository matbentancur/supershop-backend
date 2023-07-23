package grupo1.supershop.fabricas;

import grupo1.supershop.controladores.ControladorSucursal;
import grupo1.supershop.interfaces.controladores.IControladorSucursal;

public class FabricaSucursal {
    
    private FabricaSucursal() {
    }
    
    public static FabricaSucursal getInstance() {
        return FabricaSucursalHolder.INSTANCE;
    }
    
    private static class FabricaSucursalHolder {
        private static final FabricaSucursal INSTANCE = new FabricaSucursal();
    }
    
    public static IControladorSucursal getIControladorSucursal() {
            return new ControladorSucursal();
    }
}
