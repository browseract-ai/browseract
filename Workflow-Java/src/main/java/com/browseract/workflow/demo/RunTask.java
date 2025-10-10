package com.browseract.workflow.demo;

/**
 * Description:
 * Start a new workflow task and return a task ID for progress tracking.
 * 
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-workflow
 * 
 * curl -X POST 'https://api.browseract.com/v2/workflow/run-task' -H 'Authorization: Bearer app-abcdefghijklmn' -H 'Content-Type: application/json' -d '{"workflow_id": "1234567890","input_parameters": [{"name": "target_url","value": "https://www.google.com/search?q=iphone17"},{"name": "product_limit","value": "10"}],"save_browser_data": true,"profile_id": ""}'
 */

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class RunTask {
    
    public static void main(String[] args) {
        // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
        String authorization = "app-abcdefghijklmn";
        
        // workflow ID, you can copy it from: https://www.browseract.com/reception/workflow-list
        long workflowId = 1234567890L;
        
        try {
            // Create HTTP connection
            URL url = new URL("https://api.browseract.com/v2/workflow/run-task");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Set request method and headers
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + authorization);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            
            // Create request data
            String requestData = String.format(
                "{\n" +
                "  \"workflow_id\": %d,\n" +
                "  \"input_parameters\": [\n" +
                "    {\n" +
                "      \"name\": \"target_url\",\n" +
                "      \"value\": \"https://www.google.com/search?q=iphone17\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"product_limit\",\n" +
                "      \"value\": \"10\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"save_browser_data\": true,\n" +
                "  \"profile_id\": \"\"\n" +
                "}",
                workflowId
            );
            
            // Send request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            // Get response
            int responseCode = connection.getResponseCode();
            
            if (responseCode == 200) {
                // success example:
                // {'id': '12425895140306551', 'profileId': 'abcde'}
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    
                    System.out.println("api-call-ok: " + response.toString());
                    
                    // Parse response to get task_id
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = mapper.readTree(response.toString());
                    String taskId = jsonNode.get("id").asText();
                    System.out.println("Task ID: " + taskId);
                    
                    // Polling the task status until the task is completed or timed out.
                    // Please refer to "GetTask.java" or "GetTaskStatus.java"
                }
            } else {
                // error example:
                // {'code': 401, 'msg': 'Invalid authorization', 'data': None, 'ts': 1759917250113, 'time': '2025-10-08 09:54:10', 'traceId': 'bcdef'}
                // {'code': 10118, 'msg': 'Running tasks number exceeds.', 'data': None, 'ts': 1759917310153, 'time': '2025-10-08 09:55:10', 'traceId': 'cdefg'}
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    System.out.println("api-call-error: status=" + responseCode + " " + response.toString());
                }
            }
            
        } catch (Exception e) {
            System.out.println("run-error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
