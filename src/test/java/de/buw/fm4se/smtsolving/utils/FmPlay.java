package de.buw.fm4se.smtsolving.utils;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;

public class FmPlay {
    public static void main(String[] args) {
        String plink = "https://play.formal-methods.net/?check=SMT&p=ex-3-task-3";
        System.out.println(getCodeFromPermalink(plink));
    }

    /*
     * Returns the code from a permalink of FM playground
     * 
     * @param plink permalink
     * 
     * @return code of if the permalink is valid, otherwise "error"
     */
    public static String getCodeFromPermalink(String plink) {
      String[] parts = plink.split("\\?");
      if (parts.length < 2) {
        return "error";
      }
      String apiurl = parts[0]+"api/permalink/?"+parts[1];
        try {
            // Create URL object
            URL url = new URL(apiurl);

            // Open a connection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // Set the request method
            con.setRequestMethod("GET");

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(content.toString());
            String codeContent = jsonResponse.getString("code");
            return codeContent;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }
}
