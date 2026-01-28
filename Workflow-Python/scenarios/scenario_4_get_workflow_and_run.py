"""
Scenario 4: Get Workflow Info and Run

Business Need:
First view workflow details (including required parameters), then run tasks based on this information.

Use Cases:
- Not sure what parameters the workflow needs
- Need to dynamically get workflow configuration
- Development and debugging phase

Usage Steps:
1. Modify API_KEY below
2. Optional: Set WORKFLOW_ID, if empty then list all workflows first
3. Run script: python scenarios/scenario_4_get_workflow_and_run.py
"""

import os
import traceback
import requests

# ============ Configuration Area - Please modify the following variables ============
# API Key, get from: https://www.browseract.com/reception/integrations
API_KEY = os.getenv("BROWSERACT_API_KEY", "app-abcdefghijklmn")

# Workflow ID, if empty then list all workflows for user to choose
WORKFLOW_ID = None  # Set to None or "" to list workflows first

# If WORKFLOW_ID is set, these are the input parameters to run
# If WORKFLOW_ID is empty, script will show workflow info first, then prompt for input parameters
INPUT_PARAMETERS = {
    "target_url": "https://www.google.com/search?q=iphone17",
    "product_limit": "10"
}
# ================================================

API_BASE_URL = "https://api.browseract.com/v2/workflow"

def list_workflows(page=1, limit=20):
    """Get workflow list"""
    headers = {
        "Authorization": f"Bearer {API_KEY}"
    }
    
    api_url = f"{API_BASE_URL}/list-workflows?page={page}&limit={limit}"
    response = requests.get(api_url, headers=headers)
    
    if response.status_code == 200:
        return response.json()
    else:
        print(f"⚠️ Failed to get workflow list: {response.json()}")
        return None

def get_workflow(workflow_id):
    """Get workflow details"""
    headers = {
        "Authorization": f"Bearer {API_KEY}"
    }
    
    api_url = f"{API_BASE_URL}/get-workflow?workflow_id={workflow_id}"
    response = requests.get(api_url, headers=headers)
    
    if response.status_code == 200:
        return response.json()
    else:
        print(f"⚠️ Failed to get workflow details: {response.json()}")
        return None

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

def main():
    print("=" * 60)
    print("Scenario 4: Get Workflow Info and Run")
    print("=" * 60)
    
    try:
        workflow_id = WORKFLOW_ID
        
        # Step 1: If no workflow ID specified, list all workflows first
        if not workflow_id:
            print("\n📋 Step 1: Getting workflow list...")
            workflows_data = list_workflows()
            
            if workflows_data is None:
                print("❌ Unable to get workflow list, exiting")
                return
            
            items = workflows_data.get('items', [])
            total_count = workflows_data.get('total_count', 0)
            
            print(f"\nFound {total_count} workflows:\n")
            
            if len(items) == 0:
                print("❌ No available workflows")
                return
            
            # Display workflow list
            for i, workflow in enumerate(items, 1):
                print(f"{i}. {workflow.get('name', 'N/A')}")
                print(f"   ID: {workflow.get('id')}")
                print(f"   Description: {workflow.get('description', 'N/A')}")
                print(f"   Created At: {workflow.get('create_at', 'N/A')}")
                print()
            
            # Use first workflow (in actual use, can let user choose)
            if len(items) > 0:
                workflow_id = items[0].get('id')
                print(f"💡 Using first workflow: {workflow_id}\n")
            else:
                print("❌ No available workflows")
                return
        
        # Step 2: Get workflow details
        print(f"📖 Step 2: Getting workflow details (ID: {workflow_id})...")
        workflow_info = get_workflow(workflow_id)
        
        if workflow_info is None:
            print("❌ Unable to get workflow info, exiting")
            return
        
        print("\n" + "=" * 60)
        print("Workflow Information:")
        print("=" * 60)
        print(f"Name: {workflow_info.get('name', 'N/A')}")
        print(f"ID: {workflow_info.get('id')}")
        print(f"Description: {workflow_info.get('description', 'N/A')}")
        print(f"Created At: {workflow_info.get('create_at', 'N/A')}")
        print(f"Published At: {workflow_info.get('publish_at', 'N/A')}")
        
        # Display input parameters
        input_params = workflow_info.get('input_parameters', [])
        print(f"\n📝 Required Input Parameters ({len(input_params)} parameters):")
        
        if len(input_params) == 0:
            print("   This workflow does not require input parameters")
        else:
            for param in input_params:
                param_name = param.get('name', 'N/A')
                default_enabled = param.get('default_enabled', False)
                default_mark = "(has default value)" if default_enabled else ""
                print(f"   - {param_name} {default_mark}")
        
        print("=" * 60)
        
        # Step 3: Check input parameters
        print("\n🔍 Step 3: Checking input parameters...")
        
        required_params = {p.get('name') for p in input_params if not p.get('default_enabled', False)}
        provided_params = set(INPUT_PARAMETERS.keys())
        
        missing_params = required_params - provided_params
        extra_params = provided_params - {p.get('name') for p in input_params}
        
        if missing_params:
            print(f"⚠️ Missing required parameters: {', '.join(missing_params)}")
            print("   These parameters have no default values and must be provided")
        
        if extra_params:
            print(f"ℹ️ Extra parameters (workflow may not use): {', '.join(extra_params)}")
        
        if not missing_params:
            print("✅ All required parameters are provided")
        
        # Step 4: Run task
        print(f"\n🚀 Step 4: Running task...")
        print(f"   Workflow ID: {workflow_id}")
        print(f"   Input Parameters: {INPUT_PARAMETERS}")
        
        task_id = run_task(workflow_id, INPUT_PARAMETERS)
        
        if task_id:
            print(f"\n✅ Task started successfully!")
            print(f"   Task ID: {task_id}")
            print(f"\n💡 Tip: You can check task status using:")
            print(f"   - Run scenario_1_run_and_wait.py to wait for task completion")
            print(f"   - Run 3.get_task.py to view task details")
            print(f"   - Run 4.get_task_status.py to check task status")
        else:
            print("\n❌ Failed to start task")
        
    except Exception as e:
        error = traceback.format_exc()
        print(f"\n❌ Error occurred:\n{error}")

if __name__ == "__main__":
    main()
