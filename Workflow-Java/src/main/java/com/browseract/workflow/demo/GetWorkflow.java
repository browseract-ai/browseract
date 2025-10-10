package com.browseract.workflow.demo;

/**
 * Description:
 * Get workflow details, including required input parameters
 * 
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-workflow
 * 
 * curl -X GET 'https://api.browseract.com/v2/workflow/get-workflow?workflow_id=1234567890' -H 'Authorization: Bearer app-abcdefghijklmn'
 */

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class GetWorkflow {
    
    public static void main(String[] args) {
        // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
        String authorization = "app-abcdefghijklmn";
        
        // workflow ID, you can copy it from: https://www.browseract.com/reception/workflow-list
        long workflowId = 16217357109956214L;
        
        try {
            // Create HTTP connection
            URL url = new URL("https://api.browseract.com/v2/workflow/get-workflow?workflow_id=" + workflowId);
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
                    
                    // Parse and display workflow information
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = mapper.readTree(response.toString());
                    
                    System.out.println("\nWorkflow Details:");
                    System.out.println("ID: " + jsonNode.get("id").asText());
                    System.out.println("Name: " + jsonNode.get("name").asText());
                    System.out.println("Description: " + jsonNode.get("description").asText());
                    System.out.println("Created At: " + jsonNode.get("create_at").asText());
                    System.out.println("Published At: " + jsonNode.get("publish_at").asText());
                    
                    if (jsonNode.has("input_parameters")) {
                        JsonNode inputParams = jsonNode.get("input_parameters");
                        System.out.println("\nInput Parameters:");
                        for (JsonNode param : inputParams) {
                            System.out.println("- " + param.get("name").asText() + 
                                             " (default_enabled: " + param.get("default_enabled").asBoolean() + ")");
                        }
                    }
                }
            } else {
                // error example:
                // {'code': 401, 'msg': 'Invalid authorization', 'data': None, 'ts': 1759917250113, 'time': '2025-10-08 09:54:10', 'traceId': 'bcdef'}
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
  "id": "16217357109956214",
  "name": "test demo",
  "description": "This is a test demo",
  "create_at": "2025-10-07T06:15:24Z",
  "publish_at": "2025-10-07T06:27:38Z",
  "input_parameters": [
    {
      "name": "target_url",
      "default_enabled": true
    },
    {
      "name": "product_limit",
      "default_enabled": true
    }
  ]
}
*/
