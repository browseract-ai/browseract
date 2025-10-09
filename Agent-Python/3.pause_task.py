"""
Description:
Temporarily pause the agent task. Use the /resume-task endpoint to resume. Useful for manual intervention or inspection.

Documentation:
https://www.browseract.com/reception/integrations/api-agent

curl -X PUT 'https://api.browseract.com/v2/agent/pause-task?task_id=1234567890' -H 'Authorization: Bearer app-abcdefghijklmn'
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
        
        api_url = f"https://api.browseract.com/v2/agent/pause-task?task_id={task_id}"
        response = requests.put(
            api_url, headers=headers
        )

        if response.status_code == 200:
            # success example: No content will be returned
            # Note: Even if the task has been completed, this API will return 200 status_code
            print("api-call-ok:", response.text)
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
