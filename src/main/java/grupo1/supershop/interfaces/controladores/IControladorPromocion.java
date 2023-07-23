package grupo1.supershop.interfaces.controladores;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtPromocion;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface IControladorPromocion {

    public DtPromocion obtenerPromocion(Long promocionId, String secreto);
    
    public DtPromocion obtenerPromocionNombre(String promocionNombre, String secreto);

    public List<DtPromocion> listarPromociones(Long sucursalId, String secreto);

    public List<DtPromocion> listarPromocionesBorradas(Long sucursalId, String secreto);

    public List<DtPromocion> listarPromocionesVigentes(Long sucursalId);

    public List<DtPromocion> listarPromocionesExpiradas(Long sucursalId, String secreto);

    public List<DtPromocion> listarPromocionesProximas(Long sucursalId);
    
    public List<DtActividad> listarActividadPromocion(Long promocionId, String secreto);

    public DtMensajeSistema crearPromocion(DtPromocion dtPromocion, Long productoId, Long sucursalId, String secreto);

    public DtMensajeSistema modificarPromocion(DtPromocion dtPromocion, String secreto);

    public DtMensajeSistema borrarPromocion(Long promocionId, String secreto);

    public DtMensajeSistema restaurarPromocion(Long promocionId, String secreto);

    public DtMensajeSistema agregarImagen(Long promocionId, MultipartFile imagen, String secreto);

    public DtMensajeSistema quitarImagen(Long promocionId, String secreto);

    public DtMensajeSistema agregarImagenRemota(Long promocionId, String url, String secreto);

    public DtMensajeSistema quitarImagenRemota(Long promocionId, String url, String secreto);

    public DtMensajeSistema agregarImagenCloudinary(Long promocionId, MultipartFile imagen, String secreto);

    public DtMensajeSistema quitarImagenCloudinary(Long promocionId, String secreto);

}
