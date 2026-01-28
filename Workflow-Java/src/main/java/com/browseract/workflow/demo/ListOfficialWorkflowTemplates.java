package com.browseract.workflow.demo;

/**
 * Description:
 * Returns a paginated list of official workflow templates, sorted by creation time.
 * <p>
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-workflow
 * <p>
 * curl -X GET 'https://api.browseract.com/v2/workflow/list-official-workflow-templates?keyword=&page=1&limit=10' -H 'Authorization: Bearer app-abcdefghijklmn'
 */

import com.browseract.workflow.demo.util.HttpUtil;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ListOfficialWorkflowTemplates {

    public static void main(String[] args) {
        // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
        String authorization = "app-abcdefghijklmn";

        // Search keyword (optional)
        String keyword = "";

        // Page number (minimum: 1, default: 1)
        int page = 1;

        // Number of items per page (minimum: 1, maximum: 500, default: 1)
        int limit = 10;

        // Build query string
        StringBuilder urlBuilder = new StringBuilder("https://api.browseract.com/v2/workflow/list-official-workflow-templates?");
        urlBuilder.append("page=").append(page);
        urlBuilder.append("&limit=").append(limit);
        if (keyword != null && !keyword.isEmpty()) {
            urlBuilder.append("&keyword=").append(URLEncoder.encode(keyword, StandardCharsets.UTF_8));
        }

        // api address
        String url = urlBuilder.toString();

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
