package com.browseract.agent.demo;

/**
 * Description:
 * Return a paginated list of all tasks, sorted by creation time. 
 * Each task includes basic information such as status and creation time.
 * 
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-agent
 * 
 * curl -X GET 'https://api.browseract.com/v2/agent/list-tasks?agent_id=&page=1&limit=10' -H 'Authorization: Bearer app-abcdefghijklmn'
 */

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class ListTasks {
    
    public static void main(String[] args) {
        // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
        String authorization = "app-abcdefghijklmn";
        
        // Specify the agent ID to filter tasks. 
        // When provided, return only tasks associated with the specified agent. 
        // If omitted, return tasks from all agents.
        // You can copy it from: https://www.browseract.com/reception/agent-list
        String agentId = "";
        
        // Page number (minimum: 1, default: 1)
        int page = 1;
        
        // Number of items per page (minimum: 1, maximum: 500, default: 1)
        int limit = 10;
        
        try {
            // Create HTTP connection
            URL url = new URL("https://api.browseract.com/v2/agent/list-tasks?agent_id=" + agentId + "&page=" + page + "&limit=" + limit);
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
                    
                    // Parse and display task list information
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = mapper.readTree(response.toString());
                    
                    int totalCount = jsonNode.get("total_count").asInt();
                    int currentPage = jsonNode.get("page").asInt();
                    int currentLimit = jsonNode.get("limit").asInt();
                    JsonNode items = jsonNode.get("items");
                    int currentItems = items.size();
                    
                    System.out.println("Total records: " + totalCount + ", Current page records: " + currentItems);
                    System.out.println("Page: " + currentPage + ", Limit: " + currentLimit);
                    
                    // Display task items
                    if (currentItems > 0) {
                        System.out.println("\nTask List:");
                        for (JsonNode item : items) {
                            System.out.println("ID: " + item.get("id").asText());
                            System.out.println("Status: " + item.get("status").asText());
                            System.out.println("Task: " + item.get("task").asText());
                            System.out.println("Agent ID: " + item.get("agent_id").asText());
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
    "id": "16628193563511975",
    "task": "open github,search LLM and save first 10 project name.",
    "output": {
      "string": null,
      "files": null
    },
    "status": "created",
    "live_url_info": {
      "width": 1280,
      "height": 1024,
      "live_url": "https://www.browseract.com/remote/abcde"
    },
    "live_url": "https://www.browseract.com/remote/abcde",
    "profile_id": "abcdef",
    "created_at": null,
    "finished_at": null,
    "task_failure_info": null,
    "agent_id": "15133850277314863"
  }],
  "total_pages": 1,
  "total_count": 1
}
*/
