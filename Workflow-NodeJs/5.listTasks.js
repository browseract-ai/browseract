/**
 * Description:
 * Return a paginated list of all workflow tasks, sorted by creation time. 
 * Each task includes basic information such as status and creation time.
 * 
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-workflow
 * 
 * curl -X GET 'https://api.browseract.com/v2/workflow/list-tasks?workflow_id=&page=1&limit=10' -H 'Authorization: Bearer app-abcdefghijklmn'
 */

const https = require('https');

async function main() {
    // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
    const authorization = "app-abcdefghijklmn";
    
    // Specify the workflow ID to filter tasks. 
    // When provided, return only tasks associated with the specified workflow. 
    // If omitted, return tasks from all workflows.
    // You can copy it from: https://www.browseract.com/reception/workflow-list
    const workflowId = "";
    
    // Page number (minimum: 1, default: 1)
    const page = 1;
    
    // Number of items per page (minimum: 1, maximum: 500, default: 1)
    const limit = 10;
    
    try {
        const options = {
            hostname: 'api.browseract.com',
            port: 443,
            path: `/v2/workflow/list-tasks?workflow_id=${workflowId}&page=${page}&limit=${limit}`,
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
                    // {'page': 1, 'limit': 10, 'items': [], 'total_pages': 0, 'total_count': 0}
                    // success example with data: Please refer to the bottom of this file
                    console.log('api-call-ok:', responseData);
                    
                    const response = JSON.parse(responseData);
                    const totalCount = response.total_count;
                    const currentPage = response.page;
                    const currentLimit = response.limit;
                    const items = response.items;
                    const currentItems = items.length;
                    
                    console.log(`Total records: ${totalCount}, Current page records: ${currentItems}`);
                    console.log(`Page: ${currentPage}, Limit: ${currentLimit}`);
                    
                    // Display task items
                    if (currentItems > 0) {
                        console.log('\nTask List:');
                        items.forEach(item => {
                            console.log('ID:', item.id);
                            console.log('Status:', item.status);
                            console.log('Workflow ID:', item.workflow_id);
                            console.log('---');
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
  "page": 1,
  "limit": 10,
  "items": [{
    "id": "16628193563511975",
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
      "string": null,
      "files": null
    },
    "status": "created",
    "live_url_info": {
      "width": 1280,
      "height": 1024,
      "live_url": "https://www.browseract.com/remote/abcde"
    },
    "live_url": "https://www.browseract.com/remote/abcde",
    "profile_id": "abcdef",
    "created_at": null,
    "finished_at": null,
    "task_failure_info": null
  }],
  "total_pages": 1,
  "total_count": 1
}
*/
