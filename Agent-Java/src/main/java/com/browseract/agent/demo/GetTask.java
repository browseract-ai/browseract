package com.browseract.agent.demo;

/**
 * Description:
 * Retrieve detailed information about the agent task, including current status, completed steps, 
 * output from finished tasks, and other metadata.
 * 
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-agent
 * 
 * curl -X GET 'https://api.browseract.com/v2/agent/get-task?task_id=16429034742537847' -H 'Authorization: Bearer app-abcdefghijklmn'
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
            URL url = new URL("https://api.browseract.com/v2/agent/get-task?task_id=" + taskId);
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
                    System.out.println("Task: " + jsonNode.get("task").asText());
                    
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
  "task": "open github,search LLM and save first 10 project name.",
  "output": {
    "string": "Task complete! I searched GitHub for 'LLM' and extracted the first 10 project names as requested. The names are saved in results.json, and progress is tracked in todo.md. Please find both files attached for your review.\n\nAttachments:\n\nresults.json:\n{\"repositories\": [\"simonw/llm\", \"rustformers/llm\", \"karpathy/LLM101n\", \"rasbt/LLMs-from-scratch\", \"mlabonne/llm-course\", \"Shubhamsaboo/awesome-llm-apps\", \"Mintplex-Labs/anything-llm\", \"llmware-ai/llmware\", \"karpathy/llm.c\", \"Mooler0410/LLMsPracticalGuide\"]}\n",
    "files": ["Task output file url"]
  },
  "status": "finished",
  "steps": [{
    "id": "184549",
    "step": 1,
    "status": "succeed",
    "evaluation_previous_goal": "Initial step; no previous action to evaluate.",
    "step_goal": "Navigate to https://github.com, search for 'LLM', and extract the first 10 project names from the results page.",
    "screenshots_url": null
  }, {
    "id": "184551",
    "step": 2,
    "status": "succeed",
    "evaluation_previous_goal": "Successfully navigated to the GitHub homepage. Verdict: Success.",
    "step_goal": "Input 'LLM' into the search bar (index 12) and trigger the search to reach the results page.",
    "screenshots_url": "Step screenshot file url"
  }],
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
  "task_gif_url": null,
  "agent_id": "14774755173139766"
}
*/
