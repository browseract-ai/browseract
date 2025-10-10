/**
 * Description:
 * Start a new agent task and return a task ID for progress tracking.
 * 
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-agent
 * 
 * curl -X POST 'https://api.browseract.com/v2/agent/run-task' -H 'Authorization: Bearer app-abcdefghijklmn' -H 'Content-Type: application/json' -d '{"agent_id": "1234567890","task": "open github,search LLM and save first 10 project name.","save_browser_data": true,"profile_id": ""}'
 */

const https = require('https');

async function main() {
    // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
    const authorization = "app-abcdefghijklmn";
    
    // agent ID, you can copy it from: https://www.browseract.com/reception/agent-list
    const agentId = 1234567890;
    
    try {
        // Create request data
        const data = JSON.stringify({
            // The agent ID used to create and spawn a new task.
            "agent_id": agentId,
            
            // The task description that you want the agent to perform, in natural language.
            "task": "open github,search LLM and save first 10 project name.",
            
            // Sensitive data required for task execution. For security and privacy purposes, this data will not be submitted to the LLM. 
            // Usage Example: {"https://github.com/login": {"login": "username","password": "xxxx"}}, 
            // where the "login" and "password" values are only used when accessing the URL "https://github.com/login"
            "secrets": {
                "https://github.com/login": {
                    "login": "username",
                    "password": "xxxx"
                }
            },
            
            // Specify whether a profile_id should be returned in the response upon successful task submission. 
            // The profile stores browser session data, including cookies and other browsing state, that is generated during task execution.
            "save_browser_data": true,
            
            // The browser profile to use for this agent task. 
            // Browser profiles store session data, such as cookies, and other browsing state, that can be reused across tasks.
            // Note: if profile_id isn't provided, a random one will be generated during task execution.
            "profile_id": ""
        });
        
        const options = {
            hostname: 'api.browseract.com',
            port: 443,
            path: '/v2/agent/run-task',
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
                    // Please refer to "5.getTask.js" or "6.getTaskStatus.js"
                } else {
                    // error example:
                    // {'code': 401, 'msg': 'Invalid authorization', 'data': None, 'ts': 1759917250113, 'time': '2025-10-08 09:54:10', 'traceId': 'bcdef'}
                    // {'code': 10010, 'msg': 'Agent does not exist', 'data': None, 'ts': 1759997243432, 'time': '2025-10-09 08:07:23', 'traceId': 'cdefg'}
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
