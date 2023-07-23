package grupo1.supershop.interfaces.controladores;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtProducto;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface IControladorProducto {

    public DtProducto obtenerProducto(Long productoId);
    
    public DtProducto obtenerProductoNombre(String productoNombre, String secreto);

    public DtProducto obtenerProductoBarcode(String productoBarcode);

    public List<DtProducto> listarProductos();
    
    public List<DtProducto> listarProductosDisponibles(Long sucursalId);

    public List<DtProducto> listarProductosBorrados(String secreto);

    public List<DtProducto> listarProductosCategoria(Long categoriaId);
    
    public List<DtActividad> listarActividadProducto(Long productoId, String secreto);

    public List<DtProducto> buscarProducto(String texto);

    public DtMensajeSistema crearProducto(DtProducto dtProducto, String secreto);

    public DtMensajeSistema modificarProducto(DtProducto dtProducto, String secreto);

    public DtMensajeSistema borrarProducto(Long productoId, String secreto);

    public DtMensajeSistema restaurarProducto(Long productoId, String secreto);

    public DtMensajeSistema agregarImagen(Long productoId, MultipartFile imagen, String secreto);

    public DtMensajeSistema quitarImagen(Long productoId, Long archivoId, String secreto);

    public DtMensajeSistema agregarImagenRemota(Long productoId, String url, String secreto);

    public DtMensajeSistema quitarImagenRemota(Long productoId, String url, String secreto);

    public DtMensajeSistema agregarImagenCloudinary(Long productoId, MultipartFile imagen, String secreto);

    public DtMensajeSistema quitarImagenCloudinary(Long productoId, Long archivoId, String secreto);

}
