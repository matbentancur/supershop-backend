package grupo1.supershop.interfaces.controladores;

import grupo1.supershop.datatypes.DtCarrito;
import grupo1.supershop.datatypes.DtCarritoProducto;
import grupo1.supershop.datatypes.DtMensajeSistema;
import java.util.List;

public interface IControladorCarrito {

    public DtCarrito obtenerCarrito(String secreto);

    public List<DtCarritoProducto> listarCarritoProductos(String secreto);

    public DtMensajeSistema crearCarrito(Long sucursalId, Long productoId, Integer cantidad, String secreto);
    
    public DtMensajeSistema borrarCarrito(String secreto);

    public DtMensajeSistema agregarProducto(Long productoId, Integer cantidad, String secreto);

    public DtMensajeSistema quitarProducto(Long productoId, Integer cantidad, String secreto);
    
    public DtMensajeSistema modificarCantidadProducto(Long productoId, Integer cantidad, String secreto);
    
    public DtMensajeSistema borrarCarritoProducto(Long productoId, String secreto);

}
