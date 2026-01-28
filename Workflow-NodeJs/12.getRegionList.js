/**
 * Description:
 * Returns a list of supported regions for proxy usage.
 * 
 * Documentation:
 * https://www.browseract.com/reception/integrations/api-workflow
 * 
 * curl -X GET 'https://api.browseract.com/v2/workflow/get-region-list' -H 'Authorization: Bearer app-abcdefghijklmn'
 */

const https = require('https');

async function main() {
    // API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
    const authorization = "app-abcdefghijklmn";
    
    try {
        const options = {
            hostname: 'api.browseract.com',
            port: 443,
            path: '/v2/workflow/get-region-list',
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
                    // [{'name': 'United States', 'code': 'US'}, {'name': 'United Kingdom', 'code': 'UK'}, ...]
                    // success example with data: Please refer to the bottom of this file
                    console.log('api-call-ok:', responseData);
                    
                    const response = JSON.parse(responseData);
                    console.log(`Total regions: ${response.length}`);
                    
                    // Display regions in a more readable format
                    if (response.length > 0) {
                        console.log('\nAvailable Regions:');
                        response.forEach(region => {
                            console.log(`  - ${region.name} (Code: ${region.code})`);
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
[
  {
    "name": "United States",
    "code": "US"
  },
  {
    "name": "United Kingdom",
    "code": "UK"
  },
  {
    "name": "Germany",
    "code": "DE"
  },
  {
    "name": "Japan",
    "code": "JP"
  }
]
*/
