package grupo1.supershop.interfaces.controladores;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtCategoria;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface IControladorCategoria {

    public DtCategoria obtenerCategoria(Long categoriaId, String secreto);
    
    public DtCategoria obtenerCategoriaNombre(String categoriaNombre, String secreto);

    public List<DtCategoria> listarCategorias();

    public List<DtCategoria> listarCategoriasBorradas(String secreto);
    
    public List<DtActividad> listarActividadCategoria(Long categoriaId, String secreto);

    public DtMensajeSistema crearCategoria(DtCategoria dtCategoria, String secreto);

    public DtMensajeSistema modificarCategoria(DtCategoria dtCategoria, String secreto);

    public DtMensajeSistema borrarCategoria(Long categoriaId, String secreto);

    public DtMensajeSistema restaurarCategoria(Long categoriaId, String secreto);

    public DtMensajeSistema agregarImagen(Long categoriaId, MultipartFile imagen, String secreto);

    public DtMensajeSistema quitarImagen(Long categoriaId, String secreto);

    public DtMensajeSistema agregarImagenRemota(Long categoriaId, String url, String secreto);

    public DtMensajeSistema quitarImagenRemota(Long categoriaId, String url, String secreto);
    
    public DtMensajeSistema agregarImagenCloudinary(Long categoriaId, MultipartFile imagen, String secreto);

    public DtMensajeSistema quitarImagenCloudinary(Long categoriaId, String secreto);

}
