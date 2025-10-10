/**
 * Description:
 * Retrieve detailed information about the workflow task, including current status, completed steps, 
 * output from finished tasks, and other metadata.
 * 
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-workflow
 * 
 * curl -X GET 'https://api.browseract.com/v2/workflow/get-task?task_id=16429034742537847' -H 'Authorization: Bearer app-abcdefghijklmn'
 */

const https = require('https');

async function main() {
    // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
    const authorization = "app-abcdefghijklmn";
    
    // The task ID, returned by endpoints like /run-task.
    // Please refer to "1.runTask.js"
    const taskId = 16429034742537847;
    
    try {
        const options = {
            hostname: 'api.browseract.com',
            port: 443,
            path: `/v2/workflow/get-task?task_id=${taskId}`,
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${authorization}`
            }
        };
        
        const req = https.request(options, (res) => {
            let responseData = '';
            
            res.on('data', (chunk) => {
                responseData += chunk;
            });
            
            res.on('end', () => {
                if (res.statusCode === 200) {
                    // success example: Please refer to the bottom of this file
                    console.log('api-call-ok:', responseData);
                    
                    const response = JSON.parse(responseData);
                    console.log('\nTask Details:');
                    console.log('ID:', response.id);
                    console.log('Status:', response.status);
                    console.log('Workflow ID:', response.workflow_id);
                    
                    if (response.output && response.output.string) {
                        console.log('Output:', response.output.string);
                    }
                } else {
                    // error example:
                    // {'code': 401, 'msg': 'Invalid authorization', 'data': None, 'ts': 1759917250113, 'time': '2025-10-08 09:54:10', 'traceId': 'bcdef'}
                    // {'code': 10112, 'msg': 'Task is not exist.', 'data': None, 'ts': 1759918566040, 'time': '2025-10-08 10:16:06', 'traceId': 'cdefg'}
                    console.log(`api-call-error: status=${res.statusCode}`, responseData);
                }
            });
        });
        
        req.on('error', (error) => {
            console.log('run-error:', error.message);
        });
        
        req.end();
        
    } catch (error) {
        console.log('run-error:', error.message);
    }
}

main();

/*
success example:
{
  "id": "16429034742537847",
  "workflow_id": "16217357109956214",
  "input_parameters": [
    {
      "name": "target_url",
      "value": "https://www.google.com/search?q=iphone17"
    },
    {
      "name": "product_limit",
      "value": "10"
    }
  ],
  "output": {
    "string": "Workflow completed successfully! Extracted 10 products from the search results.",
    "files": ["Workflow output file url"]
  },
  "status": "finished",
  "steps": [
    {
      "id": "184549",
      "step": 1,
      "status": "succeed",
      "step_goal": "Navigate to the target URL and load the page",
      "screenshots_url": "Step screenshot file url"
    },
    {
      "id": "184551",
      "step": 2,
      "status": "succeed",
      "step_goal": "Extract product information from the page",
      "screenshots_url": "Step screenshot file url"
    }
  ],
  "live_url_info": {
    "width": 1280,
    "height": 1024,
    "live_url": "https://www.browseract.com/remote/aaaaa"
  },
  "live_url": "https://www.browseract.com/remote/aaaaa",
  "profile_id": "abcde",
  "created_at": "2025-10-09T08:08:35Z",
  "finished_at": "2025-10-09T08:09:55Z",
  "task_failure_info": null,
  "task_gif_url": null
}
*/
