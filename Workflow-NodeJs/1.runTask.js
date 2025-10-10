/**
 * Description:
 * Start a new workflow task and return a task ID for progress tracking.
 * 
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-workflow
 * 
 * curl -X POST 'https://api.browseract.com/v2/workflow/run-task' -H 'Authorization: Bearer app-abcdefghijklmn' -H 'Content-Type: application/json' -d '{"workflow_id": "1234567890","input_parameters": [{"name": "target_url","value": "https://www.google.com/search?q=iphone17"},{"name": "product_limit","value": "10"}],"save_browser_data": true,"profile_id": ""}'
 */

const https = require('https');

async function main() {
    // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
    const authorization = "app-abcdefghijklmn";
    
    // workflow ID, you can copy it from: https://www.browseract.com/reception/workflow-list
    const workflowId = 1234567890;
    
    try {
        // Create request data
        const data = JSON.stringify({
            // The workflow ID used to create and spawn a new task.
            "workflow_id": workflowId,
            
            // Parameters entered when running a workflow task, 
            // which are defined by you when orchestrate the workflow
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
            
            // Specify whether a profile_id should be returned in the response upon successful task submission. 
            // The profile stores browser session data, including cookies and other browsing state, that is generated during task execution.
            "save_browser_data": true,
            
            // The browser profile to use for this workflow task. 
            // Browser profiles store session data, such as cookies, and other browsing state, that can be reused across tasks.
            // Note: if profile_id isn't provided, a random one will be generated during task execution.
            "profile_id": ""
        });
        
        const options = {
            hostname: 'api.browseract.com',
            port: 443,
            path: '/v2/workflow/run-task',
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
