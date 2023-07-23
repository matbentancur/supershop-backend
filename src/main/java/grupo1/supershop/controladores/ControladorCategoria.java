package grupo1.supershop.controladores;

import grupo1.supershop.beans.Archivo;
import grupo1.supershop.beans.Categoria;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtCategoria;
import grupo1.supershop.interfaces.controladores.IControladorCategoria;
import grupo1.supershop.persistencia.manejadores.ManejadorArchivo;
import grupo1.supershop.persistencia.manejadores.ManejadorCategoria;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioArchivo;
import grupo1.supershop.servicios.ServicioCloudinary;
import grupo1.supershop.servicios.ServicioDataType;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import grupo1.supershop.servicios.ServicioPermiso;
import grupo1.supershop.servicios.ServicioSesion;
import grupo1.supershop.validadores.ValidadorCategoria;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.multipart.MultipartFile;

public class ControladorCategoria implements IControladorCategoria {

    @Override
    public DtCategoria obtenerCategoria(Long categoriaId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        Categoria categoria = ManejadorCategoria.getInstance().obtenerCategoria(categoriaId);
        if (categoria == null) {
            return null;
        }
        return (DtCategoria) categoria.getDataType();
    }

    @Override
    public DtCategoria obtenerCategoriaNombre(String categoriaNombre, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        Categoria categoria = ManejadorCategoria.getInstance().obtenerCategoria("nombre", categoriaNombre);
        if (categoria == null) {
            return null;
        }
        return (DtCategoria) categoria.getDataType();
    }

    @Override
    public List<DtCategoria> listarCategorias() {
        List<Categoria> lista = ManejadorCategoria.getInstance().listarCategorias("borrado", false);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtCategoria> listarCategoriasBorradas(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        List<Categoria> lista = ManejadorCategoria.getInstance().listarCategorias("borrado", true);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtActividad> listarActividadCategoria(Long categoriaId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        //OBTIENE CATEGORIA
        Categoria categoria = ManejadorCategoria.getInstance().obtenerCategoria(categoriaId);
        if (categoria == null) {
            return null;
        }

        return ServicioDataType.getInstance().beanSetToDataTypeList(categoria.getActividades());
    }

    @Override
    public DtMensajeSistema crearCategoria(DtCategoria dtCategoria, String secreto) {
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

            //OBTIENE CATEGORIA
            Categoria categoria = new Categoria(dtCategoria);

            //VALIDA CATEGORIA
            DtMensajeSistema dtMensajeSistema = ValidadorCategoria.getInstance().validarCrear(categoria, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //CREA CATEGORIA
            ManejadorCategoria.getInstance().crearCategoria(categoria);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarCreacion(categoria, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Categoria.create.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.create.error");
    }

    @Override
    public DtMensajeSistema modificarCategoria(DtCategoria dtCategoria, String secreto) {
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

            //OBTIENE CATEGORIA
            Categoria categoria = ManejadorCategoria.getInstance().obtenerCategoria(dtCategoria.getId());
            if (categoria == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.notexists.error");
            }

            categoria.modificar(dtCategoria);

            //VALIDA CATEGORIA
            DtMensajeSistema dtMensajeSistema = ValidadorCategoria.getInstance().validarModificar(categoria, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA CATEGORIA
            ManejadorCategoria.getInstance().modificarCategoria(categoria);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(categoria, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Categoria.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.update.error");
    }

    @Override
    public DtMensajeSistema borrarCategoria(Long categoriaId, String secreto) {
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

            //OBTIENE CATEGORIA
            Categoria categoria = ManejadorCategoria.getInstance().obtenerCategoria(categoriaId);
            if (categoria == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.notexists.error");
            }

            //VALIDA CATEGORIA
            DtMensajeSistema dtMensajeSistema = ValidadorCategoria.getInstance().validarBorrar(categoria, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //BORRA CATEGORIA
            ManejadorCategoria.getInstance().borrarCategoria(categoria);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarEliminacion(categoria, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Categoria.delete.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.delete.error");
    }

    @Override
    public DtMensajeSistema restaurarCategoria(Long categoriaId, String secreto) {
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

            //DEBERIA VERIFICAR EL PERMISO DEL USUARIO
            //OBTIENE CATEGORIA
            Categoria categoria = ManejadorCategoria.getInstance().obtenerCategoria(categoriaId);
            if (categoria == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.notexists.error");
            }

            //VALIDA CATEGORIA
            DtMensajeSistema dtMensajeSistema = ValidadorCategoria.getInstance().validarRestaurar(categoria, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //RESTAURA CATEGORIA
            ManejadorCategoria.getInstance().restaurarCategoria(categoria);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarRestauracion(categoria, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Categoria.restore.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.restore.error");
    }

    @Override
    public DtMensajeSistema agregarImagen(Long categoriaId, MultipartFile imagen, String secreto) {
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

            //OBTIENE CATEGORIA
            Categoria categoria = ManejadorCategoria.getInstance().obtenerCategoria(categoriaId);
            if (categoria == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorCategoria.getInstance().validarAgregarImagen(categoria, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //GENERA NOMBRE CON EXTENSION
            String archivoNombre = categoria.generarNombreImagen();
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

            //MODIFICA CATEGORIA
            categoria.setImagen(archivo);
            ManejadorCategoria.getInstance().modificarCategoria(categoria);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(categoria, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Categoria.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.update.error");
        }
    }

    @Override
    public DtMensajeSistema quitarImagen(Long categoriaId, String secreto) {
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

            //OBTIENE CATEGORIA
            Categoria categoria = ManejadorCategoria.getInstance().obtenerCategoria(categoriaId);
            if (categoria == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorCategoria.getInstance().validarQuitarImagen(categoria, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //BORRA EN SISTEMA DE ARCHIVOS
            dtMensajeSistema = ServicioArchivo.getInstance().borrarArchivo(categoria.getImagen().getId());
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            Archivo archivo = ManejadorArchivo.getInstance().obtenerArchivo(categoria.getImagen().getId());

            //MODIFICA CATEGORIA
            categoria.setImagen(null);
            ManejadorCategoria.getInstance().modificarCategoria(categoria);

            //BORRA ARCHIVO
            ManejadorArchivo.getInstance().borrarArchivo(archivo);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(categoria, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Categoria.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.update.error");
        }
    }

    @Override
    public DtMensajeSistema agregarImagenRemota(Long categoriaId, String url, String secreto) {
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

            //OBTIENE CATEGORIA
            Categoria categoria = ManejadorCategoria.getInstance().obtenerCategoria(categoriaId);
            if (categoria == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorCategoria.getInstance().validarAgregarImagen(categoria, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA CATEGORIA
            categoria.setImagenRemota(url);
            ManejadorCategoria.getInstance().modificarCategoria(categoria);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(categoria, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Categoria.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.update.error");
        }
    }

    @Override
    public DtMensajeSistema quitarImagenRemota(Long categoriaId, String url, String secreto) {
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

            //OBTIENE CATEGORIA
            Categoria categoria = ManejadorCategoria.getInstance().obtenerCategoria(categoriaId);
            if (categoria == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorCategoria.getInstance().validarQuitarImagenRemota(categoria, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA CATEGORIA
            categoria.setImagenRemota(null);
            ManejadorCategoria.getInstance().modificarCategoria(categoria);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(categoria, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Categoria.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.update.error");
        }
    }

    @Override
    public DtMensajeSistema agregarImagenCloudinary(Long categoriaId, MultipartFile imagen, String secreto) {
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

            //OBTIENE CATEGORIA
            Categoria categoria = ManejadorCategoria.getInstance().obtenerCategoria(categoriaId);
            if (categoria == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorCategoria.getInstance().validarAgregarImagen(categoria, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //GENERA NOMBRE CON EXTENSION
            String archivoNombre = categoria.generarNombreImagen();
            archivoNombre = archivoNombre + ServicioArchivo.getInstance().agregarExtension(imagen.getContentType());

            //SUBE ARCHIVO A CLOUDINARY
            Archivo archivo = ServicioCloudinary.getInstance().subirArchivo(imagen, archivoNombre);
            if (archivo == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.update.error");
            }

            //CREA ARCHIVO
            ManejadorArchivo.getInstance().crearArchivo(archivo);

            //MODIFICA CATEGORIA
            categoria.setImagen(archivo);
            ManejadorCategoria.getInstance().modificarCategoria(categoria);

            //BORRA ARCHIVO TEMPORAL
            ServicioArchivo.getInstance().borrarArchivoTemporal(archivoNombre);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(categoria, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Categoria.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.update.error");
        }
    }

    @Override
    public DtMensajeSistema quitarImagenCloudinary(Long categoriaId, String secreto) {
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

            //OBTIENE CATEGORIA
            Categoria categoria = ManejadorCategoria.getInstance().obtenerCategoria(categoriaId);
            if (categoria == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorCategoria.getInstance().validarQuitarImagen(categoria, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //BORRAR ARCHIVO EN CLOUDINARY
            Archivo archivo = ManejadorArchivo.getInstance().obtenerArchivo(categoria.getImagen().getId());
            if (!ServicioCloudinary.getInstance().eliminarArchivo(archivo)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.update.error");
            }

            //BORRA EN SISTEMA DE ARCHIVOS
            dtMensajeSistema = ServicioArchivo.getInstance().borrarArchivo(categoria.getImagen().getId());
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA CATEGORIA
            categoria.setImagen(null);
            ManejadorCategoria.getInstance().modificarCategoria(categoria);

            //BORRA ARCHIVO
            ManejadorArchivo.getInstance().borrarArchivo(archivo);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(categoria, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Categoria.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Categoria.update.error");
        }
    }

}
