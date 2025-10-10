/**
 * Description:
 * Retrieve detailed information about the agent task, including current status, completed steps, 
 * output from finished tasks, and other metadata.
 * 
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-agent
 * 
 * curl -X GET 'https://api.browseract.com/v2/agent/get-task?task_id=16429034742537847' -H 'Authorization: Bearer app-abcdefghijklmn'
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
            path: `/v2/agent/get-task?task_id=${taskId}`,
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
                    console.log('Task:', response.task);
                    
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
  "task": "open github,search LLM and save first 10 project name.",
  "output": {
    "string": "Task complete! I searched GitHub for 'LLM' and extracted the first 10 project names as requested. The names are saved in results.json, and progress is tracked in todo.md. Please find both files attached for your review.\n\nAttachments:\n\nresults.json:\n{\"repositories\": [\"simonw/llm\", \"rustformers/llm\", \"karpathy/LLM101n\", \"rasbt/LLMs-from-scratch\", \"mlabonne/llm-course\", \"Shubhamsaboo/awesome-llm-apps\", \"Mintplex-Labs/anything-llm\", \"llmware-ai/llmware\", \"karpathy/llm.c\", \"Mooler0410/LLMsPracticalGuide\"]}\n",
    "files": ["Task output file url"]
  },
  "status": "finished",
  "steps": [{
    "id": "184549",
    "step": 1,
    "status": "succeed",
    "evaluation_previous_goal": "Initial step; no previous action to evaluate.",
    "step_goal": "Navigate to https://github.com, search for 'LLM', and extract the first 10 project names from the results page.",
    "screenshots_url": null
  }, {
    "id": "184551",
    "step": 2,
    "status": "succeed",
    "evaluation_previous_goal": "Successfully navigated to the GitHub homepage. Verdict: Success.",
    "step_goal": "Input 'LLM' into the search bar (index 12) and trigger the search to reach the results page.",
    "screenshots_url": "Step screenshot file url"
  }],
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
  "task_gif_url": null,
  "agent_id": "14774755173139766"
}
*/
