/**
 * Description:
 * Start a new workflow task using an official template and return a task ID for progress tracking.
 * 
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-workflow
 * 
 * curl -X POST 'https://api.browseract.com/v2/workflow/run-task-by-template' -H 'Authorization: Bearer app-abcdefghijklmn' -H 'Content-Type: application/json' -d '{"workflow_template_id": "1234567890","input_parameters": [{"name": "target_url","value": "https://www.google.com/search?q=iphone17"},{"name": "product_limit","value": "10"}],"proxyRegion": "US", "callback_url": "https://www.mydomain.com/callback"}'
 */

const https = require('https');

async function main() {
    // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
    const authorization = "app-abcdefghijklmn";
    
    // workflow template ID, you can get it from: https://www.browseract.com/reception/workflow-list
    // Or use the API: GET /v2/workflow/list-official-workflow-templates
    const workflowTemplateId = "1234567890";
    
    try {
        // Create request data
        const data = JSON.stringify({
            // The workflow template ID used to create and spawn a new task.
            "workflow_template_id": workflowTemplateId,
            
            // Parameters entered when running a workflow task, 
            // which are defined by the template
            "input_parameters": [
                {
                    // First parameter's name
                    "name": "target_url",
                    // First parameter's value
                    "value": "https://www.google.com/search?q=iphone17"
                },
                {
                    // Second parameter's name
                    "name": "product_limit",
                    // Second parameter's value
                    "value": "10"
                }
            ],
            
            // Optional. Region where the proxy should be used. Default is "US".
            // You can get available regions from: GET /v2/workflow/get-region-list
            "proxyRegion": "US",

            // HTTP/HTTPS URL to receive task completion notifications via POST request.
            // The callback payload structure is identical to the "Get Task" API response.
            // Triggered when: Task completes, fails, or is canceled.
            // Requirements:
            // - Valid HTTP/HTTPS URL (max 2048 characters)
            // - Publicly accessible endpoint
            // - Must return 2xx status within 30 seconds
            // - Redirects (3xx) are not allowed
            // Retry: Automatic retry (max 3 attempts) for 5xx errors only.
            "callback_url": "https://www.mydomain.com/task_finish_callback",

            // HTTP/HTTPS URL to receive task status change notifications via POST request.
            // The callback payload structure is identical to the "Get Task" API response.
            // Triggered when: Task running, paused, finished, canceled, failed.
            // Requirements:
            // - Valid HTTP/HTTPS URL (max 2048 characters)
            // - Publicly accessible endpoint
            // - Must return 2xx status within 30 seconds
            // - Redirects (3xx) are not allowed
            // Retry: Automatic retry (max 3 attempts) for 5xx errors only.
            "status_change_callback_url": "https://www.mydomain.com/task_status_change_callback"
        });
        
        const options = {
            hostname: 'api.browseract.com',
            port: 443,
            path: '/v2/workflow/run-task-by-template',
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${authorization}`,
                'Content-Type': 'application/json',
                'Content-Length': Buffer.byteLength(data)
            }
        };
        
        const req = https.request(options, (res) => {
            let responseData = '';
            
            res.on('data', (chunk) => {
                responseData += chunk;
            });
            
            res.on('end', () => {
                if (res.statusCode === 200) {
                    // success example:
                    // {'id': '12425895140306551', 'profileId': 'abcde'}
                    console.log('api-call-ok:', responseData);
                    
                    const response = JSON.parse(responseData);
                    const taskId = response.id;
                    console.log('Task ID:', taskId);
                    
                    // Polling the task status until the task is completed or timed out.
                    // Please refer to "3.getTask.js" or "4.getTaskStatus.js"
                } else {
                    // error example:
                    // {'code': 401, 'msg': 'Invalid authorization', 'data': None, 'ts': 1759917250113, 'time': '2025-10-08 09:54:10', 'traceId': 'bcdef'}
                    // {'code': 10118, 'msg': 'Running tasks number exceeds.', 'data': None, 'ts': 1759917310153, 'time': '2025-10-08 09:55:10', 'traceId': 'cdefg'}
                    console.log(`api-call-error: status=${res.statusCode}`, responseData);
                }
            });
        });
        
        req.on('error', (error) => {
            console.log('run-error:', error.message);
        });
        
        req.write(data);
        req.end();
        
    } catch (error) {
        console.log('run-error:', error.message);
    }
}

main();
