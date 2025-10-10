/**
 * Description:
 * Returns a paginated list of all Workflows, sorted by creation time.
 * 
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-workflow
 * 
 * curl -X GET 'https://api.browseract.com/v2/workflow/list-workflows?page=1&limit=10' -H 'Authorization: Bearer app-abcdefghijklmn'
 */

const https = require('https');

async function main() {
    // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
    const authorization = "app-abcdefghijklmn";
    
    // Page number (minimum: 1, default: 1)
    const page = 1;
    
    // Number of items per page (minimum: 1, maximum: 500, default: 1)
    const limit = 10;
    
    try {
        const options = {
            hostname: 'api.browseract.com',
            port: 443,
            path: `/v2/workflow/list-workflows?page=${page}&limit=${limit}`,
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
                    
                    console.log(`Total workflows: ${totalCount}, Current page workflows: ${currentItems}`);
                    console.log(`Page: ${currentPage}, Limit: ${currentLimit}`);
                    
                    // Display workflow items
                    if (currentItems > 0) {
                        console.log('\nWorkflow List:');
                        items.forEach(item => {
                            console.log('ID:', item.id);
                            console.log('Name:', item.name);
                            console.log('Description:', item.description);
                            console.log('Created At:', item.create_at);
                            console.log('Published At:', item.publish_at);
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
    "id": "16217357109956214",
    "name": "test demo",
    "description": "This is a test demo",
    "create_at": "2025-10-07T06:15:24Z",
    "publish_at": "2025-10-07T06:27:37Z"
  }],
  "total_pages": 1,
  "total_count": 1
}
*/
