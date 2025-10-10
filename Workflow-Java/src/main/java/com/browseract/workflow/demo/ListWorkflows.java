package com.browseract.workflow.demo;

/**
 * Description:
 * Returns a paginated list of all Workflows, sorted by creation time.
 * 
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-workflow
 * 
 * curl -X GET 'https://api.browseract.com/v2/workflow/list-workflows?page=1&limit=10' -H 'Authorization: Bearer app-abcdefghijklmn'
 */

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class ListWorkflows {
    
    public static void main(String[] args) {
        // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
        String authorization = "app-abcdefghijklmn";
        
        // Page number (minimum: 1, default: 1)
        int page = 1;
        
        // Number of items per page (minimum: 1, maximum: 500, default: 1)
        int limit = 10;
        
        try {
            // Create HTTP connection
            URL url = new URL("https://api.browseract.com/v2/workflow/list-workflows?page=" + page + "&limit=" + limit);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Set request method and headers
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + authorization);
            
            // Get response
            int responseCode = connection.getResponseCode();
            
            if (responseCode == 200) {
                // success example:
                // {'page': 1, 'limit': 10, 'items': [], 'total_pages': 0, 'total_count': 0}
                // success example with data: Please refer to the bottom of this file
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    
                    System.out.println("api-call-ok: " + response.toString());
                    
                    // Parse and display workflow list information
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = mapper.readTree(response.toString());
                    
                    int totalCount = jsonNode.get("total_count").asInt();
                    int currentPage = jsonNode.get("page").asInt();
                    int currentLimit = jsonNode.get("limit").asInt();
                    JsonNode items = jsonNode.get("items");
                    int currentItems = items.size();
                    
                    System.out.println("Total workflows: " + totalCount + ", Current page workflows: " + currentItems);
                    System.out.println("Page: " + currentPage + ", Limit: " + currentLimit);
                    
                    // Display workflow items
                    if (currentItems > 0) {
                        System.out.println("\nWorkflow List:");
                        for (JsonNode item : items) {
                            System.out.println("ID: " + item.get("id").asText());
                            System.out.println("Name: " + item.get("name").asText());
                            System.out.println("Description: " + item.get("description").asText());
                            System.out.println("Created At: " + item.get("create_at").asText());
                            System.out.println("Published At: " + item.get("publish_at").asText());
                            System.out.println("---");
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
  "page": 1,
  "limit": 10,
  "items": [{
    "id": "16217357109956214",
    "name": "test demo",
    "description": "This is a test demo",
    "create_at": "2025-10-07T06:15:24Z",
    "publish_at": "2025-10-07T06:27:37Z"
  }],
  "total_pages": 1,
  "total_count": 1
}
*/
