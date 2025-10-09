"""
Description:
Return a paginated list of all tasks, sorted by creation time. 
Each task includes basic information such as status and creation time.

Documentation:
https://www.browseract.com/reception/integrations/api-agent

curl -X GET 'https://api.browseract.com/v2/agent/list-tasks?agent_id=&page=1&limit=10' -H 'Authorization: Bearer app-abcdefghijklmn'
"""

import traceback
import requests

def main():
    # API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
    authorization = "app-abcdefghijklmn"

    # Specify the agent ID to filter tasks. 
    # When provided, return only tasks associated with the specified agent. 
    # If omitted, return tasks from all agents.
    # You can copy it from: https://www.browseract.com/reception/agent-list
    agent_id = ""

    # Page number (minimum: 1, default: 1)
    page = 1

    # Number of items per page (minimum: 1, maximum: 500, default: 1)
    limit = 10

    try:
        headers = {
            "Authorization": f"Bearer {authorization}"
        }
        
        api_url = f"https://api.browseract.com/v2/agent/list-tasks?agent_id={agent_id}&page={page}&limit={limit}"
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
    'id': '16628193563511975',
    'task': 'open github,search LLM and save first 10 project name.',
    'output': {
      'string': None,
      'files': None
    },
    'status': 'created',
    'live_url_info': {
      'width': 1280,
      'height': 1024,
      'live_url': 'https://www.browseract.com/remote/abcde'
    },
    'live_url': 'https://www.browseract.com/remote/abcde',
    'profile_id': 'abcdef',
    'created_at': None,
    'finished_at': None,
    'task_failure_info': None,
    'agent_id': '15133850277314863'
  }],
  'total_pages': 1,
  'total_count': 1
}
"""