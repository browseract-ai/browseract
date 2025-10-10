/**
 * Description:
 * Get the current status of a task. This is a lightweight endpoint that returns only the task status
 * without detailed information. For complete task details, use the /get-task endpoint.
 * 
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-agent
 * 
 * curl -X GET 'https://api.browseract.com/v2/agent/get-task-status?task_id=16429034742537847' -H 'Authorization: Bearer app-abcdefghijklmn'
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
            path: `/v2/agent/get-task-status?task_id=${taskId}`,
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
                    // success example:
                    // {'status': 'running'}
                    // {'status': 'finished'}
                    // {'status': 'failed'}
                    // {'status': 'paused'}
                    console.log('api-call-ok:', responseData);
                    
                    const response = JSON.parse(responseData);
                    const status = response.status;
                    console.log('Task Status:', status);
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
