package com.browseract.scenarios;

/**
 * Scenario 2: Run Template Task and Wait for Completion ⭐ Recommended for Beginners
 * 
 * Business Need:
 * Start a workflow task using an official template, wait for it to complete, and then get the results.
 * 
 * Use Cases:
 * - Need to synchronously wait for task completion
 * - Need to get the complete output results of the task
 * - Suitable for single task execution
 * - Perfect for users who want to use official templates without creating custom workflows
 * 
 * Usage Steps:
 * 1. Add Jackson dependency to your project (Maven/Gradle):
 *    Maven (pom.xml):
 *    <dependency>
 *        <groupId>com.fasterxml.jackson.core</groupId>
 *        <artifactId>jackson-databind</artifactId>
 *        <version>2.15.2</version>
 *    </dependency>
 * 
 * 2. Modify API_KEY and WORKFLOW_TEMPLATE_ID below
 * 3. Modify input_parameters according to the template requirements
 * 4. Run: mvn exec:java -Dexec.mainClass="com.browseract.scenarios.Scenario2RunTemplateAndWait"
 * 
 * Note: This file is self-contained and can be copied directly into your IDE.
 *       All HTTP utility methods are included at the bottom of this file.
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scenario2RunTemplateAndWait {
    
    // ============ Configuration Area - Please modify the following variables ============
    // API Key, get from: https://www.browseract.com/reception/integrations
    private static final String API_KEY = System.getenv("BROWSERACT_API_KEY") != null 
            ? System.getenv("BROWSERACT_API_KEY") 
            : "app-abcdefghijklmn";
    
    // Workflow Template ID, you can get it from:
    // - Run: java -cp target/classes com.browseract.workflow.demo.ListOfficialWorkflowTemplates
    // - Or visit: https://www.browseract.com/template?platformType=0
    private static final String WORKFLOW_TEMPLATE_ID = "1234567890";
    
    // Polling configuration
    private static final int POLL_INTERVAL = 5;  // Check task status every 5 seconds
    private static final int MAX_WAIT_TIME = 1800;  // Maximum wait time: 30 minutes (1800 seconds)
    // ================================================
    
    private static final String API_BASE_URL = "https://api.browseract.com/v2/workflow";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("Scenario 2: Run Template Task and Wait for Completion");
        System.out.println("============================================================");
        
        try {
            // Step 1: Start task using template
            System.out.println("\n📤 Step 1: Starting task using template...");
            System.out.println("   Template ID: " + WORKFLOW_TEMPLATE_ID);
            
            // Workflow input parameters (modify according to the template definition)
            List<InputParameter> inputParameters = new ArrayList<>();
            inputParameters.add(new InputParameter("target_url", "https://www.google.com/search?q=iphone17"));
            inputParameters.add(new InputParameter("product_limit", "10"));
            
            String taskId = runTaskByTemplate(WORKFLOW_TEMPLATE_ID, inputParameters);
            
            if (taskId == null) {
                System.out.println("❌ Unable to start task, exiting");
                return;
            }
            
            // Step 2: Wait for task completion
            System.out.println("\n📥 Step 2: Waiting for task completion...");
            String finalStatus = waitForTaskCompletion(taskId);
            
            // Step 3: Get task results
            System.out.println("\n📊 Step 3: Getting task results...");
            String taskInfoJson = getTask(taskId);
            
            if (taskInfoJson != null) {
                System.out.println("\n============================================================");
                System.out.println("Task Result (JSON):");
                System.out.println("============================================================");
                // Output complete task result as formatted JSON
                JsonNode jsonNode = objectMapper.readTree(taskInfoJson);
                System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode));
                System.out.println("============================================================");
            } else {
                System.out.println("⚠️ Unable to get task details");
            }
            
        } catch (Exception e) {
            System.err.println("\n❌ Error occurred:");
            e.printStackTrace();
        }
    }
    
    private static String runTaskByTemplate(String workflowTemplateId, List<InputParameter> inputParameters) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + API_KEY);
        
        RunTaskByTemplateRequest requestBody = new RunTaskByTemplateRequest();
        requestBody.setWorkflow_template_id(workflowTemplateId);
        requestBody.setInput_parameters(inputParameters);
        
        String apiUrl = API_BASE_URL + "/run-task-by-template";
        HttpResult result = postJson(apiUrl, requestBody, headers);
        
        if (result.isSuccess()) {
            try {
                JsonNode jsonNode = objectMapper.readTree(result.getText());
                String taskId = jsonNode.get("id").asText();
                System.out.println("✅ Task started, Task ID: " + taskId);
                if (jsonNode.has("profileId")) {
                    System.out.println("   Profile ID: " + jsonNode.get("profileId").asText());
                }
                return taskId;
            } catch (Exception e) {
                System.out.println("❌ Failed to parse response: " + e.getMessage());
                return null;
            }
        } else {
            System.out.println("❌ Failed to start task: " + result.getText());
            return null;
        }
    }
    
    private static String getTaskStatus(String taskId) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + API_KEY);
        
        String apiUrl = API_BASE_URL + "/get-task-status?task_id=" + taskId;
        HttpResult result = get(apiUrl, headers);
        
        if (result.isSuccess()) {
            try {
                JsonNode jsonNode = objectMapper.readTree(result.getText());
                return jsonNode.get("status").asText();
            } catch (Exception e) {
                return null;
            }
        } else {
            // Network error or API error, will retry in next polling cycle
            return null;
        }
    }
    
    private static String getTask(String taskId) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + API_KEY);
        
        String apiUrl = API_BASE_URL + "/get-task?task_id=" + taskId;
        HttpResult result = get(apiUrl, headers);
        
        if (result.isSuccess()) {
            return result.getText();
        } else {
            System.out.println("⚠️ Network error while getting task details: " + result.getText());
            return null;
        }
    }
    
    private static String waitForTaskCompletion(String taskId) {
        long startTime = System.currentTimeMillis();
        String previousStatus = null;
        
        System.out.println("\n⏳ Waiting for task completion (max wait time: " + (MAX_WAIT_TIME / 60) + " minutes)...");
        
        while (true) {
            // Check if timeout
            long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
            if (elapsedTime > MAX_WAIT_TIME) {
                System.out.println("\n⏰ Wait timeout (waited " + elapsedTime + " seconds)");
                return null;
            }
            
            // Get task status
            String status = getTaskStatus(taskId);
            
            if (status == null) {
                // Network error or API error, continue waiting
                System.out.println();
                System.out.print("   Network error, retrying... (waited " + elapsedTime + " seconds)\r");
            } else if ("finished".equals(status)) {
                System.out.println("\n✅ Task completed!");
                return "finished";
            } else if ("failed".equals(status)) {
                System.out.println("\n❌ Task execution failed");
                return "failed";
            } else if ("canceled".equals(status)) {
                System.out.println("\n🚫 Task canceled");
                return "canceled";
            } else {
                // running, created, paused, etc.
                // If status changed, print on new line; otherwise update same line
                if (!status.equals(previousStatus)) {
                    System.out.println();  // New line when status changes
                    System.out.print("   Status: " + status + " (waited " + elapsedTime + " seconds)\r");
                    previousStatus = status;
                } else {
                    // Same status, update same line
                    System.out.print("   Status: " + status + " (waited " + elapsedTime + " seconds)\r");
                }
            }
            
            // Wait before checking again
            try {
                Thread.sleep(POLL_INTERVAL * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
    }
    
    // Inner classes for request/response
    public static class RunTaskByTemplateRequest {
        private String workflow_template_id;
        private List<InputParameter> input_parameters;
        
        public String getWorkflow_template_id() { return workflow_template_id; }
        public void setWorkflow_template_id(String workflow_template_id) { this.workflow_template_id = workflow_template_id; }
        
        public List<InputParameter> getInput_parameters() { return input_parameters; }
        public void setInput_parameters(List<InputParameter> input_parameters) { this.input_parameters = input_parameters; }
    }
    
    public static class InputParameter {
        private String name;
        private String value;
        
        public InputParameter(String name, String value) {
            this.name = name;
            this.value = value;
        }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
    }
    
    // ========================================================================
    // HTTP Utility Methods (Inline implementation for easy copy-paste)
    // ========================================================================
    
    /**
     * Send POST request with JSON body
     */
    private static HttpResult postJson(String url, Object requestBody, Map<String, String> headers) {
        try {
            URL urlObj = URI.create(url).toURL();
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            
            // Set request method and headers
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            
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
    
    /**
     * Send GET request
     */
    private static HttpResult get(String url, Map<String, String> headers) {
        try {
            URL urlObj = URI.create(url).toURL();
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            
            // Set request method and headers
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            
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
    
    /**
     * HTTP Result wrapper class
     */
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
