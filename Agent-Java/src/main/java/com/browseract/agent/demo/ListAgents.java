package com.browseract.agent.demo;

/**
 * Description:
 * Retrieve a list of agents associated with your account. This endpoint supports pagination and filtering.
 * <p>
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-agent
 * <p>
 * curl -X GET 'https://api.browseract.com/v2/agent/list-agents?page=1&size=10' -H 'Authorization: Bearer app-abcdefghijklmn'
 */

import com.browseract.agent.demo.util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

public class ListAgents {

    public static void main(String[] args) {
        // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
        String authorization = "app-abcdefghijklmn";

        // api address
        String url = "https://api.browseract.com/v2/agent/list-agents?page=1&size=10";

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