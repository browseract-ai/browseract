package com.browseract.workflow.demo;

/**
 * Description:
 * Start a new workflow task and return a task ID for progress tracking.
 * <p>
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-workflow
 * <p>
 * curl -X POST 'https://api.browseract.com/v2/workflow/run-task' -H 'Authorization: Bearer app-abcdefghijklmn' -H 'Content-Type: application/json' -d '{"workflow_id": "1234567890","input_parameters": [{"name": "target_url","value": "https://www.google.com/search?q=iphone17"},{"name": "product_limit","value": "10"}],"save_browser_data": true,"profile_id": "","callback_url": "https://www.mydomain.com/callback"}'
 */

import com.browseract.workflow.demo.util.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunTask {

    public static void main(String[] args) throws JsonProcessingException {
        // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
        String authorization = "app-abcdefghijklmn";

        // workflow ID, you can copy it from: https://www.browseract.com/reception/workflow-list
        long workflowId = 1234567890L;

        // api address
        String url = "https://api.browseract.com/v2/workflow/run-task";

        // authorization for request
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + authorization);

        // init api parameters
        RunTaskRequest requestBodyObject = new RunTaskRequest();
        requestBodyObject.setWorkflow_id(workflowId);
        requestBodyObject.setSave_browser_data(true);
        requestBodyObject.setProfile_id("");
        requestBodyObject.setCallback_url("https://www.mydomain.com/callback");

        // add workflow's parameters
        List<InputParameter> params = new ArrayList<>();
        params.add(new InputParameter("target_url", "https://www.google.com/search?q=iphone17"));
        params.add(new InputParameter("product_limit", "10"));
        requestBodyObject.setInput_parameters(params);


        // send request
        HttpUtil.HttpResult result = HttpUtil.postJson(url, requestBodyObject, headers);
        if (!result.isSuccess()) {
            System.err.println("api-call-error: status=" + result.getCode() + " " + result.getText());
            return;
        }

        System.out.println("api-call-ok: " + result.getText());

        // JsonNode jsonNode = new ObjectMapper().readTree(result.getText());
        // String taskId = jsonNode.get("id").asText();
        // System.out.println("Task ID: " + taskId);

        // Polling the task status until the task is completed or timed out.
        // Please refer to "GetTask.java" or "GetTaskStatus.java"
    }

    public static class RunTaskRequest {
        /**
         * The workflow ID used to create and spawn a new task.
         */
        private long workflow_id;

        /**
         * Parameters entered when running a workflow task,
         * which are defined by you when orchestrate the workflow
         */
        private List<InputParameter> input_parameters;

        /**
         * Specify whether a profile_id should be returned in the response upon successful task submission.
         * The profile stores browser session data, including cookies and other browsing state, that is generated during task execution.
         */
        private boolean save_browser_data;

        /**
         * The browser profile to use for this workflow task.
         * Browser profiles store session data, such as cookies, and other browsing state, that can be reused across tasks.
         * Note: if profile_id isn't provided, a random one will be generated during task execution.
         */
        private String profile_id;
        
        /**
         * HTTP / HTTPS URL to receive task completion notifications via POST request.
         * The callback payload structure is identical to the "Get Task" API response.
         * Triggered when: Task completes, fails, or is canceled.
         * Requirements:
         * - Valid HTTP/HTTPS URL (max 2048 characters)
         * - Publicly accessible endpoint
         * - Must return 2xx status within 30 seconds
         * - Redirects (3xx) are not allowed
         * Retry: Automatic retry (max 3 attempts) for 5xx errors only.
         */
        private String callback_url;

        // Getters and Setters
        public long getWorkflow_id() {
            return workflow_id;
        }

        public void setWorkflow_id(long workflow_id) {
            this.workflow_id = workflow_id;
        }

        public List<InputParameter> getInput_parameters() {
            return input_parameters;
        }

        public void setInput_parameters(List<InputParameter> input_parameters) {
            this.input_parameters = input_parameters;
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
        
        public String getCallback_url() {
            return callback_url;
        }

        public void setCallback_url(String callback_url) {
            this.callback_url = profile_id;
        }
    }

    public static class InputParameter {
        /**
         * Workflow parameter's name
         */
        private String name;

        /**
         * Workflow parameter's value
         */
        private String value;

        public InputParameter(String name, String value) {
            this.name = name;
            this.value = value;
        }

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
