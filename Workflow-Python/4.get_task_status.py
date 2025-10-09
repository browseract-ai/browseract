"""
Description:
Returns only the current status of the task.

Documentation:
https://www.browseract.com/reception/integrations/api-workflow

curl -X GET 'https://api.browseract.com/v2/workflow/get-task-status?task_id=16429034742537847' -H 'Authorization: Bearer app-abcdefghijklmn'
"""

import traceback
import requests

def main():
    # API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
    authorization = "app-abcdefghijklmn"

    # The task ID, returned by endpoints like /run-task.
    # Please refer to "1.run_task.py"
    task_id = 16429034742537847

    try:
        headers = {
            "Authorization": f"Bearer {authorization}"
        }
        
        api_url = f"https://api.browseract.com/v2/workflow/get-task-status?task_id={task_id}"
        response = requests.get(
            api_url, headers=headers
        )

        if response.status_code == 200:
            # success example:
            # {'status': 'created'}
            # {'status': 'running'}
            # {'status': 'finished'}
            # {'status': 'failed'}
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
