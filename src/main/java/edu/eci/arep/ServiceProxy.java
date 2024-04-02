package edu.eci.arep;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static spark.Spark.*;

public class ServiceProxy {

    private static String[] get_URL = null;
    private static final String USER_AGENT = "Mozilla/5.0";
    private static int instancia = 0;

    public static void main(String[] args) {
        get_URL = System.getenv("MATH_SERVICES").split(";");
        staticFiles.location("/public");
        port(getPort());
        get("/lucasseq", (req, res) -> {
            System.out.println(get_URL[instancia] + "/lucasseq?value=" + req.queryParams("value").replace(" ", "%20"));
            URL obj = new URL(get_URL[instancia] + "/lucasseq?value=" + req.queryParams("value").replace(" ", "%20"));
            if (instancia == get_URL.length - 1) instancia = 0;
            else instancia++;
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            StringBuffer response = new StringBuffer();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) response.append(inputLine);
                in.close();
            } else System.out.println("GET request not worked");
            System.out.println(response);
            System.out.println("GET DONE");
            return response.toString();
        });
    }

    /**
     * Obtiene las URLs de los servicios de registro remotos.
     *
     * @param logServices Lista de nombres de los servicios de registro remotos.
     * @return Las URLs de los servicios de registro remotos.
     */
    public static String[] getLogServicesURLS(String[] logServices) {
        String[] logServicesURLS = new String[logServices.length];
        for (int i = 0; i < logServicesURLS.length; i++) {
            logServicesURLS[i] = "http://" + logServices[i] + ":35000/logservice?msg=";
        }
        return logServicesURLS;
    }

    /**
     * Obtiene el puerto en el que se ejecutará el servidor web local.
     *
     * @return El puerto en el que se ejecutará el servidor web local.
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) return Integer.parseInt(System.getenv("PORT"));
        return 4567;
    }
}
