"""
Description:
Retrieve detailed information about the agent task, including current status, completed steps, 
output from finished tasks, and other metadata.

Documentation:
https://www.browseract.com/reception/integrations/api-agent

curl -X GET 'https://api.browseract.com/v2/agent/get-task?task_id=16429034742537847' -H 'Authorization: Bearer app-abcdefghijklmn'
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
        
        api_url = f"https://api.browseract.com/v2/agent/get-task?task_id={task_id}"
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
  'id': '16429034742537847',
  'task': 'open github,search LLM and save first 10 project name.',
  'output': {
    'string': 'Task complete! I searched GitHub for \'LLM\' and extracted the first 10 project names as requested. The names are saved in results.json, and progress is tracked in todo.md. Please find both files attached for your review.\n\nAttachments:\n\nresults.json:\n{"repositories": ["simonw/llm", "rustformers/llm", "karpathy/LLM101n", "rasbt/LLMs-from-scratch", "mlabonne/llm-course", "Shubhamsaboo/awesome-llm-apps", "Mintplex-Labs/anything-llm", "llmware-ai/llmware", "karpathy/llm.c", "Mooler0410/LLMsPracticalGuide"]}\n',
    'files': ['Task output file url']
  },
  'status': 'finished',
  'steps': [{
    'id': '184549',
    'step': 1,
    'status': 'succeed',
    'evaluation_previous_goal': 'Initial step; no previous action to evaluate.',
    'step_goal': "Navigate to https://github.com, search for 'LLM', and extract the first 10 project names from the results page.",
    'screenshots_url': None
  }, {
    'id': '184551',
    'step': 2,
    'status': 'succeed',
    'evaluation_previous_goal': 'Successfully navigated to the GitHub homepage. Verdict: Success.',
    'step_goal': "Input 'LLM' into the search bar (index 12) and trigger the search to reach the results page.",
    'screenshots_url': 'Step screenshot file url'
  }, {
    'id': '184554',
    'step': 3,
    'status': 'succeed',
    'evaluation_previous_goal': "Successfully reached the search results page for 'LLM' on GitHub. Verdict: Success.",
    'step_goal': 'Extract the first 10 project names from the search results and save them in results.json, then update todo.md to reflect progress.',
    'screenshots_url': 'Step screenshot file url'
  }, {
    'id': '184556',
    'step': 4,
    'status': 'succeed',
    'evaluation_previous_goal': 'Successfully extracted the first 10 repository names and updated todo.md, but results.json has not yet been created. Verdict: Partial success.',
    'step_goal': 'Write the extracted repository names to results.json as a JSON array and update todo.md to reflect that 10/10 items have been collected and saved.',
    'screenshots_url': 'Step screenshot file url'
  }, {
    'id': '184557',
    'step': 5,
    'status': 'succeed',
    'evaluation_previous_goal': 'All steps have been completed successfully: search performed, 10 project names extracted, results saved, and progress tracked in todo.md. Verdict: Success.',
    'step_goal': 'Prepare to call done, summarizing the results and providing results.json and todo.md to the user for download and review.',
    'screenshots_url': 'Step screenshot file url'
  }],
  'live_url_info': {
    'width': 1280,
    'height': 1024,
    'live_url': 'https://www.browseract.com/remote/aaaaa'
  },
  'live_url': 'https://www.browseract.com/remote/aaaaa',
  'profile_id': 'abcde',
  'created_at': '2025-10-09T08:08:35Z',
  'finished_at': '2025-10-09T08:09:55Z',
  'task_failure_info': None,
  'task_gif_url': None,
  'agent_id': '14774755173139766'
}
"""