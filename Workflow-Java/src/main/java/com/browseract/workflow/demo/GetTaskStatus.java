package com.browseract.workflow.demo;

/**
 * Description:
 * Get the current status of a task. This is a lightweight endpoint that returns only the task's status and basic information.
 * <p>
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-workflow
 * <p>
 * curl -X GET 'https://api.browseract.com/v2/workflow/get-task-status?task_id=16429034742537847' -H 'Authorization: Bearer app-abcdefghijklmn'
 */

import com.browseract.workflow.demo.util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

public class GetTaskStatus {

    public static void main(String[] args) {
        // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
        String authorization = "app-abcdefghijklmn";

        // The task ID, returned by endpoints like /run-task.
        // Please refer to "RunTask.java"
        long taskId = 16612404460519847L;

        // api address
        String url = "https://api.browseract.com/v2/workflow/get-task-status?task_id=" + taskId;

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