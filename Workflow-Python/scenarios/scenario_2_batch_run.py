"""
Scenario 2: Batch Run Tasks

Business Need:
Run the same workflow with multiple different input parameters in batch.

Use Cases:
- Need to process a large number of similar tasks
- Data collection, batch testing scenarios
- Can execute asynchronously, don't need to wait for results immediately

Usage Steps:
1. Modify API_KEY and WORKFLOW_ID below
2. Modify BATCH_INPUTS list, add data you want to process in batch
3. Run script: python scenarios/scenario_2_batch_run.py
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

# Batch input data list
# Each element represents input parameters for one task
BATCH_INPUTS = [
    {
        "target_url": "https://www.google.com/search?q=iphone17",
        "product_limit": "10"
    },
    {
        "target_url": "https://www.google.com/search?q=iphone18",
        "product_limit": "20"
    },
    {
        "target_url": "https://www.google.com/search?q=iphone19",
        "product_limit": "15"
    },
]

# Whether to wait for all tasks to complete (True: wait and show results, False: only start tasks)
WAIT_FOR_COMPLETION = False

# If waiting for completion, polling configuration
POLL_INTERVAL = 5  # Check task status every 5 seconds
MAX_WAIT_TIME = 600  # Maximum wait time per task: 10 minutes
# ================================================

API_BASE_URL = "https://api.browseract.com/v2/workflow"

def run_task(workflow_id, input_parameters):
    """Start a task"""
    headers = {
        "Authorization": f"Bearer {API_KEY}"
    }
    
    data = {
        "workflow_id": workflow_id,
        "input_parameters": [
            {"name": k, "value": str(v)} for k, v in input_parameters.items()
        ],
        "save_browser_data": True,
    }
    
    api_url = f"{API_BASE_URL}/run-task"
    response = requests.post(api_url, json=data, headers=headers)
    
    if response.status_code == 200:
        result = response.json()
        return result["id"]
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
    return None

def wait_for_task_completion(task_id, max_wait_time):
    """Wait for task completion"""
    start_time = time.time()
    
    while True:
        elapsed_time = time.time() - start_time
        if elapsed_time > max_wait_time:
            return None
        
        status = get_task_status(task_id)
        
        if status in ["finished", "failed", "canceled"]:
            return status
        
        time.sleep(POLL_INTERVAL)

def main():
    print("=" * 60)
    print("Scenario 2: Batch Run Tasks")
    print("=" * 60)
    
    try:
        print(f"\n📋 Preparing to run {len(BATCH_INPUTS)} tasks in batch...\n")
        
        # Step 1: Batch start tasks
        task_results = []
        for i, input_data in enumerate(BATCH_INPUTS, 1):
            print(f"📤 [{i}/{len(BATCH_INPUTS)}] Starting task...")
            print(f"   Input parameters: {input_data}")
            
            task_id = run_task(WORKFLOW_ID, input_data)
            
            if task_id:
                task_results.append({
                    "index": i,
                    "task_id": task_id,
                    "input": input_data,
                    "status": "created"
                })
                print(f"   ✅ Task started, Task ID: {task_id}\n")
            else:
                task_results.append({
                    "index": i,
                    "task_id": None,
                    "input": input_data,
                    "status": "failed_to_start"
                })
                print(f"   ❌ Failed to start task\n")
            
            # Avoid requests too fast, add slight delay
            if i < len(BATCH_INPUTS):
                time.sleep(1)
        
        # Display startup summary
        print("=" * 60)
        print("Task Startup Summary:")
        print("=" * 60)
        success_count = sum(1 for r in task_results if r["task_id"])
        print(f"Successfully started: {success_count}/{len(BATCH_INPUTS)}")
        print(f"\nTask List:")
        for result in task_results:
            status_icon = "✅" if result["task_id"] else "❌"
            print(f"  {status_icon} Task #{result['index']}: Task ID = {result['task_id']}")
        
        # Step 2: If waiting for completion is enabled, wait and get results
        if WAIT_FOR_COMPLETION and success_count > 0:
            print("\n" + "=" * 60)
            print("Waiting for tasks to complete...")
            print("=" * 60)
            
            for result in task_results:
                if result["task_id"] is None:
                    continue
                
                task_id = result["task_id"]
                print(f"\n⏳ Waiting for task #{result['index']} (Task ID: {task_id})...")
                
                final_status = wait_for_task_completion(task_id, MAX_WAIT_TIME)
                result["final_status"] = final_status or "timeout"
                
                if final_status:
                    task_info = get_task(task_id)
                    if task_info:
                        result["task_info"] = task_info
                        output = task_info.get('output', {})
                        result["output"] = output
            
            # Display final results
            print("\n" + "=" * 60)
            print("Task Execution Results:")
            print("=" * 60)
            
            for result in task_results:
                if result["task_id"] is None:
                    continue
                
                print(f"\nTask #{result['index']} (Task ID: {result['task_id']}):")
                print(f"  Status: {result.get('final_status', 'unknown')}")
                
                if result.get("task_info"):
                    output = result.get("output", {})
                    if output.get('string'):
                        print(f"  Output: {output['string'][:100]}...")
                    if output.get('files'):
                        print(f"  File count: {len(output['files'])}")
        else:
            print("\n💡 Tip: All tasks have been started.")
            print("   To check task status, use list_tasks API or run scenario_3_monitor_tasks.py")
            print(f"\nTask ID List:")
            for result in task_results:
                if result["task_id"]:
                    print(f"  - {result['task_id']}")
        
    except Exception as e:
        error = traceback.format_exc()
        print(f"\n❌ Error occurred:\n{error}")

if __name__ == "__main__":
    main()
