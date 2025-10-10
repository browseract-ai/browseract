package com.browseract.agent.demo;

/**
 * Description:
 * Start a new agent task and return a task ID for progress tracking.
 * <p>
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-agent
 * <p>
 * curl -X POST 'https://api.browseract.com/v2/agent/run-task' -H 'Authorization: Bearer app-abcdefghijklmn' -H 'Content-Type: application/json' -d '{"agent_id": "1234567890","task": "open github,search LLM and save first 10 project name.","save_browser_data": true,"profile_id": ""}'
 */

import com.browseract.agent.demo.util.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;
import java.util.Map;

public class RunTask {

    public static void main(String[] args) throws JsonProcessingException {
        // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
        String authorization = "app-abcdefghijklmn";

        // agent ID, you can copy it from: https://www.browseract.com/reception/agent-list
        long agentId = 1234567890L;

        // api address
        String url = "https://api.browseract.com/v2/agent/run-task";

        // authorization for request
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + authorization);

        // init api parameters
        RunTaskRequest requestBodyObject = new RunTaskRequest();
        requestBodyObject.setAgent_id(agentId);
        requestBodyObject.setTask("open github,search LLM and save first 10 project name.");
        requestBodyObject.setSave_browser_data(true);
        requestBodyObject.setProfile_id("");

        // add secrets for login
        Map<String, Map<String, String>> secrets = new HashMap<>();
        Map<String, String> githubSecrets = new HashMap<>();
        githubSecrets.put("login", "username");
        githubSecrets.put("password", "xxxx");
        secrets.put("https://github.com/login", githubSecrets);
        requestBodyObject.setSecrets(secrets);

        // send request
        HttpUtil.HttpResult result = HttpUtil.postJson(url, requestBodyObject, headers);
        if (!result.isSuccess()) {
            System.err.println("api-call-error: status=" + result.getCode() + " " + result.getText());
            return;
        }

        System.out.println("api-call-ok: " + result.getText());

        // Parse response to get task_id
        // JsonNode jsonNode = new ObjectMapper().readTree(result.getText());
        // String taskId = jsonNode.get("id").asText();
        // System.out.println("Task ID: " + taskId);

        // Polling the task status until the task is completed or timed out.
        // Please refer to "GetTask.java" or "GetTaskStatus.java"
    }

    public static class RunTaskRequest {
        /**
         * The agent ID used to create and spawn a new task.
         */
        private long agent_id;

        /**
         * The task description that you want the agent to perform, in natural language.
         */
        private String task;

        /**
         * Sensitive data required for task execution. For security and privacy purposes, this data will not be submitted to the LLM.
         * Usage Example: {"https://github.com/login": {"login": "username","password": "xxxx"}},
         * where the "login" and "password" values are only used when accessing the URL "https://github.com/login"
         */
        private Map<String, Map<String, String>> secrets;

        /**
         * Specify whether a profile_id should be returned in the response upon successful task submission.
         * The profile stores browser session data, including cookies and other browsing state, that is generated during task execution.
         */
        private boolean save_browser_data;

        /**
         * The browser profile to use for this agent task.
         * Browser profiles store session data, such as cookies, and other browsing state, that can be reused across tasks.
         * Note: if profile_id isn't provided, a random one will be generated during task execution.
         */
        private String profile_id;

        // Getters and Setters
        public long getAgent_id() {
            return agent_id;
        }

        public void setAgent_id(long agent_id) {
            this.agent_id = agent_id;
        }

        public String getTask() {
            return task;
        }

        public void setTask(String task) {
            this.task = task;
        }

        public Map<String, Map<String, String>> getSecrets() {
            return secrets;
        }

        public void setSecrets(Map<String, Map<String, String>> secrets) {
            this.secrets = secrets;
        }

        public boolean isSave_browser_data() {
            return save_browser_data;
        }

        public void setSave_browser_data(boolean save_browser_data) {
            this.save_browser_data = save_browser_data;
        }

        public String getProfile_id() {
            return profile_id;
        }

        public void setProfile_id(String profile_id) {
            this.profile_id = profile_id;
        }
    }
}