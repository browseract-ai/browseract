package com.browseract.workflow.demo;

/**
 * Description:
 * Retrieve detailed information about the workflow task, including current status, completed steps, 
 * output from finished tasks, and other metadata.
 * 
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-workflow
 * 
 * curl -X GET 'https://api.browseract.com/v2/workflow/get-task?task_id=16429034742537847' -H 'Authorization: Bearer app-abcdefghijklmn'
 */

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class GetTask {
    
    public static void main(String[] args) {
        // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
        String authorization = "app-abcdefghijklmn";
        
        // The task ID, returned by endpoints like /run-task.
        // Please refer to "RunTask.java"
        long taskId = 16429034742537847L;
        
        try {
            // Create HTTP connection
            URL url = new URL("https://api.browseract.com/v2/workflow/get-task?task_id=" + taskId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Set request method and headers
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + authorization);
            
            // Get response
            int responseCode = connection.getResponseCode();
            
            if (responseCode == 200) {
                // success example: Please refer to the bottom of this file
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    
                    System.out.println("api-call-ok: " + response.toString());
                    
                    // Parse and display task information
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = mapper.readTree(response.toString());
                    
                    System.out.println("\nTask Details:");
                    System.out.println("ID: " + jsonNode.get("id").asText());
                    System.out.println("Status: " + jsonNode.get("status").asText());
                    System.out.println("Workflow ID: " + jsonNode.get("workflow_id").asText());
                    
                    if (jsonNode.has("output") && !jsonNode.get("output").isNull()) {
                        JsonNode output = jsonNode.get("output");
                        if (output.has("string") && !output.get("string").isNull()) {
                            System.out.println("Output: " + output.get("string").asText());
                        }
                    }
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

/*
success example:
{
  "id": "16429034742537847",
  "workflow_id": "16217357109956214",
  "input_parameters": [
    {
      "name": "target_url",
      "value": "https://www.google.com/search?q=iphone17"
    },
    {
      "name": "product_limit",
      "value": "10"
    }
  ],
  "output": {
    "string": "Workflow completed successfully! Extracted 10 products from the search results.",
    "files": ["Workflow output file url"]
  },
  "status": "finished",
  "steps": [
    {
      "id": "184549",
      "step": 1,
      "status": "succeed",
      "step_goal": "Navigate to the target URL and load the page",
      "screenshots_url": "Step screenshot file url"
    },
    {
      "id": "184551",
      "step": 2,
      "status": "succeed",
      "step_goal": "Extract product information from the page",
      "screenshots_url": "Step screenshot file url"
    }
  ],
  "live_url_info": {
    "width": 1280,
    "height": 1024,
    "live_url": "https://www.browseract.com/remote/aaaaa"
  },
  "live_url": "https://www.browseract.com/remote/aaaaa",
  "profile_id": "abcde",
  "created_at": "2025-10-09T08:08:35Z",
  "finished_at": "2025-10-09T08:09:55Z",
  "task_failure_info": null,
  "task_gif_url": null
}
*/
