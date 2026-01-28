package com.browseract.workflow.demo;

/**
 * Description:
 * Start a new workflow task using an official template and return a task ID for progress tracking.
 * <p>
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-workflow
 * <p>
 * curl -X POST 'https://api.browseract.com/v2/workflow/run-task-by-template' -H 'Authorization: Bearer app-abcdefghijklmn' -H 'Content-Type: application/json' -d '{"workflow_template_id": "1234567890","input_parameters": [{"name": "target_url","value": "https://www.google.com/search?q=iphone17"},{"name": "product_limit","value": "10"}],"proxyRegion": "US", "callback_url": "https://www.mydomain.com/callback"}'
 */

import com.browseract.workflow.demo.util.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunTaskByTemplate {

    public static void main(String[] args) throws JsonProcessingException {
        // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
        String authorization = "app-abcdefghijklmn";

        // workflow template ID, you can get it from: https://www.browseract.com/reception/workflow-list
        // Or use the API: GET /v2/workflow/list-official-workflow-templates
        String workflowTemplateId = "1234567890";

        // api address
        String url = "https://api.browseract.com/v2/workflow/run-task-by-template";

        // authorization for request
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + authorization);

        // init api parameters
        RunTaskByTemplateRequest requestBodyObject = new RunTaskByTemplateRequest();
        requestBodyObject.setWorkflow_template_id(workflowTemplateId);
        requestBodyObject.setProxyRegion("US");
        requestBodyObject.setCallback_url("https://www.mydomain.com/task_finish_callback");
        requestBodyObject.setStatus_change_callback_url("https://www.mydomain.com/task_status_change_callback");

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

    public static class RunTaskByTemplateRequest {
        /**
         * The workflow template ID used to create and spawn a new task.
         */
        private String workflow_template_id;

        /**
         * Parameters entered when running a workflow task,
         * which are defined by the template
         */
        private List<InputParameter> input_parameters;

        /**
         * Optional. Region where the proxy should be used. Default is "US".
         * You can get available regions from: GET /v2/workflow/get-region-list
         */
        private String proxyRegion;
        
        /**
         * HTTP/HTTPS URL to receive task completion notifications via POST request.
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
        
        /**
         * HTTP/HTTPS URL to receive task status change notifications via POST request.
         * The callback payload structure is identical to the "Get Task" API response.
         * Triggered when: Task running, paused, finished, canceled, failed.
         * Requirements:
         * - Valid HTTP/HTTPS URL (max 2048 characters)
         * - Publicly accessible endpoint
         * - Must return 2xx status within 30 seconds
         * - Redirects (3xx) are not allowed
         * Retry: Automatic retry (max 3 attempts) for 5xx errors only.
         */
        private String status_change_callback_url;

        // Getters and Setters
        public String getWorkflow_template_id() {
            return workflow_template_id;
        }

        public void setWorkflow_template_id(String workflow_template_id) {
            this.workflow_template_id = workflow_template_id;
        }

        public List<InputParameter> getInput_parameters() {
            return input_parameters;
        }

        public void setInput_parameters(List<InputParameter> input_parameters) {
            this.input_parameters = input_parameters;
        }

        public String getProxyRegion() {
            return proxyRegion;
        }

        public void setProxyRegion(String proxyRegion) {
            this.proxyRegion = proxyRegion;
        }
        
        public String getCallback_url() {
            return callback_url;
        }

        public void setCallback_url(String callback_url) {
            this.callback_url = callback_url;
        }
        
        public String getStatus_change_callback_url() {
            return status_change_callback_url;
        }

        public void setStatus_change_callback_url(String status_change_callback_url) {
            this.status_change_callback_url = status_change_callback_url;
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
