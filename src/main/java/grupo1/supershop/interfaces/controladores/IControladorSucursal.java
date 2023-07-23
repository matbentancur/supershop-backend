package grupo1.supershop.interfaces.controladores;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtSucursal;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface IControladorSucursal {

    public DtSucursal obtenerSucursal(Long sucursalId);
    
    public DtSucursal obtenerSucursalNombre(String sucursalNombre, String secreto);

    public List<DtSucursal> listarSucursales();

    public List<DtSucursal> listarSucursalesBorradas(String secreto);
    
    public List<DtActividad> listarActividadSucursal(Long sucursalId, String secreto);

    public DtMensajeSistema crearSucursal(DtSucursal dtSucursal, String secreto);

    public DtMensajeSistema modificarSucursal(DtSucursal dtSucursal, String secreto);

    public DtMensajeSistema borrarSucursal(Long sucursalId, String secreto);

    public DtMensajeSistema restaurarSucursal(Long sucursalId, String secreto);

    public DtMensajeSistema agregarImagen(Long sucursalId, MultipartFile imagen, String secreto);

    public DtMensajeSistema quitarImagen(Long sucursalId, String secreto);

    public DtMensajeSistema agregarImagenRemota(Long sucursalId, String url, String secreto);

    public DtMensajeSistema quitarImagenRemota(Long sucursalId, String url, String secreto);

    public DtMensajeSistema agregarImagenCloudinary(Long sucursalId, MultipartFile imagen, String secreto);

    public DtMensajeSistema quitarImagenCloudinary(Long sucursalId, String secreto);

}
