"""
Description:
Retrieve detailed information about the workflow task, including current status, completed steps, 
output from finished tasks, and other metadata.

Documentation:
https://www.browseract.com/reception/integrations/api-workflow

curl -X GET 'https://api.browseract.com/v2/workflow/get-task?task_id=16429034742537847' -H 'Authorization: Bearer app-abcdefghijklmn'
"""

import traceback
import requests

def main():
    # API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
    authorization = "app-abcdefghijklmn"

    # The task ID, returned by endpoints like /run-task.
    # Please refer to "1.run_task.py"
    task_id = 1234567890

    try:
        headers = {
            "Authorization": f"Bearer {authorization}"
        }
        
        api_url = f"https://api.browseract.com/v2/workflow/get-task?task_id={task_id}"
        response = requests.get(
            api_url, headers=headers
        )

        if response.status_code == 200:
            # success example: Please refer to the bottom of this file
            print("api-call-ok:", response.json())
        else:
            # error example:
            # {'code': 401, 'msg': 'Invalid authorization', 'data': None, 'ts': 1759917250113, 'time': '2025-10-08 09:54:10', 'traceId': 'bcdef'}
            # {'code': 10112, 'msg': 'Task is not exist.', 'data': None, 'ts': 1759918566040, 'time': '2025-10-08 10:16:06', 'traceId': 'cdefg'}
            print(f"api-call-error: status={response.status_code}", response.json())
    except:
        error = traceback.format_exc()
        print(f"run-error: {error}")

if __name__ == "__main__":
    main()

"""
success example:
{
  'id': '1234567890',
  'output': {
    'string': None,
    'files': None
  },
  'status': 'running',
  'steps': [{
    'id': '181973',
    'step': 1,
    'status': 'succeed',
    'evaluation_previous_goal': 'No previous action taken yet; this is the initial step. Ready to begin navigation as instructed.',
    'step_goal': 'Navigate to the exact URL provided by the user: https://www.google.com/search?q=iphone17, as required by the navigation rules.',
    'screenshots_url': 'The step screenshot url'
  }, {
    'id': '181978',
    'step': 2,
    'status': 'succeed',
    'evaluation_previous_goal': 'Navigation to the target URL was successful, but the page presented a Google reCAPTCHA challenge, indicating unusual traffic. Verdict: Success for navigation, but CAPTCHA must be bypassed to proceed.',
    'step_goal': 'Bypass the reCAPTCHA challenge using the bypass_captcha action to gain access to the search results page.',
    'screenshots_url': 'The step screenshot url'
  }],
  'live_url_info': {
    'width': 1280,
    'height': 1024,
    'live_url': 'https://www.browseract.com/remote/abcde'
  },
  'live_url': 'https://www.browseract.com/remote/abcde',
  'profile_id': 'cdefghi',
  'created_at': '2025-10-08T10:17:54Z',
  'finished_at': '2025-10-08T10:17:54Z',
  'task_failure_info': None,
  'workflow_id': '16217357109956214',
  'input_parameters': 'target_url=https://www.google.com/search?q=iphone17; product_limit=10'
}
"""