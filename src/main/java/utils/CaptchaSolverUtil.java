package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CaptchaSolverUtil {

    private static final String API_KEY = "YOUR_2CAPTCHA_API_KEY";

    public static String solveCaptcha(String siteKey, String pageUrl) {
        try {
            // Step 1: Submit CAPTCHA
            String requestUrl = "http://2captcha.com/in.php?key=" + API_KEY +
                                "&method=userrecaptcha&googlekey=" + siteKey +
                                "&pageurl=" + pageUrl;

            String submitResponse = sendGet(requestUrl);
            System.out.println("Submit Response: " + submitResponse);

            if (submitResponse == null || !submitResponse.startsWith("OK|")) {
                System.err.println("Captcha submission failed: " + submitResponse);
                return null;
            }

            String[] parts = submitResponse.split("\\|");
            if (parts.length < 2) {
                System.err.println("Unexpected submit response format: " + submitResponse);
                return null;
            }
            String captchaId = parts[1];

            // Step 2: Poll for result
            String fetchUrl = "http://2captcha.com/res.php?key=" + API_KEY +
                              "&action=get&id=" + captchaId;

            for (int i = 0; i < 20; i++) { // poll up to ~200s
                Thread.sleep(10000); // wait 10s before polling
                String response = sendGet(fetchUrl);
                System.out.println("Poll Response [" + i + "]: " + response);

                if (response == null) {
                    continue;
                }

                if (response.startsWith("OK|")) {
                    String[] resParts = response.split("\\|", 2);
                    if (resParts.length > 1) {
                        return resParts[1];
                    }
                } else if ("CAPCHA_NOT_READY".equals(response)) {
                    continue; // keep polling
                } else {
                    System.err.println("Captcha solver error: " + response);
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // timeout or error
    }

    private static String sendGet(String url) throws Exception {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
    }
}
