package edu.eci.arep;

import static spark.Spark.*;

public class MathServices {

    public static void main(String[] args) {
        port(getPort());
        get("/lucasseq", (req, res) -> {
            res.type("application/json");
            int value = req.queryMap("value").integerValue();
            StringBuilder response = new StringBuilder("{ \"operation\": \"Secuencia de lucas\", \"input\": " + value + ", \"output\": \"");
            int[] numbers = new int[value+1];
            for (int n = 0; n <= value; n++) {
                if (n==0) numbers[n] = 2;
                else if (n==1) numbers[n] = 1;
                else numbers[n] = numbers[n-1] + numbers[n-2];
                response.append(numbers[n]).append(", ");
            }
            response.setLength(response.length() - 2);
            return response.append("\"}").toString();
        });
    }

    /**
     * Obtiene el puerto en el que se ejecutará el servidor web local.
     *
     * @return El puerto en el que se ejecutará el servidor web local.
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) return Integer.parseInt(System.getenv("PORT"));
        return 4568;
    }
}
