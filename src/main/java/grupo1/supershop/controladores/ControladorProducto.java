package grupo1.supershop.controladores;

import grupo1.supershop.beans.Archivo;
import grupo1.supershop.beans.Producto;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtProducto;
import grupo1.supershop.interfaces.controladores.IControladorProducto;
import grupo1.supershop.persistencia.manejadores.ManejadorArchivo;
import grupo1.supershop.persistencia.manejadores.ManejadorProducto;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioArchivo;
import grupo1.supershop.servicios.ServicioCloudinary;
import grupo1.supershop.servicios.ServicioDataType;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import grupo1.supershop.servicios.ServicioPermiso;
import grupo1.supershop.servicios.ServicioSesion;
import grupo1.supershop.validadores.ValidadorProducto;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.multipart.MultipartFile;

public class ControladorProducto implements IControladorProducto {

    @Override
    public DtProducto obtenerProducto(Long productoId) {
        Producto producto = ManejadorProducto.getInstance().obtenerProducto(productoId);
        if (producto == null) {
            return null;
        }
        return (DtProducto) producto.getDataType();
    }

    @Override
    public DtProducto obtenerProductoNombre(String productoNombre, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        Producto producto = ManejadorProducto.getInstance().obtenerProducto("nombre", productoNombre);
        if (producto == null) {
            return null;
        }
        return (DtProducto) producto.getDataType();
    }

    @Override
    public DtProducto obtenerProductoBarcode(String productoBarcode) {
        Producto producto = ManejadorProducto.getInstance().obtenerProducto("barcode", productoBarcode);
        if (producto == null) {
            return null;
        }
        return (DtProducto) producto.getDataType();
    }

    @Override
    public List<DtProducto> listarProductos() {
        List<Producto> lista = ManejadorProducto.getInstance().listarProductos("borrado", false);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtProducto> listarProductosDisponibles(Long sucursalId) {
        List<Producto> lista = ManejadorProducto.getInstance().listarProductosDisponibles(sucursalId);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtProducto> listarProductosBorrados(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }
        List<Producto> lista = ManejadorProducto.getInstance().listarProductos("borrado", true);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtProducto> listarProductosCategoria(Long categoriaId) {
        List<Producto> lista = ManejadorProducto.getInstance().listarProductos("categoria.id", categoriaId);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtActividad> listarActividadProducto(Long productoId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        //OBTIENE PRODUCTO
        Producto producto = ManejadorProducto.getInstance().obtenerProducto(productoId);
        if (producto == null) {
            return null;
        }

        return ServicioDataType.getInstance().beanSetToDataTypeList(producto.getActividades());
    }

    @Override
    //TODO
    public List<DtProducto> buscarProducto(String texto) {
        List<Producto> lista = ManejadorProducto.getInstance().buscarProducto(texto);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public DtMensajeSistema crearProducto(DtProducto dtProducto, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE PRODUCTO
            Producto producto = new Producto(dtProducto);

            //VALIDA PRODUCTO
            DtMensajeSistema dtMensajeSistema = ValidadorProducto.getInstance().validarCrear(producto, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //CREA PRODUCTO
            ManejadorProducto.getInstance().crearProducto(producto);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarCreacion(producto, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Producto.create.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.create.error");
    }

    @Override
    public DtMensajeSistema modificarProducto(DtProducto dtProducto, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE PRODUCTO
            Producto producto = ManejadorProducto.getInstance().obtenerProducto(dtProducto.getId());
            if (producto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            producto.modificar(dtProducto);

            //VALIDA PRODUCTO
            DtMensajeSistema dtMensajeSistema = ValidadorProducto.getInstance().validarModificar(producto, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA PRODUCTO
            ManejadorProducto.getInstance().modificarProducto(producto);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(producto, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Producto.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.update.error");
    }

    @Override
    public DtMensajeSistema borrarProducto(Long productoId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE PRODUCTO
            Producto producto = ManejadorProducto.getInstance().obtenerProducto(productoId);
            if (producto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            //VALIDA PRODUCTO
            DtMensajeSistema dtMensajeSistema = ValidadorProducto.getInstance().validarBorrar(producto, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //BORRA PRODUCTO
            ManejadorProducto.getInstance().borrarProducto(producto);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarEliminacion(producto, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Producto.delete.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.delete.error");
    }

    @Override
    public DtMensajeSistema restaurarProducto(Long productoId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE PRODUCTO
            Producto producto = ManejadorProducto.getInstance().obtenerProducto(productoId);
            if (producto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            //VALIDA PRODUCTO
            DtMensajeSistema dtMensajeSistema = ValidadorProducto.getInstance().validarRestaurar(producto, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //RESTAURA PRODUCTO
            ManejadorProducto.getInstance().restaurarProducto(producto);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarRestauracion(producto, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Producto.restore.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.restore.error");
    }

    @Override
    public DtMensajeSistema agregarImagen(Long productoId, MultipartFile imagen, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE PRODUCTO
            Producto producto = ManejadorProducto.getInstance().obtenerProducto(productoId);
            if (producto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorProducto.getInstance().validarAgregarImagen(producto, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //GENERA NOMBRE CON EXTENSION
            String archivoNombre = producto.generarNombreImagen();
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

            //MODIFICA PRODUCTO
            producto.agregarImagen(archivo);
            ManejadorProducto.getInstance().modificarProducto(producto);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(producto, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Producto.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.update.error");
        }
    }

    @Override
    public DtMensajeSistema quitarImagen(Long productoId, Long archivoId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE PRODUCTO
            Producto producto = ManejadorProducto.getInstance().obtenerProducto(productoId);
            if (producto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            //OBTIENE ARCHIVO
            Archivo archivo = ManejadorArchivo.getInstance().obtenerArchivo(archivoId);
            if (archivo == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Archivo.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorProducto.getInstance().validarQuitarImagen(producto, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //BORRA EN SISTEMA DE ARCHIVOS
            dtMensajeSistema = ServicioArchivo.getInstance().borrarArchivo(archivo.getId());
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA PRODUCTO
            producto.quitarImagen(archivo);
            ManejadorProducto.getInstance().modificarProducto(producto);

            //BORRA ARCHIVO
            ManejadorArchivo.getInstance().borrarArchivo(archivo);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(producto, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Producto.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.update.error");
        }
    }

    @Override
    public DtMensajeSistema agregarImagenRemota(Long productoId, String url, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE PRODUCTO
            Producto producto = ManejadorProducto.getInstance().obtenerProducto(productoId);
            if (producto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            if (!producto.agregarImagenRemota(url)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.add.remoteImage.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorProducto.getInstance().validarAgregarImagen(producto, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA PRODUCTO
            ManejadorProducto.getInstance().modificarProducto(producto);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(producto, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Producto.add.remoteImage.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.add.remoteImage.error");
        }
    }

    @Override
    public DtMensajeSistema quitarImagenRemota(Long productoId, String url, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE PRODUCTO
            Producto producto = ManejadorProducto.getInstance().obtenerProducto(productoId);
            if (producto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorProducto.getInstance().validarAgregarImagen(producto, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA PRODUCTO
            if (!producto.quitarImagenRemota(url)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.remove.remoteImage.error");
            }

            ManejadorProducto.getInstance().modificarProducto(producto);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(producto, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Producto.remove.remoteImage.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.remove.remoteImage.error");
        }
    }

    @Override
    public DtMensajeSistema agregarImagenCloudinary(Long productoId, MultipartFile imagen, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE PRODUCTO
            Producto producto = ManejadorProducto.getInstance().obtenerProducto(productoId);
            if (producto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorProducto.getInstance().validarAgregarImagen(producto, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //GENERA NOMBRE CON EXTENSION
            String archivoNombre = producto.generarNombreImagen();
            archivoNombre = archivoNombre + ServicioArchivo.getInstance().agregarExtension(imagen.getContentType());

            //SUBE ARCHIVO A CLOUDINARY
            Archivo archivo = ServicioCloudinary.getInstance().subirArchivo(imagen, archivoNombre);
            if (archivo == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.update.error");
            }

            //CREA ARCHIVO
            ManejadorArchivo.getInstance().crearArchivo(archivo);

            //MODIFICA PRODUCTO
            producto.agregarImagen(archivo);
            ManejadorProducto.getInstance().modificarProducto(producto);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(producto, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Producto.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.update.error");
        }
    }

    @Override
    public DtMensajeSistema quitarImagenCloudinary(Long productoId, Long archivoId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE PRODUCTO
            Producto producto = ManejadorProducto.getInstance().obtenerProducto(productoId);
            if (producto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            //OBTIENE ARCHIVO
            Archivo archivo = ManejadorArchivo.getInstance().obtenerArchivo(archivoId);
            if (archivo == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Archivo.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorProducto.getInstance().validarQuitarImagen(producto, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //BORRAR ARCHIVO EN CLOUDINARY
            if (!ServicioCloudinary.getInstance().eliminarArchivo(archivo)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.update.error");
            }

            //BORRA EN SISTEMA DE ARCHIVOS
            dtMensajeSistema = ServicioArchivo.getInstance().borrarArchivo(archivo.getId());
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA PRODUCTO
            producto.quitarImagen(archivo);
            ManejadorProducto.getInstance().modificarProducto(producto);

            //BORRA ARCHIVO
            ManejadorArchivo.getInstance().borrarArchivo(archivo);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(producto, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Producto.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.update.error");
        }
    }

}
