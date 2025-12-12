"""
Description:
Resume a paused workflow task and continue execution from where it left off. 
This operation is only available for tasks that are currently in a paused state - other status tasks cannot be resumed.

Documentation:
https://www.browseract.com/reception/integrations/api-workflow

curl -X PUT 'https://api.browseract.com/v2/workflow/resume-task?task_id=1234567890' -H 'Authorization: Bearer app-abcdefghijklmn'
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
        
        api_url = f"https://api.browseract.com/v2/workflow/resume-task?task_id={task_id}"
        response = requests.put(
            api_url, headers=headers
        )

        if response.status_code == 200:
            # success example: No content will be returned
            print("api-call-ok:", response.text)
        else:
            # error example:
            # {'code': 401, 'msg': 'Invalid authorization', 'data': None, 'ts': 1759917250113, 'time': '2025-10-08 09:54:10', 'traceId': 'bcdef'}
            # {'code': 10112, 'msg': 'Task is not exist.', 'data': None, 'ts': 1759918566040, 'time': '2025-10-08 10:16:06', 'traceId': 'cdefg'}
            # {'code': 10127, 'msg': 'Task resume only use for paused task', 'data': None, 'ts': 1765522457238, 'time': '2025-12-12 06:54:17', 'traceId': 'cdefg'}
            print(f"api-call-error: status={response.status_code}", response.json())
    except:
        error = traceback.format_exc()
        print(f"run-error: {error}")

if __name__ == "__main__":
    main()
