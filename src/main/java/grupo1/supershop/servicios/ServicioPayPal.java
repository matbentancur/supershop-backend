package grupo1.supershop.servicios;

import java.util.Base64;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ServicioPayPal {

    private static ServicioPayPal instance = null;

    private ServicioPayPal() {
        clientId = ServicioProperties.getInstance().getAppProperties("paypal.client.id");
        clientSecret = ServicioProperties.getInstance().getAppProperties("paypal.client.secret");
        sandbox = ServicioProperties.getInstance().getAppProperties("paypal.sandbox");
        pathToken = ServicioProperties.getInstance().getAppProperties("paypal.path.token");
        pathCheckoutOrders = ServicioProperties.getInstance().getAppProperties("paypal.path.checkout.orders");
        pathCheckoutCapture = ServicioProperties.getInstance().getAppProperties("paypal.path.checkout.orders.capture");
    }

    public static ServicioPayPal getInstance() {
        if (instance == null) {
            instance = new ServicioPayPal();
        }
        return instance;
    }

    private String clientId;
    private String clientSecret;
    private String sandbox;
    private String pathToken;
    private String pathCheckoutOrders;
    private String pathCheckoutCapture;

    private String getAuth(String client_id, String clientSecret) {
        String auth = client_id + ":" + clientSecret;
        return Base64.getEncoder().encodeToString(auth.getBytes());
    }

    private String generateAccessToken() {
        String auth = this.getAuth(clientId, clientSecret);
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + auth);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        HttpEntity<?> request = new HttpEntity<>(requestBody, headers);
        requestBody.add("grant_type", "client_credentials");

        ResponseEntity<String> response = restTemplate.postForEntity(sandbox + pathToken, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return new JSONObject(response.getBody()).getString("access_token");
        } else {
            return "Unavailable to get ACCESS TOKEN, STATUS CODE " + response.getStatusCode();
        }
    }

    public String createOrder(String amountValue) {
        String accessToken = generateAccessToken();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.setContentType(MediaType.APPLICATION_JSON);

        //JSON String
        String requestJson = "{\"intent\":\"CAPTURE\",\"purchase_units\":[{\"amount\":{\"currency_code\":\"USD\",\"value\":\"" + amountValue + "\"}}]}";
        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                sandbox + pathCheckoutOrders,
                HttpMethod.POST,
                entity,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            return "Unavailable to get CREATE AN ORDER, STATUS CODE " + response.getStatusCode();
        }
    }

    public String capturePaymentOrder(String orderId) {
        String accessToken = generateAccessToken();
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();

        headers.set("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    sandbox + String.format(pathCheckoutCapture, orderId),
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.CREATED) {
                return response.getBody();
            } else {
                return "Unavailable to get CAPTURE ORDER, STATUS CODE " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "ORDER WAITING TO BE APPROVED";
        }

    }

}
