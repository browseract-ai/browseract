"""
Scenario 5: Use Callbacks for Task Completion

Business Need:
Set callback URL when starting a task, receive task completion notifications via callback instead of polling.

Use Cases:
- Need to asynchronously handle task completion events
- Avoid frequent API polling
- Integrate into existing systems

Usage Steps:
1. Modify API_KEY and WORKFLOW_ID below
2. Set CALLBACK_URL (your server's callback receiving address)
3. Run script: python scenarios/scenario_5_callback_based.py
4. Implement callback receiving endpoint on your server

Note:
- Callback URL must be a publicly accessible HTTPS address
- Callback endpoint must return 2xx status code within 30 seconds
- Callback payload structure is the same as Get Task API response
"""

import os
import traceback
import requests

# ============ Configuration Area - Please modify the following variables ============
# API Key, get from: https://www.browseract.com/reception/integrations
API_KEY = os.getenv("BROWSERACT_API_KEY", "app-abcdefghijklmn")

# Workflow ID, get from: https://www.browseract.com/reception/workflow-list
WORKFLOW_ID = 1234567890

# Workflow input parameters
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

# Callback URL - Your server's address to receive task completion notifications
# Note: Must be a publicly accessible HTTPS URL
CALLBACK_URL = "https://www.mydomain.com/api/task-callback"

# Status change callback URL (optional) - Receive task status change notifications
# If not set, only task completion notifications will be received
STATUS_CHANGE_CALLBACK_URL = "https://www.mydomain.com/api/task-status-callback"
# ================================================

API_BASE_URL = "https://api.browseract.com/v2/workflow"

def run_task(workflow_id, input_parameters, callback_url=None, status_change_callback_url=None):
    """Start a task (with callbacks)"""
    headers = {
        "Authorization": f"Bearer {API_KEY}"
    }
    
    data = {
        "workflow_id": workflow_id,
        "input_parameters": input_parameters,
        "save_browser_data": True,
    }
    
    # Set callback URLs
    if callback_url:
        data["callback_url"] = callback_url
        print(f"✅ Task completion callback set: {callback_url}")
    
    if status_change_callback_url:
        data["status_change_callback_url"] = status_change_callback_url
        print(f"✅ Status change callback set: {status_change_callback_url}")
    
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
    print("Scenario 5: Use Callbacks for Task Completion")
    print("=" * 60)
    
    print("\n📋 Callback Mechanism Explanation:")
    print("   1. Task completion callback (callback_url):")
    print("      - Trigger: When task completes, fails, or is canceled")
    print("      - Payload: Same as Get Task API response")
    print("   2. Status change callback (status_change_callback_url):")
    print("      - Trigger: When task status changes (running, paused, finished, canceled, failed)")
    print("      - Payload: Same as Get Task API response")
    print("\n⚠️ Callback Requirements:")
    print("   - Must be a publicly accessible HTTPS URL")
    print("   - Must return 2xx status code within 30 seconds")
    print("   - 3xx redirects are not supported")
    print("   - 5xx errors will be automatically retried (max 3 times)")
    print("=" * 60)
    
    try:
        # Check if callback URL is set
        if not CALLBACK_URL or CALLBACK_URL == "https://www.mydomain.com/api/task-callback":
            print("\n⚠️ Warning: Please set a valid CALLBACK_URL first")
            print("   Callback URL must be a publicly accessible HTTPS address")
            print("   Example: https://your-domain.com/api/task-callback")
            print("\n💡 Tip: You can use the following tools to test callbacks:")
            print("   - https://webhook.site (temporary testing)")
            print("   - ngrok (local development)")
            return
        
        # Start task
        print(f"\n🚀 Starting task (with callbacks)...")
        print(f"   Workflow ID: {WORKFLOW_ID}")
        print(f"   Input Parameters: {INPUT_PARAMETERS}")
        
        task_id = run_task(
            WORKFLOW_ID,
            INPUT_PARAMETERS,
            callback_url=CALLBACK_URL,
            status_change_callback_url=STATUS_CHANGE_CALLBACK_URL if STATUS_CHANGE_CALLBACK_URL != "https://www.mydomain.com/api/task-status-callback" else None
        )
        
        if task_id:
            print(f"\n✅ Task started successfully!")
            print(f"   Task ID: {task_id}")
            print(f"\n📡 Callback Configuration:")
            print(f"   - Task completion callback: {CALLBACK_URL}")
            if STATUS_CHANGE_CALLBACK_URL and STATUS_CHANGE_CALLBACK_URL != "https://www.mydomain.com/api/task-status-callback":
                print(f"   - Status change callback: {STATUS_CHANGE_CALLBACK_URL}")
            
            print(f"\n💡 Next Steps:")
            print(f"   1. Ensure your callback endpoint is ready to receive POST requests")
            print(f"   2. Callback payload format is the same as Get Task API response")
            print(f"   3. Your endpoint should return 2xx status code")
            print(f"   4. When task completes, your server will receive callback notification")
            
            print(f"\n📝 Callback Payload Example (when task completes):")
            print("""
{
  "id": "1234567890",
  "status": "finished",
  "output": {
    "string": "Task output text",
    "files": ["https://example.com/output/file.json"]
  },
  "steps": [...],
  "created_at": "2025-10-08T10:17:54Z",
  "finished_at": "2025-10-08T10:24:09Z",
  ...
}
            """)
        else:
            print("\n❌ Failed to start task")
        
    except Exception as e:
        error = traceback.format_exc()
        print(f"\n❌ Error occurred:\n{error}")

if __name__ == "__main__":
    main()
