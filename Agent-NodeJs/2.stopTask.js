/**
 * Description:
 * Permanently terminate the currently running task. 
 * This action is irreversible - once stopped, the task cannot be resumed or restarted. 
 * For temporary task suspension that allows resumption, use the /pause-task endpoint instead.
 * 
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-agent
 * 
 * curl -X PUT 'https://api.browseract.com/v2/agent/stop-task?task_id=16429034742537847' -H 'Authorization: Bearer app-abcdefghijklmn'
 */

const https = require('https');

async function main() {
    // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
    const authorization = "app-abcdefghijklmn";
    
    // The task ID, returned by endpoints like /run-task.
    // Please refer to "1.runTask.js"
    const taskId = 16612404460519847;
    
    try {
        const options = {
            hostname: 'api.browseract.com',
            port: 443,
            path: `/v2/agent/stop-task?task_id=${taskId}`,
            method: 'PUT',
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
                    // success example: No content will be returned
                    console.log('api-call-ok:', responseData);
                } else {
                    // error example:
                    // {'code': 401, 'msg': 'Invalid authorization', 'data': None, 'ts': 1759917250113, 'time': '2025-10-08 09:54:10', 'traceId': 'bcdef'}
                    // {'code': 10112, 'msg': 'Task is not exist.', 'data': None, 'ts': 1759918566040, 'time': '2025-10-08 10:16:06', 'traceId': 'cdefg'}
                    // {'code': 10121, 'msg': 'Task has completed.', 'data': None, 'ts': 1760011576022, 'time': '2025-10-09 12:06:16', 'traceId': 'hijkl'}
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
