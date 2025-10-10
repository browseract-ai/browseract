package com.browseract.workflow.demo;

/**
 * Description:
 * Retrieve detailed information about a specific workflow, including its configuration and metadata.
 * <p>
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-workflow
 * <p>
 * curl -X GET 'https://api.browseract.com/v2/workflow/get-workflow?workflow_id=1234567890' -H 'Authorization: Bearer app-abcdefghijklmn'
 */

import com.browseract.workflow.demo.util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

public class GetWorkflow {

    public static void main(String[] args) {
        // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
        String authorization = "app-abcdefghijklmn";

        // The workflow ID, you can copy it from: https://www.browseract.com/reception/workflow-list
        long workflowId = 1234567890L;

        // api address
        String url = "https://api.browseract.com/v2/workflow/get-workflow?workflow_id=" + workflowId;

        // authorization for request
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + authorization);

        // send request
        HttpUtil.HttpResult result = HttpUtil.get(url, headers);
        if (!result.isSuccess()) {
            System.err.println("api-call-error: status=" + result.getCode() + " " + result.getText());
            return;
        }

        System.out.println("api-call-ok: " + result.getText());
    }
}