"""
Description:
Returns a paginated list of official workflow templates, sorted by creation time.

Documentation:
https://www.browseract.com/reception/integrations/api-workflow

curl -X GET 'https://api.browseract.com/v2/workflow/list-official-workflow-templates?keyword=&page=1&limit=10' -H 'Authorization: Bearer app-abcdefghijklmn'
"""

import traceback
import requests

def main():
    # API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
    authorization = "app-abcdefghijklmn"

    # Search keyword (optional)
    keyword = ""

    # Page number (minimum: 1, default: 1)
    page = 1

    # Number of items per page (minimum: 1, maximum: 500, default: 1)
    limit = 10

    try:
        headers = {
            "Authorization": f"Bearer {authorization}"
        }
        
        # Build query parameters
        params = {
            "page": page,
            "limit": limit
        }
        
        # Add keyword if provided
        if keyword:
            params["keyword"] = keyword
        
        api_url = "https://api.browseract.com/v2/workflow/list-official-workflow-templates"
        response = requests.get(
            api_url, headers=headers, params=params
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
            print(f"api-call-error: status={response.status_code}", response.json())
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
    'templateId': '16217357109956214',
    'name': 'E-commerce Product Scraper',
    'recommendDesc': 'Scrape product information from e-commerce websites',
    'detailUrl': 'https://www.browseract.com/template/16217357109956214'
  }, {
    'templateId': '16217357109956215',
    'name': 'Social Media Data Collector',
    'recommendDesc': 'Collect data from social media platforms',
    'detailUrl': 'https://www.browseract.com/template/16217357109956215'
  }],
  'total_pages': 1,
  'total_count': 2
}
"""
