package grupo1.supershop.servicios;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import grupo1.supershop.beans.Compra;
import grupo1.supershop.beans.Vale;
import grupo1.supershop.datatypes.DtMensajePush;
import grupo1.supershop.datatypes.DtMensajeSistema;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.core.io.ClassPathResource;

public class ServicioNotificacionPush {

    private static ServicioNotificacionPush instance = null;
    private final FirebaseMessaging firebaseMessaging;
    private static String imagen = "https://supershop.herokuapp.com/_next/image?url=%2F_next%2Fstatic%2Fmedia%2Flogo.88e60df8.png&w=256&q=75";

    private ServicioNotificacionPush() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource("firebase-service-account.json").getInputStream());
        FirebaseOptions firebaseOptions = FirebaseOptions
                .builder()
                .setCredentials(googleCredentials)
                .build();
        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "my-app");
        firebaseMessaging = FirebaseMessaging.getInstance(app);
    }

    public static ServicioNotificacionPush getInstance() throws IOException {
        if (instance == null) {
            instance = new ServicioNotificacionPush();
        }
        return instance;
    }

    private String sendNotification(DtMensajePush dtMensajePush, String token) throws FirebaseMessagingException {

        Notification notification = Notification
                .builder()
                .setTitle(dtMensajePush.getAsunto())
                .setBody(dtMensajePush.getContenido())
                .build();

        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
//                .putAllData(null)
                .build();

        return firebaseMessaging.send(message);
    }

    private boolean verificarToken(String token) throws FirebaseAuthException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
        if (decodedToken.getUid() != null) {
            return true;
        } else {
            return false;
        }
    }

    public DtMensajeSistema notificar(DtMensajePush dtMensajePush, String token) {
        try {
            this.sendNotification(dtMensajePush, token);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("PushNotification.send.success");
        } catch (FirebaseMessagingException ex) {
            Logger.getLogger(ServicioNotificacionPush.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaALERT("PushNotification.send.error");
        }
    }

    public DtMensajeSistema notificarConfirmacionCompra(String token, Compra compra) {
        try {
            String asunto = "Confirmación de compra";
//            String texto = "Se ha confirmado la compra con ID";
            String texto = "Se ha confirmado la compra con ID: " + compra.getId()
                    + "\nSucursal: " + compra.getSucursal().getNombre()
                    + "\nFecha y hora: " + ServicioFechaHora.getInstance().getStringDateTime();
            DtMensajePush dtMensajePush = new DtMensajePush(asunto, texto);
            this.sendNotification(dtMensajePush, token);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("PushNotification.send.success");
        } catch (FirebaseMessagingException ex) {
            Logger.getLogger(ServicioNotificacionPush.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaALERT("PushNotification.send.error");
        }
    }

    public DtMensajeSistema notificarConclusionCompra(String token, Compra compra) {
        try {
            String asunto = "Conclusión de compra";
//            String texto = "Se ha concluido la compra con ID";
            String texto = "Se ha concluido la compra con ID: " + compra.getId()
                    + "\nSucursal: " + compra.getSucursal().getNombre()
                    + "\nFecha y hora: " + ServicioFechaHora.getInstance().getStringDateTime();
            DtMensajePush dtMensajePush = new DtMensajePush(asunto, texto);
            this.sendNotification(dtMensajePush, token);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("PushNotification.send.success");
        } catch (FirebaseMessagingException ex) {
            Logger.getLogger(ServicioNotificacionPush.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaALERT("PushNotification.send.error");
        }
    }

    public DtMensajeSistema notificarDevoluciónCompra(String token, Compra compra, Vale vale) throws Exception {
        try {
            String asunto = "Devolucion de compra";
//            String texto = "Se ha devuelto la compra con ID";
            String texto = "Se ha devuelto la compra con ID: " + compra.getId()
                    + "\nSe ha generado el siguiete vale: " + vale.getMonto().toString()
                    + "\nExpiración del vale: " + vale.getExpira().toString()
                    + "\nSucursal: " + compra.getSucursal().getNombre()
                    + "\nFecha y hora: " + ServicioFechaHora.getInstance().getStringDateTime();
            DtMensajePush dtMensajePush = new DtMensajePush(asunto, texto);
            this.sendNotification(dtMensajePush, token);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("PushNotification.send.success");
        } catch (FirebaseMessagingException ex) {
            Logger.getLogger(ServicioNotificacionPush.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaALERT("PushNotification.send.error");
        }
    }
}
