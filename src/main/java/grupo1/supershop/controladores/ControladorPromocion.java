package grupo1.supershop.controladores;

import grupo1.supershop.beans.Archivo;
import grupo1.supershop.beans.Producto;
import grupo1.supershop.beans.Promocion;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtPromocion;
import grupo1.supershop.interfaces.controladores.IControladorPromocion;
import grupo1.supershop.persistencia.manejadores.ManejadorArchivo;
import grupo1.supershop.persistencia.manejadores.ManejadorProducto;
import grupo1.supershop.persistencia.manejadores.ManejadorPromocion;
import grupo1.supershop.persistencia.manejadores.ManejadorSucursal;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioArchivo;
import grupo1.supershop.servicios.ServicioCloudinary;
import grupo1.supershop.servicios.ServicioDataType;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import grupo1.supershop.servicios.ServicioPermiso;
import grupo1.supershop.servicios.ServicioSesion;
import grupo1.supershop.validadores.ValidadorPromocion;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.multipart.MultipartFile;

public class ControladorPromocion implements IControladorPromocion {

    @Override
    public DtPromocion obtenerPromocion(Long promocionId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSucursal(usuario)) {
            return null;
        }

        Promocion promocion = ManejadorPromocion.getInstance().obtenerPromocion(promocionId);
        if (promocion == null) {
            return null;
        }
        return (DtPromocion) promocion.getDataType();
    }

    @Override
    public DtPromocion obtenerPromocionNombre(String promocionNombre, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSucursal(usuario)) {
            return null;
        }

        Promocion promocion = ManejadorPromocion.getInstance().obtenerPromocion("nombre", promocionNombre);
        if (promocion == null) {
            return null;
        }
        return (DtPromocion) promocion.getDataType();
    }

    @Override
    public List<DtPromocion> listarPromociones(Long sucursalId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSucursal(usuario)) {
            return null;
        }

        List<Promocion> lista = ManejadorPromocion.getInstance().listarPromociones("borrado", false, "sucursal.id", sucursalId);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtPromocion> listarPromocionesBorradas(Long sucursalId, String secreto) {
        //DEBERIA VERIFICAR LA SESION

        //DEBERIA VERIFICAR EL PERMISO DEL USUARIO
        List<Promocion> lista = ManejadorPromocion.getInstance().listarPromociones("borrado", true, "sucursal.id", sucursalId);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtPromocion> listarPromocionesExpiradas(Long sucursalId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSucursal(usuario)) {
            return null;
        }

        List<Promocion> lista = ManejadorPromocion.getInstance().listarPromocionesExpiradas(sucursalId);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtPromocion> listarPromocionesProximas(Long sucursalId) {
        List<Promocion> lista = ManejadorPromocion.getInstance().listarPromocionesProximas(sucursalId);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtPromocion> listarPromocionesVigentes(Long sucursalId) {
        List<Promocion> lista = ManejadorPromocion.getInstance().listarPromocionesVigentes(sucursalId);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtActividad> listarActividadPromocion(Long promocionId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradores(usuario)) {
            return null;
        }

        //OBTIENE PROMOCION
        Promocion promocion = ManejadorPromocion.getInstance().obtenerPromocion(promocionId);
        if (promocion == null) {
            return null;
        }

        return ServicioDataType.getInstance().beanSetToDataTypeList(promocion.getActividades());
    }

    @Override
    public DtMensajeSistema crearPromocion(DtPromocion dtPromocion, Long productoId, Long sucursalId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return null;
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSucursal(usuario)) {
                return null;
            }

            //OBTIENE SUCURSAL
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(sucursalId);
            if (sucursal == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.notexists.error");
            }

            //OBTIENE PRODUCTO
            Producto producto = ManejadorProducto.getInstance().obtenerProducto(productoId);
            if (producto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            //OBTIENE PROMOCION
            Promocion promocion = new Promocion(dtPromocion);
            promocion.setProducto(producto);
            promocion.setSucursal(sucursal);

            //VALIDA PROMOCION
            DtMensajeSistema dtMensajeSistema = ValidadorPromocion.getInstance().validarCrear(promocion, producto, sucursal, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //CREA PROMOCION
            ManejadorPromocion.getInstance().crearPromocion(promocion);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarCreacion(promocion, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Promocion.create.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorPromocion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.create.error");
    }

    @Override
    public DtMensajeSistema modificarPromocion(DtPromocion dtPromocion, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return null;
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSucursal(usuario)) {
                return null;
            }

            //OBTIENE PROMOCION
            Promocion promocion = ManejadorPromocion.getInstance().obtenerPromocion(dtPromocion.getId());
            if (promocion == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.notexists.error");
            }

            promocion.modificar(dtPromocion);

            //VALIDA PROMOCION
            DtMensajeSistema dtMensajeSistema = ValidadorPromocion.getInstance().validarModificar(promocion, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA PROMOCION
            ManejadorPromocion.getInstance().modificarPromocion(promocion);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(promocion, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Promocion.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorMetodoEnvio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.update.error");

    }

    @Override
    public DtMensajeSistema borrarPromocion(Long promocionId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return null;
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSucursal(usuario)) {
                return null;
            }

            //OBTIENE PROMOCION
            Promocion promocion = ManejadorPromocion.getInstance().obtenerPromocion(promocionId);
            if (promocion == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.notexists.error");
            }

            //VALIDA PROMOCION
            DtMensajeSistema dtMensajeSistema = ValidadorPromocion.getInstance().validarBorrar(promocion, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //BORRA PROMOCION
            ManejadorPromocion.getInstance().borrarPromocion(promocion);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarEliminacion(promocion, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Promocion.delete.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorMetodoEnvio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.delete.error");

    }

    @Override
    public DtMensajeSistema restaurarPromocion(Long promocionId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return null;
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSucursal(usuario)) {
                return null;
            }

            //OBTIENE PROMOCION
            Promocion promocion = ManejadorPromocion.getInstance().obtenerPromocion(promocionId);
            if (promocion == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.notexists.error");
            }

            //VALIDA PROMOCION
            DtMensajeSistema dtMensajeSistema = ValidadorPromocion.getInstance().validarRestaurar(promocion, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //RESTAURA PROMOCION
            ManejadorPromocion.getInstance().restaurarPromocion(promocion);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarRestauracion(promocion, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Promocion.restore.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorMetodoEnvio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.restore.error");

    }

    @Override
    public DtMensajeSistema agregarImagen(Long promocionId, MultipartFile imagen, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return null;
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSucursal(usuario)) {
                return null;
            }

            //OBTIENE PROMOCION
            Promocion promocion = ManejadorPromocion.getInstance().obtenerPromocion(promocionId);
            if (promocion == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorPromocion.getInstance().validarAgregarImagen(promocion, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //GENERA NOMBRE CON EXTENSION
            String archivoNombre = promocion.generarNombreImagen();
            archivoNombre = archivoNombre + ServicioArchivo.getInstance().agregarExtension(imagen.getContentType());

            //SUBE ARCHIVO A SISTEMA DE ARCHIVOS EN DIRECTORIO TEMPORAL
            dtMensajeSistema = ServicioArchivo.getInstance().subirArchivoTemporal(imagen, archivoNombre);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            Archivo archivo = new Archivo();
            archivo.setNombre(archivoNombre);

            //OBTIENE METADOTOS DEL ARCHIVO
            archivo = ServicioArchivo.getInstance().obtenerMetadatos(archivo);
            if (archivo == null) {
                ServicioArchivo.getInstance().borrarArchivoTemporal(archivoNombre);
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Archivo.create.error");
            }

            //MUEVE ARCHIVO EN EL SISTEMA DE ARCHIVOS A DIRECTORIO FINAL 
            dtMensajeSistema = ServicioArchivo.getInstance().moverArchivoTemporal(archivo.getNombre());
            if (!dtMensajeSistema.isExitoso()) {
                ServicioArchivo.getInstance().borrarArchivoTemporal(archivoNombre);
                return dtMensajeSistema;
            }

            //CREA ARCHIVO
            ManejadorArchivo.getInstance().crearArchivo(archivo);

            //MODIFICA PROMOCION
            promocion.setImagen(archivo);
            ManejadorPromocion.getInstance().modificarPromocion(promocion);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(promocion, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Promocion.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.update.error");
        }
    }

    @Override
    public DtMensajeSistema quitarImagen(Long promocionId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return null;
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSucursal(usuario)) {
                return null;
            }

            //OBTIENE PROMOCION
            Promocion promocion = ManejadorPromocion.getInstance().obtenerPromocion(promocionId);
            if (promocion == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorPromocion.getInstance().validarQuitarImagen(promocion, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //BORRA EN SISTEMA DE ARCHIVOS
            dtMensajeSistema = ServicioArchivo.getInstance().borrarArchivo(promocion.getImagen().getId());
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            Archivo archivo = ManejadorArchivo.getInstance().obtenerArchivo(promocion.getImagen().getId());

            //MODIFICA PROMOCION
            promocion.setImagen(null);
            ManejadorPromocion.getInstance().modificarPromocion(promocion);

            //BORRA ARCHIVO
            ManejadorArchivo.getInstance().borrarArchivo(archivo);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(promocion, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Promocion.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.update.error");
        }
    }

    @Override
    public DtMensajeSistema agregarImagenRemota(Long promocionId, String url, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return null;
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSucursal(usuario)) {
                return null;
            }

            //OBTIENE PROMOCION
            Promocion promocion = ManejadorPromocion.getInstance().obtenerPromocion(promocionId);
            if (promocion == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorPromocion.getInstance().validarAgregarImagenRemota(promocion, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA PROMOCION
            promocion.setImagenRemota(url);
            ManejadorPromocion.getInstance().modificarPromocion(promocion);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(promocion, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Promocion.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.update.error");
        }
    }

    @Override
    public DtMensajeSistema quitarImagenRemota(Long promocionId, String url, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return null;
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSucursal(usuario)) {
                return null;
            }

            //OBTIENE PROMOCION
            Promocion promocion = ManejadorPromocion.getInstance().obtenerPromocion(promocionId);
            if (promocion == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorPromocion.getInstance().validarQuitarImagenRemota(promocion, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA PROMOCION
            promocion.setImagenRemota(null);
            ManejadorPromocion.getInstance().modificarPromocion(promocion);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(promocion, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Promocion.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.update.error");
        }
    }

    @Override
    public DtMensajeSistema agregarImagenCloudinary(Long promocionId, MultipartFile imagen, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return null;
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSucursal(usuario)) {
                return null;
            }

            //OBTIENE PROMOCION
            Promocion promocion = ManejadorPromocion.getInstance().obtenerPromocion(promocionId);
            if (promocion == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorPromocion.getInstance().validarAgregarImagen(promocion, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //GENERA NOMBRE CON EXTENSION
            String archivoNombre = promocion.generarNombreImagen();
            archivoNombre = archivoNombre + ServicioArchivo.getInstance().agregarExtension(imagen.getContentType());

            //SUBE ARCHIVO A CLOUDINARY
            Archivo archivo = ServicioCloudinary.getInstance().subirArchivo(imagen, archivoNombre);
            if (archivo == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.update.error");
            }

            //CREA ARCHIVO
            ManejadorArchivo.getInstance().crearArchivo(archivo);

            //MODIFICA PROMOCION
            promocion.setImagen(archivo);
            ManejadorPromocion.getInstance().modificarPromocion(promocion);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(promocion, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Promocion.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.update.error");
        }
    }

    @Override
    public DtMensajeSistema quitarImagenCloudinary(Long promocionId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return null;
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSucursal(usuario)) {
                return null;
            }

            //OBTIENE PROMOCION
            Promocion promocion = ManejadorPromocion.getInstance().obtenerPromocion(promocionId);
            if (promocion == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorPromocion.getInstance().validarQuitarImagen(promocion, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //BORRAR ARCHIVO EN CLOUDINARY
            Archivo archivo = ManejadorArchivo.getInstance().obtenerArchivo(promocion.getImagen().getId());
            if (!ServicioCloudinary.getInstance().eliminarArchivo(archivo)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.update.error");
            }

            //BORRA EN SISTEMA DE ARCHIVOS
            dtMensajeSistema = ServicioArchivo.getInstance().borrarArchivo(promocion.getImagen().getId());
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA PROMOCION
            promocion.setImagen(null);
            ManejadorPromocion.getInstance().modificarPromocion(promocion);

            //BORRA ARCHIVO
            ManejadorArchivo.getInstance().borrarArchivo(archivo);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(promocion, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Promocion.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Promocion.update.error");
        }
    }

}
