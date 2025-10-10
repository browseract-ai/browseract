package com.browseract.agent.demo;

/**
 * Description:
 * Get the current status of a task. This is a lightweight endpoint that returns only the task status
 * without detailed information. For complete task details, use the /get-task endpoint.
 * 
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-agent
 * 
 * curl -X GET 'https://api.browseract.com/v2/agent/get-task-status?task_id=16429034742537847' -H 'Authorization: Bearer app-abcdefghijklmn'
 */

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class GetTaskStatus {
    
    public static void main(String[] args) {
        // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
        String authorization = "app-abcdefghijklmn";
        
        // The task ID, returned by endpoints like /run-task.
        // Please refer to "RunTask.java"
        long taskId = 16429034742537847L;
        
        try {
            // Create HTTP connection
            URL url = new URL("https://api.browseract.com/v2/agent/get-task-status?task_id=" + taskId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Set request method and headers
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + authorization);
            
            // Get response
            int responseCode = connection.getResponseCode();
            
            if (responseCode == 200) {
                // success example:
                // {'status': 'running'}
                // {'status': 'finished'}
                // {'status': 'failed'}
                // {'status': 'paused'}
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    
                    System.out.println("api-call-ok: " + response.toString());
                    
                    // Parse and display task status
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = mapper.readTree(response.toString());
                    String status = jsonNode.get("status").asText();
                    System.out.println("Task Status: " + status);
                }
            } else {
                // error example:
                // {'code': 401, 'msg': 'Invalid authorization', 'data': None, 'ts': 1759917250113, 'time': '2025-10-08 09:54:10', 'traceId': 'bcdef'}
                // {'code': 10112, 'msg': 'Task is not exist.', 'data': None, 'ts': 1759918566040, 'time': '2025-10-08 10:16:06', 'traceId': 'cdefg'}
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
