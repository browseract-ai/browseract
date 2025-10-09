"""
Description:
Get workflow details, including required input parameters

Documentation:
https://www.browseract.com/reception/integrations/api-workflow

curl -X GET 'https://api.browseract.com/v2/workflow/get-workflow?workflow_id=1234567890' -H 'Authorization: Bearer app-abcdefghijklmn'
"""

import traceback
import requests

def main():
    # API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
    authorization = "app-abcdefghijklmn"

    # workflow ID, you can copy it from: https://www.browseract.com/reception/workflow-list
    workflow_id = 16217357109956214

    try:
        headers = {
            "Authorization": f"Bearer {authorization}"
        }
        
        api_url = f"https://api.browseract.com/v2/workflow/get-workflow?workflow_id={workflow_id}"
        response = requests.get(
            api_url, headers=headers
        )

        if response.status_code == 200:
            # success example:
            # {'page': 1, 'limit': 10, 'items': [], 'total_pages': 0, 'total_count': 0}
            # success example with data: Please refer to the bottom of this file
            print("api-call-ok:", response.json())
        else:
            # error example:
            # {'code': 401, 'msg': 'Invalid authorization', 'data': None, 'ts': 1759917250113, 'time': '2025-10-08 09:54:10', 'traceId': 'bcdef'}
            print("api-call-error:", response.json())
    except:
        error = traceback.format_exc()
        print(f"run-error: {error}")

if __name__ == "__main__":
    main()

"""
success example: 
{
  'id': '16217357109956214',
  'name': 'test demo',
  'description': 'This is a test demo',
  'create_at': '2025-10-07T06:15:24Z',
  'publish_at': '2025-10-07T06:27:38Z',
  'input_parameters': [{
    'name': 'target_url',
    'default_enabled': True
  }, {
    'name': 'product_limit',
    'default_enabled': True
  }]
}
"""