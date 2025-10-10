package com.browseract.agent.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpUtil {
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    public static HttpResult postJson(String url, Object requestBody, Map<String, String> headers) {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            
            // Set request method and headers
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            
            // Add custom headers
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            
            // Convert request body to JSON
            String jsonData = objectMapper.writeValueAsString(requestBody);
            
            // Send request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            // Get response
            int responseCode = connection.getResponseCode();
            
            if (responseCode == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    return new HttpResult(true, responseCode, response.toString());
                }
            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    return new HttpResult(false, responseCode, response.toString());
                }
            }
            
        } catch (Exception e) {
            return new HttpResult(false, -1, "Error: " + e.getMessage());
        }
    }
    
    public static HttpResult put(String url, Map<String, String> headers) {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            
            // Set request method and headers
            connection.setRequestMethod("PUT");
            
            // Add custom headers
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            
            // Get response
            int responseCode = connection.getResponseCode();
            
            if (responseCode == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    return new HttpResult(true, responseCode, response.toString());
                }
            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    return new HttpResult(false, responseCode, response.toString());
                }
            }
            
        } catch (Exception e) {
            return new HttpResult(false, -1, "Error: " + e.getMessage());
        }
    }
    
    public static HttpResult get(String url, Map<String, String> headers) {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            
            // Set request method and headers
            connection.setRequestMethod("GET");
            
            // Add custom headers
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            
            // Get response
            int responseCode = connection.getResponseCode();
            
            if (responseCode == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    return new HttpResult(true, responseCode, response.toString());
                }
            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    return new HttpResult(false, responseCode, response.toString());
                }
            }
            
        } catch (Exception e) {
            return new HttpResult(false, -1, "Error: " + e.getMessage());
        }
    }
    
    public static class HttpResult {
        private final boolean success;
        private final int code;
        private final String text;
        
        public HttpResult(boolean success, int code, String text) {
            this.success = success;
            this.code = code;
            this.text = text;
        }
        
        public boolean isSuccess() {
            return success;
        }
        
        public int getCode() {
            return code;
        }
        
        public String getText() {
            return text;
        }
    }
}
