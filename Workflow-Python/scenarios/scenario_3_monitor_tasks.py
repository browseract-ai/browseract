"""
Scenario 3: Monitor and Manage Tasks

Business Need:
Periodically check task list, monitor task status, and handle failed tasks.

Use Cases:
- Need to monitor execution of multiple tasks
- Need to handle failed tasks (retry or notify)
- Task management and operations scenarios

Usage Steps:
1. Modify API_KEY below
2. Optional: Set WORKFLOW_ID to monitor tasks of a specific workflow only
3. Run script: python scenarios/scenario_3_monitor_tasks.py
"""

import os
import time
import traceback
import requests
from datetime import datetime

# ============ Configuration Area - Please modify the following variables ============
# API Key, get from: https://www.browseract.com/reception/integrations
API_KEY = os.getenv("BROWSERACT_API_KEY", "app-abcdefghijklmn")

# Optional: Specify workflow ID, if empty then monitor tasks from all workflows
WORKFLOW_ID = ""  # Leave empty to monitor all tasks

# Monitoring configuration
MONITOR_INTERVAL = 10  # Check every 10 seconds
MONITOR_DURATION = 300  # Monitor for 5 minutes (300 seconds), set to 0 for continuous monitoring
PAGE_SIZE = 20  # Number of tasks to fetch per request

# Whether to automatically stop tasks running too long (seconds)
AUTO_STOP_TIMEOUT = 0  # Set to 0 to disable auto-stop
# ================================================

API_BASE_URL = "https://api.browseract.com/v2/workflow"

def list_tasks(workflow_id="", page=1, limit=20):
    """Get task list"""
    headers = {
        "Authorization": f"Bearer {API_KEY}"
    }
    
    api_url = f"{API_BASE_URL}/list-tasks?workflow_id={workflow_id}&page={page}&limit={limit}"
    response = requests.get(api_url, headers=headers)
    
    if response.status_code == 200:
        return response.json()
    else:
        print(f"⚠️ Failed to get task list: {response.json()}")
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

def stop_task(task_id):
    """Stop a task"""
    headers = {
        "Authorization": f"Bearer {API_KEY}"
    }
    
    api_url = f"{API_BASE_URL}/stop-task?task_id={task_id}"
    response = requests.put(api_url, headers=headers)
    
    return response.status_code == 200

def resume_task(task_id):
    """Resume a paused task"""
    headers = {
        "Authorization": f"Bearer {API_KEY}"
    }
    
    api_url = f"{API_BASE_URL}/resume-task?task_id={task_id}"
    response = requests.put(api_url, headers=headers)
    
    return response.status_code == 200

def format_time(timestamp_str):
    """Format timestamp"""
    if not timestamp_str:
        return "N/A"
    try:
        dt = datetime.fromisoformat(timestamp_str.replace('Z', '+00:00'))
        return dt.strftime("%Y-%m-%d %H:%M:%S")
    except:
        return timestamp_str

def calculate_duration(created_at, finished_at=None):
    """Calculate task duration"""
    if not created_at:
        return "N/A"
    
    try:
        start = datetime.fromisoformat(created_at.replace('Z', '+00:00'))
        end = datetime.fromisoformat(finished_at.replace('Z', '+00:00')) if finished_at else datetime.now(start.tzinfo)
        duration = (end - start).total_seconds()
        
        if duration < 60:
            return f"{int(duration)}s"
        elif duration < 3600:
            return f"{int(duration/60)}m"
        else:
            return f"{int(duration/3600)}h {int((duration%3600)/60)}m"
    except:
        return "N/A"

def main():
    print("=" * 60)
    print("Scenario 3: Monitor and Manage Tasks")
    print("=" * 60)
    
    workflow_filter = f"Workflow ID: {WORKFLOW_ID}" if WORKFLOW_ID else "All workflows"
    print(f"\nMonitoring scope: {workflow_filter}")
    print(f"Check interval: {MONITOR_INTERVAL} seconds")
    if MONITOR_DURATION > 0:
        print(f"Monitoring duration: {MONITOR_DURATION} seconds")
    else:
        print("Monitoring duration: Continuous (Press Ctrl+C to stop)")
    print("=" * 60)
    
    start_time = time.time()
    check_count = 0
    
    try:
        while True:
            check_count += 1
            elapsed_time = time.time() - start_time
            
            # Check if monitoring duration reached
            if MONITOR_DURATION > 0 and elapsed_time >= MONITOR_DURATION:
                print(f"\n⏰ Reached monitoring duration limit ({MONITOR_DURATION} seconds), stopping monitoring")
                break
            
            print(f"\n[{datetime.now().strftime('%H:%M:%S')}] Check #{check_count} (running for {int(elapsed_time)} seconds)")
            print("-" * 60)
            
            # Get task list
            tasks_data = list_tasks(WORKFLOW_ID, page=1, limit=PAGE_SIZE)
            
            if tasks_data is None:
                print("⚠️ Unable to get task list, waiting for next check...")
                time.sleep(MONITOR_INTERVAL)
                continue
            
            items = tasks_data.get('items', [])
            total_count = tasks_data.get('total_count', 0)
            
            print(f"📊 Total tasks: {total_count}, current page: {len(items)} tasks")
            
            if len(items) == 0:
                print("   No tasks")
            else:
                # Count task statuses
                status_count = {}
                for task in items:
                    status = task.get('status', 'unknown')
                    status_count[status] = status_count.get(status, 0) + 1
                
                print(f"\n📈 Status Summary:")
                for status, count in sorted(status_count.items()):
                    status_icon = {
                        'finished': '✅',
                        'running': '🔄',
                        'failed': '❌',
                        'created': '📝',
                        'paused': '⏸️',
                        'canceled': '🚫'
                    }.get(status, '❓')
                    print(f"   {status_icon} {status}: {count}")
                
                # Display recent tasks
                print(f"\n📋 Recent Tasks:")
                for i, task in enumerate(items[:5], 1):  # Only show first 5
                    task_id = task.get('id', 'N/A')
                    status = task.get('status', 'unknown')
                    created_at = task.get('created_at', '')
                    finished_at = task.get('finished_at', '')
                    
                    status_icon = {
                        'finished': '✅',
                        'running': '🔄',
                        'failed': '❌',
                        'created': '📝',
                        'paused': '⏸️',
                        'canceled': '🚫'
                    }.get(status, '❓')
                    
                    duration = calculate_duration(created_at, finished_at if finished_at else None)
                    
                    print(f"   {i}. {status_icon} [{status}] Task ID: {task_id}")
                    print(f"      Created At: {format_time(created_at)}")
                    if finished_at:
                        print(f"      Finished At: {format_time(finished_at)}")
                    print(f"      Duration: {duration}")
                    
                    # Check if auto-stop is needed
                    if AUTO_STOP_TIMEOUT > 0 and status == 'running':
                        try:
                            created = datetime.fromisoformat(created_at.replace('Z', '+00:00'))
                            running_time = (datetime.now(created.tzinfo) - created).total_seconds()
                            if running_time > AUTO_STOP_TIMEOUT:
                                print(f"      ⚠️ Task running too long ({int(running_time)}s), attempting to stop...")
                                if stop_task(task_id):
                                    print(f"      ✅ Task stopped")
                                else:
                                    print(f"      ❌ Failed to stop task")
                        except:
                            pass
                    
                    # Display failure information
                    if status == 'failed':
                        failure_info = task.get('task_failure_info')
                        if failure_info:
                            message = failure_info.get('message', '')
                            if len(message) > 100:
                                message = message[:100] + "..."
                            print(f"      Failure reason: {message}")
                    
                    print()
            
            # Wait for next check
            print("-" * 60)
            time.sleep(MONITOR_INTERVAL)
            
    except KeyboardInterrupt:
        print(f"\n\n⏹️ Monitoring stopped (checked {check_count} times)")
    except Exception as e:
        error = traceback.format_exc()
        print(f"\n❌ Error occurred:\n{error}")

if __name__ == "__main__":
    main()
