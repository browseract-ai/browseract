"""
Scenario 2: Run Template Task and Wait for Completion ⭐ Recommended for Beginners

Business Need:
Start a workflow task using an official template, wait for it to complete, and then get the results.

Use Cases:
- Need to synchronously wait for task completion
- Need to get the complete output results of the task
- Suitable for single task execution
- Perfect for users who want to use official templates without creating custom workflows

Usage Steps:
1. Modify API_KEY and WORKFLOW_TEMPLATE_ID below
2. Modify input_parameters according to the template requirements
3. Run script: python Scenarios-Python/scenario_2_run_template_and_wait.py
"""

import os
import time
import traceback
import json
import requests

# ============ Configuration Area - Please modify the following variables ============
# API Key, get from: https://www.browseract.com/reception/integrations
API_KEY = os.getenv("BROWSERACT_API_KEY", "app-abcdefghijklmn")

# Workflow Template ID, you can get it from:
# - Run: python Workflow-Python/11.list_official_workflow_templates.py
# - Or visit: https://www.browseract.com/template?platformType=0
WORKFLOW_TEMPLATE_ID = "1234567890"

# Workflow input parameters (modify according to the template definition)
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

def run_task_by_template(workflow_template_id, input_parameters):
    """Start a task using template"""
    headers = {
        "Authorization": f"Bearer {API_KEY}"
    }
    
    data = {
        "workflow_template_id": workflow_template_id,
        "input_parameters": input_parameters,
    }
    
    api_url = f"{API_BASE_URL}/run-task-by-template"
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
    try:
        response = requests.get(api_url, headers=headers, timeout=30)
        
        if response.status_code == 200:
            return response.json().get("status")
        else:
            print(f"⚠️ Failed to get task status: {response.json()}")
            return None
    except (requests.exceptions.SSLError, requests.exceptions.ConnectionError, 
            requests.exceptions.Timeout, requests.exceptions.RequestException) as e:
        # Network error, will retry in next polling cycle
        return None

def get_task(task_id):
    """Get detailed task information"""
    headers = {
        "Authorization": f"Bearer {API_KEY}"
    }
    
    api_url = f"{API_BASE_URL}/get-task?task_id={task_id}"
    try:
        response = requests.get(api_url, headers=headers, timeout=30)
        
        if response.status_code == 200:
            return response.json()
        else:
            print(f"⚠️ Failed to get task details: {response.json()}")
            return None
    except (requests.exceptions.SSLError, requests.exceptions.ConnectionError, 
            requests.exceptions.Timeout, requests.exceptions.RequestException) as e:
        print(f"⚠️ Network error while getting task details: {type(e).__name__}")
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
            # Network error or API error, continue waiting
            elapsed = int(elapsed_time)
            print(f"   Network error, retrying... (waited {elapsed} seconds)", end="\r")
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
    print("Scenario 2: Run Template Task and Wait for Completion")
    print("=" * 60)
    
    try:
        # Step 1: Start task using template
        print("\n📤 Step 1: Starting task using template...")
        print(f"   Template ID: {WORKFLOW_TEMPLATE_ID}")
        task_id = run_task_by_template(WORKFLOW_TEMPLATE_ID, INPUT_PARAMETERS)
        
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
            print("Task Result (JSON):")
            print("=" * 60)
            # Output complete task result as formatted JSON
            print(json.dumps(task_info, indent=2, ensure_ascii=False))
            print("=" * 60)
        else:
            print("⚠️ Unable to get task details")
            
    except Exception as e:
        error = traceback.format_exc()
        print(f"\n❌ Error occurred:\n{error}")

if __name__ == "__main__":
    main()
