"""
Description:
Returns a paginated list of all tasks, sorted by creation time. 
Each task includes basic information such as status and creation time.

Documentation:
https://www.browseract.com/reception/integrations/api-workflow

curl -X GET 'https://api.browseract.com/v2/workflow/list-tasks?workflow_id=&page=1&limit=10' -H 'Authorization: Bearer app-abcdefghijklmn'
"""

import traceback
import requests

def main():
    # API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
    authorization = "app-abcdefghijklmn"

    # Specify the workflow ID to filter tasks. 
    # When provided, return only tasks associated with the specified workflow. 
    # If omitted, return tasks from all workflows.
    # You can copy it from: https://www.browseract.com/reception/workflow-list
    workflow_id = ""

    # Page number (minimum: 1, default: 1)
    page = 1

    # Number of items per page (minimum: 1, maximum: 500, default: 1)
    limit = 10

    try:
        headers = {
            "Authorization": f"Bearer {authorization}"
        }
        
        api_url = f"https://api.browseract.com/v2/workflow/list-tasks?workflow_id={workflow_id}&page={page}&limit={limit}"
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
    'id': '16429034742537847',
    'output': {
      'string': '',
      'files': ['task output file url']
    },
    'status': 'finished',
    'live_url_info': {
      'width': 1280,
      'height': 1024,
      'live_url': 'https://www.browseract.com/remote/abc'
    },
    'live_url': 'https://www.browseract.com/remote/abc',
    'profile_id': 'abcdefg',
    'created_at': '2025-10-08T10:17:54Z',
    'finished_at': '2025-10-08T10:24:09Z',
    'task_failure_info': None,
    'workflow_id': '16217357109956214',
    'input_parameters': 'target_url=https://www.google.com/search?q=iphone17; product_limit=10'
  }, {
    'id': '16426188022264951',
    'output': {
      'string': '',
      'files': ['task output file url']
    },
    'status': 'finished',
    'live_url_info': {
      'width': 1280,
      'height': 1024,
      'live_url': 'https://www.browseract.com/remote/abc'
    },
    'live_url': 'https://www.browseract.com/remote/abc',
    'profile_id': 'abcdefg',
    'created_at': '2025-10-08T09:55:14Z',
    'finished_at': '2025-10-08T10:00:01Z',
    'task_failure_info': None,
    'workflow_id': '16217357109956214',
    'input_parameters': 'target_url=https://www.google.com/search?q=iphone17; product_limit=10'
  }, {
    'id': '16425895140306551',
    'output': {
      'string': '',
      'files': None
    },
    'status': 'failed',
    'live_url_info': {
      'width': 1280,
      'height': 1024,
      'live_url': 'https://www.browseract.com/remote/abc'
    },
    'live_url': 'https://www.browseract.com/remote/abc',
    'profile_id': 'abcdefg',
    'created_at': '2025-10-08T09:53:00Z',
    'finished_at': '2025-10-08T09:55:14Z',
    'task_failure_info': {
      'code': 1011,
      'message': 'Navigation to https://www.google.com/search?q=iphone17 failed because the page is blocked by a persistent Google reCAPTCHA challenge. CAPTCHA bypass was attempted but did not succeed, and the search results remain inaccessible. No login was required, but access is completely blocked. Task marked as unsuccessful per navigation rules.'
    },
    'workflow_id': '16217357109956214',
    'input_parameters': 'target_url=https://www.google.com/search?q=iphone17; product_limit=10'
  }],
  'total_pages': 1,
  'total_count': 3
}
"""