# BrowserAct Workflow API - Scenario-Based Usage Guide

## 📋 Table of Contents

- [Why Scenario-Based Examples?](#why-scenario-based-examples)
- [Common Use Cases](#common-use-cases)
- [Quick Start](#quick-start)

## Why Scenario-Based Examples?

This project provides individual API examples, which are very useful for understanding how each API works. However, in real-world development, developers typically need to:

1. **Combine multiple APIs** to complete a full business workflow
2. **Understand relationships between APIs**, such as how to go from running a task to getting results
3. **Handle common business scenarios**, such as polling task status, batch processing, etc.

Scenario-based examples help new developers:
- Quickly understand the platform's workflow
- Copy code and modify just a few parameters to use it
- Learn best practices and common patterns

## Common Use Cases

### Scenario 1: Run Task and Wait for Completion ⭐ Most Common

**Business Need**: Start a workflow task, wait for it to complete, and then get the results.

**Use Cases**:
- Need to synchronously wait for task completion
- Need to get the complete output results of the task
- Suitable for single task execution

**APIs Involved**:
- `run_task` - Start a task
- `get_task_status` - Poll task status
- `get_task` - Get final results

**Example Code**: See `scenarios/scenario_1_run_and_wait.py`

---

### Scenario 2: Batch Run Tasks

**Business Need**: Run the same workflow with multiple different input parameters in batch.

**Use Cases**:
- Need to process a large number of similar tasks
- Data collection, batch testing scenarios
- Can execute asynchronously, don't need to wait for results immediately

**APIs Involved**:
- `list_workflows` - Get workflow list (optional)
- `run_task` - Batch start tasks
- `list_tasks` - View batch task status

**Example Code**: See `scenarios/scenario_2_batch_run.py`

---

### Scenario 3: Monitor and Manage Tasks

**Business Need**: Periodically check task list, monitor task status, and handle failed tasks.

**Use Cases**:
- Need to monitor execution of multiple tasks
- Need to handle failed tasks (retry or notify)
- Task management and operations scenarios

**APIs Involved**:
- `list_tasks` - Get task list
- `get_task` - Get task details
- `stop_task` - Stop abnormal tasks (optional)
- `resume_task` - Resume paused tasks (optional)

**Example Code**: See `scenarios/scenario_3_monitor_tasks.py`

---

### Scenario 4: Get Workflow Info and Run

**Business Need**: First view workflow details (including required parameters), then run tasks based on this information.

**Use Cases**:
- Not sure what parameters the workflow needs
- Need to dynamically get workflow configuration
- Development and debugging phase

**APIs Involved**:
- `list_workflows` - List all workflows
- `get_workflow` - Get workflow details (including input parameters)
- `run_task` - Run task with correct parameters

**Example Code**: See `scenarios/scenario_4_get_workflow_and_run.py`

---

### Scenario 5: Use Callbacks for Task Completion

**Business Need**: Set callback URL when starting a task, receive task completion notifications via callback instead of polling.

**Use Cases**:
- Need to asynchronously handle task completion events
- Avoid frequent API polling
- Integrate into existing systems

**APIs Involved**:
- `run_task` - Start task (set callback_url)
- Implement callback receiving endpoint (need to implement yourself)

**Example Code**: See `scenarios/scenario_5_callback_based.py`

---

## Quick Start

### 1. Configure API Key

All scenario examples require configuring your API Key. There are two ways:

**Method 1: Directly modify variables in code**
```python
API_KEY = "your-api-key-here"
```

**Method 2: Use environment variables (Recommended)**
```bash
# Windows PowerShell
$env:BROWSERACT_API_KEY="your-api-key-here"

# Linux/Mac
export BROWSERACT_API_KEY="your-api-key-here"
```

### 2. Get Workflow ID

1. Visit: https://www.browseract.com/reception/workflow-list
2. Copy the workflow ID you want to use

### 3. Run Scenario Examples

```bash
# Scenario 1: Run task and wait for completion
python scenarios/scenario_1_run_and_wait.py

# Scenario 2: Batch run tasks
python scenarios/scenario_2_batch_run.py

# Scenario 3: Monitor tasks
python scenarios/scenario_3_monitor_tasks.py

# Scenario 4: Get workflow info and run
python scenarios/scenario_4_get_workflow_and_run.py

# Scenario 5: Use callbacks
python scenarios/scenario_5_callback_based.py
```

## Scenario Selection Guide

| Scenario | Use Case | Complexity |
|----------|----------|------------|
| Scenario 1 | Single task, need to synchronously wait for results | ⭐ Simple |
| Scenario 2 | Batch process multiple tasks | ⭐⭐ Medium |
| Scenario 3 | Need to monitor and manage multiple tasks | ⭐⭐⭐ Complex |
| Scenario 4 | Not sure about workflow parameters | ⭐ Simple |
| Scenario 5 | Need async processing, integrate existing system | ⭐⭐ Medium |

## Next Steps

- View [API Documentation](https://www.browseract.com/reception/integrations/api-workflow)
- View [Individual API Examples](../README.md#example-files)
- For questions, contact support: support@browseract.com
