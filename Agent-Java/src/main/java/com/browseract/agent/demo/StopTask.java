package com.browseract.agent.demo;

/**
 * Description:
 * Permanently terminate the currently running task.
 * This action is irreversible - once stopped, the task cannot be resumed or restarted.
 * For temporary task suspension that allows resumption, use the /pause-task endpoint instead.
 * <p>
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-agent
 * <p>
 * curl -X PUT 'https://api.browseract.com/v2/agent/stop-task?task_id=16429034742537847' -H 'Authorization: Bearer app-abcdefghijklmn'
 */

import com.browseract.agent.demo.util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

public class StopTask {

    public static void main(String[] args) {
        // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
        String authorization = "app-abcdefghijklmn";

        // The task ID, returned by endpoints like /run-task.
        // Please refer to "RunTask.java"
        long taskId = 16612404460519847L;

        // api address
        String url = "https://api.browseract.com/v2/agent/stop-task?task_id=" + taskId;

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