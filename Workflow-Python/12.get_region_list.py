"""
Description:
Returns a list of supported regions for proxy usage.

Documentation:
https://www.browseract.com/reception/integrations/api-workflow

curl -X GET 'https://api.browseract.com/v2/workflow/get-region-list' -H 'Authorization: Bearer app-abcdefghijklmn'
"""

import traceback
import requests

def main():
    # API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
    authorization = "app-abcdefghijklmn"

    try:
        headers = {
            "Authorization": f"Bearer {authorization}"
        }
        
        api_url = "https://api.browseract.com/v2/workflow/get-region-list"
        response = requests.get(
            api_url, headers=headers
        )

        if response.status_code == 200:
            # success example:
            # [{'name': 'United States', 'code': 'US'}, {'name': 'United Kingdom', 'code': 'UK'}, ...]
            # success example with data: Please refer to the bottom of this file
            json = response.json()
            print(f"api-call-ok: total regions num:{len(json)}\n\n", json)
            
            # Display regions in a more readable format
            if json:
                print("\nAvailable Regions:")
                for region in json:
                    print(f"  - {region.get('name', 'N/A')} (Code: {region.get('code', 'N/A')})")
        else:
            # error example:
            # {'code': 401, 'msg': 'Invalid authorization', 'data': None, 'ts': 1759917250113, 'time': '2025-10-08 09:54:10', 'traceId': 'bcdef'}
            print(f"api-call-error: status={response.status_code}", response.json())
    except:
        error = traceback.format_exc()
        print(f"run-error: {error}")

if __name__ == "__main__":
    main()

"""
success example: 
[
  {
    'name': 'United States',
    'code': 'US'
  },
  {
    'name': 'United Kingdom',
    'code': 'UK'
  },
  {
    'name': 'Germany',
    'code': 'DE'
  },
  {
    'name': 'Japan',
    'code': 'JP'
  }
]
"""
