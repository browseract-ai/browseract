"""
Description:
Start a new agent task and return a task ID for progress tracking.

Documentation:
https://www.browseract.com/reception/integrations/api-agent

curl -X POST 'https://api.browseract.com/v2/agent/run-task' -H 'Authorization: Bearer app-abcdefghijklmn' -H 'Content-Type: application/json' -d '{"agent_id": "1234567890","task": "open github,search LLM and save first 10 project name.","save_browser_data": true,"profile_id": ""}'
"""

import traceback
import requests

def main():
    # API Key Required for API Call, generated from: https://www.browseract.com/reception/integrations
    authorization = "app-abcdefghijklmn"

    # agent ID, you can copy it from: https://www.browseract.com/reception/agent-list
    agent_id = 1234567890

    try:
        headers = {
            "Authorization": f"Bearer {authorization}"
        }
        
        # define API parameters
        data = {
            # The agent ID used to create and spawn a new task.
            "agent_id": agent_id,
            
            # The task description that you want the agent to perform, in natural language.
            "task": "open github,search LLM and save first 10 project name.",
            
            # Sensitive data required for task execution. For security and privacy purposes, this data will not be submitted to the LLM. 
            # Usage Example: {"https://github.com/login": {"login": "username","password": "xxxx"}}, 
            # where the "login" and "password" values are only used when accessing the URL "https://github.com/login"
            "secrets": {"https://github.com/login": {"login": "username","password": "xxxx"}},
            
            # Specify whether a profile_id should be returned in the response upon successful task submission. 
            # The profile stores browser session data, including cookies and other browsing state, that is generated during task execution.
            "save_browser_data": True,
            
            # The browser profile to use for this agent task. 
            # Browser profiles store session data, such as cookies, and other browsing state, that can be reused across tasks.
            # Note: if profile_id isn't provided, a random one will be generated during task execution.
            "profile_id": "",
        }
        
        api_url = "https://api.browseract.com/v2/agent/run-task"
        response = requests.post(
            api_url, json=data, headers=headers
        )

        if response.status_code == 200:
            # success example:
            # {'id': '12425895140306551', 'profileId': 'abcde'}
            print("api-call-ok:", response.json())
            
            task_id = response.json()["id"]
            # Polling the task status until the task is completed or timed out.
            # Please refer to "5.get_task.py" or "6.get_task_status.py"
        else:
            # error example:
            # {'code': 401, 'msg': 'Invalid authorization', 'data': None, 'ts': 1759917250113, 'time': '2025-10-08 09:54:10', 'traceId': 'bcdef'}
            # {'code': 10010, 'msg': 'Agent does not exist', 'data': None, 'ts': 1759997243432, 'time': '2025-10-09 08:07:23', 'traceId': 'cdefg'}
            print(f"api-call-error: status={response.status_code}", response.json())
    except:
        error = traceback.format_exc()
        print(f"run-error: {error}")

if __name__ == "__main__":
    main()
