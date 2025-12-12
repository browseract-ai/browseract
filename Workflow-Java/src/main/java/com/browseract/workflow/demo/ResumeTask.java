package com.browseract.workflow.demo;

/**
 * Description:
 * Resume a paused workflow task and continue execution from where it left off. 
 * This operation is only available for tasks that are currently in a paused state - other status tasks cannot be resumed.
 * 
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-workflow
 * 
 * curl -X PUT 'https://api.browseract.com/v2/workflow/resume-task?task_id=1234567890' -H 'Authorization: Bearer app-abcdefghijklmn'
 */

import com.browseract.workflow.demo.util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

public class ResumeTask {

    public static void main(String[] args) {
        // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
        String authorization = "app-abcdefghijklmn";

        // The task ID, returned by endpoints like /run-task.
        // Please refer to "RunTask.java"
        long taskId = 1234567890L;

        // api address
        String url = "https://api.browseract.com/v2/workflow/resume-task?task_id=" + taskId;

        // authorization for request
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + authorization);

        // send request
        HttpUtil.HttpResult result = HttpUtil.put(url, headers);
        if (!result.isSuccess()) {
            System.err.println("api-call-error: status=" + result.getCode() + " " + result.getText());
            return;
        }

        System.out.println("api-call-ok: " + result.getText());
    }
}