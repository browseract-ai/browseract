"""
Description:
Returns a paginated list of all Workflows, sorted by creation time.

Documentation:
https://www.browseract.com/reception/integrations/api-workflow

curl -X GET 'https://api.browseract.com/v2/workflow/list-workflows?page=1&limit=10' -H 'Authorization: Bearer app-abcdefghijklmn'
"""

import traceback
import requests

def main():
    # API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
    authorization = "app-abcdefghijklmn"

    # Page number (minimum: 1, default: 1)
    page = 1

    # Number of items per page (minimum: 1, maximum: 500, default: 1)
    limit = 10

    try:
        headers = {
            "Authorization": f"Bearer {authorization}"
        }
        
        api_url = f"https://api.browseract.com/v2/workflow/list-workflows?page={page}&limit={limit}"
        response = requests.get(
            api_url, headers=headers
        )

        if response.status_code == 200:
            # success example:
            # {'page': 1, 'limit': 10, 'items': [], 'total_pages': 0, 'total_count': 0}
            # success example with data: Please refer to the bottom of this file
            json = response.json()
            total = json['total_count']
            current = len(json['items'])
            print(f"api-call-ok: total records num:{total} current page records num:{current}\n\n", json)
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
  'page': 1,
  'limit': 10,
  'items': [{
    'id': '16217357109956214',
    'name': 'test demo',
    'description': 'This is a test demo',
    'create_at': '2025-10-07T06:15:24Z',
    'publish_at': '2025-10-07T06:27:37Z'
  }],
  'total_pages': 1,
  'total_count': 1
}
"""