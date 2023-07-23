package grupo1.supershop.servicios;

import grupo1.supershop.beans.Compra;
import grupo1.supershop.beans.ServidorCorreo;
import grupo1.supershop.beans.Vale;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.persistencia.manejadores.ManejadorBase;
import grupo1.supershop.utils.SMTPAuthenticator;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;

public class ServicioServidorCorreo {

    private static ServicioServidorCorreo instance = null;

    @Getter
    @Setter
    private Session smtpSesion;

    @Getter
    @Setter
    private String smtpServidor;

    @Getter
    @Setter
    private String smtpPuerto;

    @Getter
    @Setter
    private String smtpSeguridad;

    @Getter
    @Setter
    private String smtpIdentificacion;

    @Getter
    @Setter
    private String smtpUsuario;

    @Getter
    @Setter
    private String smtpPassword;

    @Getter
    @Setter
    private String smtpDesdeCorreo;

    @Getter
    @Setter
    private String smtpDesdeNombre;

    private ServicioServidorCorreo() {
    }

    public static ServicioServidorCorreo getInstance() {
        if (instance == null) {
            instance = new ServicioServidorCorreo();
        }
        return instance;
    }

    private ServidorCorreo obtenerServidorCorreo() {
        ServidorCorreo servidorCorreo = (ServidorCorreo) ManejadorBase.getInstance().obtenerBean(new ServidorCorreo(), "nombre", "defecto");
        return servidorCorreo;
    }

    private void leerServidorCorreo(ServidorCorreo servidorCorreo) {
        servidorCorreo.desencriptarPassword();
        this.setSmtpServidor(servidorCorreo.getServidor());
        this.setSmtpPuerto(servidorCorreo.getPuerto().toString());
        this.setSmtpSeguridad(servidorCorreo.getSeguridad());
        this.setSmtpIdentificacion(servidorCorreo.getIdentificacion());
        this.setSmtpUsuario(servidorCorreo.getUsuario());
        this.setSmtpPassword(servidorCorreo.getPassword());
        this.setSmtpDesdeCorreo(servidorCorreo.getDesdeCorreo());
        this.setSmtpDesdeNombre(servidorCorreo.getDesdeNombre());
    }

    private void iniciarSesionSMTP() {
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.host", this.getSmtpServidor());
        mailProperties.put("mail.smtp.port", this.getSmtpPuerto());
        mailProperties.put("mail.smtp.user", this.getSmtpDesdeCorreo());

        if (this.getSmtpSeguridad().equals("STARTTLS")) {
            mailProperties.put("mail.smtp.starttls.enable", "true");
            mailProperties.put("mail.smtp.ssl.trust", "*");
        }

        if (this.getSmtpSeguridad().equals("SSL_TLS")) {
            mailProperties.put("mail.smtp.ssl.enable", "true");
            mailProperties.put("mail.smtp.ssl.trust", "*");
        }

        if (this.getSmtpIdentificacion().equals("LOGIN")) {
            mailProperties.put("mail.smtp.auth.mechanisms", "LOGIN");
        }
        if (this.getSmtpIdentificacion().equals("PLAIN")) {
            mailProperties.put("mail.smtp.auth.mechanisms", "PLAIN");
        }
        if (this.getSmtpIdentificacion().equals("DIGEST_MD5")) {
            mailProperties.put("mail.smtp.auth.mechanisms", "DIGEST_MD5");
        }
        if (this.getSmtpIdentificacion().equals("NTLM")) {
            mailProperties.put("mail.smtp.auth.mechanisms", "NTLM");
        }

        Authenticator autenticacion = new SMTPAuthenticator(this.getSmtpUsuario(), this.getSmtpPassword());
        this.setSmtpSesion(Session.getInstance(mailProperties, autenticacion));
    }

    public void enviarCorreo(List<String> destinatarios, List<String> destinatariosConCopia, List<String> destinatariosConCopiaOculta, String asunto, String texto, String correoContenidoTipo) throws Exception {
        this.leerServidorCorreo(this.obtenerServidorCorreo());
        this.iniciarSesionSMTP();
        MimeMessage mensaje = new MimeMessage(this.getSmtpSesion());
        mensaje.setSentDate(new Date(System.currentTimeMillis()));
        mensaje.setFrom(new InternetAddress(this.getSmtpDesdeCorreo(), this.getSmtpDesdeNombre()));
        mensaje.setSubject(asunto);

        MimeBodyPart messageBodyPartTexto = new MimeBodyPart();

        if (correoContenidoTipo.equals("TXT")) {
            messageBodyPartTexto.setContent(texto, "text/plain; charset=utf-8");
        }

        if (correoContenidoTipo.equals("HTML")) {
            messageBodyPartTexto.setContent(texto, "text/html; charset=utf-8");
        }

        for (String destinatario : destinatarios) {
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
        }

        if (destinatariosConCopia != null) {
            if (!destinatariosConCopia.isEmpty()) {
                for (String destinatarioConCopia : destinatariosConCopia) {
                    mensaje.addRecipient(Message.RecipientType.CC, new InternetAddress(destinatarioConCopia));
                }
            }
        }

        if (destinatariosConCopiaOculta != null) {
            if (!destinatariosConCopiaOculta.isEmpty()) {
                for (String destinatarioConCopiaOculta : destinatariosConCopiaOculta) {
                    mensaje.addRecipient(Message.RecipientType.BCC, new InternetAddress(destinatarioConCopiaOculta));
                }
            }
        }

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPartTexto);
        mensaje.setContent(multipart);

        mensaje.saveChanges();
        Transport.send(mensaje);
    }

    public void enviarCorreo(String destinatario, String asunto, String texto) throws Exception {
        List<String> destinatarios = new ArrayList<>();
        destinatarios.add(destinatario);
        this.enviarCorreo(destinatarios, null, null, asunto, texto, "HTML");
    }

    public void enviarCorreoPrueba(ServidorCorreo servidorCorreo, String email) throws Exception {
        this.leerServidorCorreo(servidorCorreo);
        this.iniciarSesionSMTP();
        MimeMessage mensaje = new MimeMessage(this.getSmtpSesion());
        mensaje.setSentDate(new Date(System.currentTimeMillis()));
        mensaje.setFrom(new InternetAddress(this.getSmtpDesdeCorreo(), this.getSmtpDesdeNombre()));
        mensaje.setSubject("TEST");

        MimeBodyPart messageBodyPartTexto = new MimeBodyPart();
        messageBodyPartTexto.setContent("TEST", "text/plain; charset=utf-8");

        mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(email));

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPartTexto);
        mensaje.setContent(multipart);

        mensaje.saveChanges();
        Transport.send(mensaje);
    }

    public DtMensajeSistema enviarCorreoNuevoUsuario(String destinatario, String password) {
        try {
            String asunto = "Nuevo usuario creado";
            String texto = "Usuario: " + destinatario + "\nPassword:" + password;
            this.enviarCorreo(destinatario, asunto, texto);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Email.send.success");
        } catch (Exception ex) {
            Logger.getLogger(ServicioServidorCorreo.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Email.send.error");
        }
    }

    public DtMensajeSistema enviarCorreoRecuperacionContraseña(String destinatario, String password) {
        try {
            String asunto = "Recuperación de contraseña";
            String texto = "Usuario: " + destinatario + "\nPassword:" + password;
            this.enviarCorreo(destinatario, asunto, texto);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Email.send.success");
        } catch (Exception ex) {
            Logger.getLogger(ServicioServidorCorreo.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Email.send.error");
        }
    }

    public DtMensajeSistema enviarCorreoCancelacionCompra(String destinatario, Compra compra) throws Exception {
        try {
            String asunto = "Cancelación de compra";
            String texto = "Usted ha cancelado la compra con ID: " + compra.getId()
                    + "\nFecha y hora: " + ServicioFechaHora.getInstance().getStringDateTime();
            this.enviarCorreo(destinatario, asunto, texto);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Email.send.success");
        } catch (Exception ex) {
            Logger.getLogger(ServicioServidorCorreo.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaALERT("Email.send.error");
        }
    }

    public DtMensajeSistema enviarCorreoConfirmacionCompra(String destinatario, Compra compra) throws Exception {
        try {
            String asunto = "Confirmación de compra";
            String texto = "Se ha confirmado la compra con ID: " + compra.getId()
                    + "\nSucursal: " + compra.getSucursal().getNombre()
                    + "\nFecha y hora: " + ServicioFechaHora.getInstance().getStringDateTime();
            this.enviarCorreo(destinatario, asunto, texto);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Email.send.success");
        } catch (Exception ex) {
            Logger.getLogger(ServicioServidorCorreo.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaALERT("Email.send.error");
        }
    }

    public DtMensajeSistema enviarCorreoConclusionCompra(String destinatario, Compra compra) throws Exception {
        try {
            String asunto = "Conclusión de compra";
            String texto = "Se ha concluido la compra con ID: " + compra.getId()
                    + "\nSucursal: " + compra.getSucursal().getNombre()
                    + "\nFecha y hora: " + ServicioFechaHora.getInstance().getStringDateTime();
            this.enviarCorreo(destinatario, asunto, texto);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Email.send.success");
        } catch (Exception ex) {
            Logger.getLogger(ServicioServidorCorreo.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaALERT("Email.send.error");
        }
    }

    public DtMensajeSistema enviarCorreoDevoluciónCompra(String destinatario, Compra compra, Vale vale) throws Exception {
        try {
            String asunto = "Devolución de compra";
            String texto = "Se ha devuelto la compra con ID: " + compra.getId()
                    + "\nSe ha generado el siguiete vale: " + vale.getMonto().toString()
                    + "\nExpiración del vale: " + vale.getExpira().toString()
                    + "\nSucursal: " + compra.getSucursal().getNombre()
                    + "\nFecha y hora: " + ServicioFechaHora.getInstance().getStringDateTime();
            this.enviarCorreo(destinatario, asunto, texto);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Email.send.success");
        } catch (Exception ex) {
            Logger.getLogger(ServicioServidorCorreo.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaALERT("Email.send.error");
        }
    }

    public DtMensajeSistema enviarCorreoFinalizacionCompra(String destinatario, Compra compra) throws Exception {
        try {
            String asunto = "Finalización de compra";
            String texto = "Usted ha finalizado la compra con ID: " + compra.getId()
                    + "\nSucursal: " + compra.getSucursal().getNombre()
                    + "\nFecha y hora: " + ServicioFechaHora.getInstance().getStringDateTime();
            this.enviarCorreo(destinatario, asunto, texto);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Email.send.success");
        } catch (Exception ex) {
            Logger.getLogger(ServicioServidorCorreo.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaALERT("Email.send.error");
        }
    }

}
