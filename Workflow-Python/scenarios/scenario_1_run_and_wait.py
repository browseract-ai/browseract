"""
Scenario 1: Run Task and Wait for Completion ⭐ Most Common

Business Need:
Start a workflow task, wait for it to complete, and then get the results.

Use Cases:
- Need to synchronously wait for task completion
- Need to get the complete output results of the task
- Suitable for single task execution

Usage Steps:
1. Modify API_KEY and WORKFLOW_ID below
2. Modify input_parameters according to your workflow
3. Run script: python scenarios/scenario_1_run_and_wait.py
"""

import os
import time
import traceback
import requests

# ============ Configuration Area - Please modify the following variables ============
# API Key, get from: https://www.browseract.com/reception/integrations
API_KEY = os.getenv("BROWSERACT_API_KEY", "app-abcdefghijklmn")

# Workflow ID, get from: https://www.browseract.com/reception/workflow-list
WORKFLOW_ID = 1234567890

# Workflow input parameters (modify according to your workflow definition)
INPUT_PARAMETERS = [
    {
        "name": "target_url",
        "value": "https://www.google.com/search?q=iphone17",
    },
    {
        "name": "product_limit",
        "value": "10",
    }
]

# Polling configuration
POLL_INTERVAL = 5  # Check task status every 5 seconds
MAX_WAIT_TIME = 600  # Maximum wait time: 10 minutes (600 seconds)
# ================================================

API_BASE_URL = "https://api.browseract.com/v2/workflow"

def run_task(workflow_id, input_parameters):
    """Start a task"""
    headers = {
        "Authorization": f"Bearer {API_KEY}"
    }
    
    data = {
        "workflow_id": workflow_id,
        "input_parameters": input_parameters,
        "save_browser_data": True,
    }
    
    api_url = f"{API_BASE_URL}/run-task"
    response = requests.post(api_url, json=data, headers=headers)
    
    if response.status_code == 200:
        result = response.json()
        task_id = result["id"]
        print(f"✅ Task started, Task ID: {task_id}")
        if "profileId" in result:
            print(f"   Profile ID: {result['profileId']}")
        return task_id
    else:
        print(f"❌ Failed to start task: {response.json()}")
        return None

def get_task_status(task_id):
    """Get task status"""
    headers = {
        "Authorization": f"Bearer {API_KEY}"
    }
    
    api_url = f"{API_BASE_URL}/get-task-status?task_id={task_id}"
    response = requests.get(api_url, headers=headers)
    
    if response.status_code == 200:
        return response.json().get("status")
    else:
        print(f"⚠️ Failed to get task status: {response.json()}")
        return None

def get_task(task_id):
    """Get detailed task information"""
    headers = {
        "Authorization": f"Bearer {API_KEY}"
    }
    
    api_url = f"{API_BASE_URL}/get-task?task_id={task_id}"
    response = requests.get(api_url, headers=headers)
    
    if response.status_code == 200:
        return response.json()
    else:
        print(f"⚠️ Failed to get task details: {response.json()}")
        return None

def wait_for_task_completion(task_id):
    """Wait for task completion"""
    start_time = time.time()
    
    print(f"\n⏳ Waiting for task completion (max wait time: {MAX_WAIT_TIME} seconds)...")
    
    while True:
        # Check if timeout
        elapsed_time = time.time() - start_time
        if elapsed_time > MAX_WAIT_TIME:
            print(f"\n⏰ Wait timeout (waited {elapsed_time:.0f} seconds)")
            return None
        
        # Get task status
        status = get_task_status(task_id)
        
        if status is None:
            print("⚠️ Unable to get task status, continuing to wait...")
        elif status == "finished":
            print(f"\n✅ Task completed!")
            return "finished"
        elif status == "failed":
            print(f"\n❌ Task execution failed")
            return "failed"
        elif status == "canceled":
            print(f"\n🚫 Task canceled")
            return "canceled"
        else:
            # running, created, paused, etc.
            elapsed = int(elapsed_time)
            print(f"   Status: {status} (waited {elapsed} seconds)", end="\r")
        
        # Wait before checking again
        time.sleep(POLL_INTERVAL)

def main():
    print("=" * 60)
    print("Scenario 1: Run Task and Wait for Completion")
    print("=" * 60)
    
    try:
        # Step 1: Start task
        print("\n📤 Step 1: Starting task...")
        task_id = run_task(WORKFLOW_ID, INPUT_PARAMETERS)
        
        if task_id is None:
            print("❌ Unable to start task, exiting")
            return
        
        # Step 2: Wait for task completion
        print("\n📥 Step 2: Waiting for task completion...")
        final_status = wait_for_task_completion(task_id)
        
        # Step 3: Get task results
        print("\n📊 Step 3: Getting task results...")
        task_info = get_task(task_id)
        
        if task_info:
            print("\n" + "=" * 60)
            print("Task Details:")
            print("=" * 60)
            print(f"Task ID: {task_info.get('id')}")
            print(f"Status: {task_info.get('status')}")
            print(f"Created At: {task_info.get('created_at')}")
            print(f"Finished At: {task_info.get('finished_at')}")
            
            # Display output
            output = task_info.get('output', {})
            if output.get('string'):
                print(f"\nOutput Text:")
                print(output['string'])
            if output.get('files'):
                print(f"\nOutput Files:")
                for file_url in output['files']:
                    print(f"  - {file_url}")
            
            # Display step information
            steps = task_info.get('steps', [])
            if steps:
                print(f"\nExecution Steps ({len(steps)} steps):")
                for step in steps:
                    status_icon = "✅" if step.get('status') == 'succeed' else "❌"
                    print(f"  {status_icon} Step {step.get('step')}: {step.get('step_goal', '')[:50]}...")
            
            # Display failure information
            failure_info = task_info.get('task_failure_info')
            if failure_info:
                print(f"\n❌ Failure Reason:")
                print(f"   {failure_info.get('message', '')}")
            
            print("=" * 60)
        else:
            print("⚠️ Unable to get task details")
            
    except Exception as e:
        error = traceback.format_exc()
        print(f"\n❌ Error occurred:\n{error}")

if __name__ == "__main__":
    main()
