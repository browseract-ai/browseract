/**
 * Description:
 * Get workflow details, including required input parameters
 * 
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-workflow
 * 
 * curl -X GET 'https://api.browseract.com/v2/workflow/get-workflow?workflow_id=1234567890' -H 'Authorization: Bearer app-abcdefghijklmn'
 */

const https = require('https');

async function main() {
    // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
    const authorization = "app-abcdefghijklmn";
    
    // workflow ID, you can copy it from: https://www.browseract.com/reception/workflow-list
    const workflowId = 16217357109956214;
    
    try {
        const options = {
            hostname: 'api.browseract.com',
            port: 443,
            path: `/v2/workflow/get-workflow?workflow_id=${workflowId}`,
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
                    console.log('\nWorkflow Details:');
                    console.log('ID:', response.id);
                    console.log('Name:', response.name);
                    console.log('Description:', response.description);
                    console.log('Created At:', response.create_at);
                    console.log('Published At:', response.publish_at);
                    
                    if (response.input_parameters) {
                        console.log('\nInput Parameters:');
                        response.input_parameters.forEach(param => {
                            console.log(`- ${param.name} (default_enabled: ${param.default_enabled})`);
                        });
                    }
                } else {
                    // error example:
                    // {'code': 401, 'msg': 'Invalid authorization', 'data': None, 'ts': 1759917250113, 'time': '2025-10-08 09:54:10', 'traceId': 'bcdef'}
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
  "id": "16217357109956214",
  "name": "test demo",
  "description": "This is a test demo",
  "create_at": "2025-10-07T06:15:24Z",
  "publish_at": "2025-10-07T06:27:38Z",
  "input_parameters": [
    {
      "name": "target_url",
      "default_enabled": true
    },
    {
      "name": "product_limit",
      "default_enabled": true
    }
  ]
}
*/
