# BrowserAct Workflow API - Scenario-Based Usage Guide

## 📋 Table of Contents

- [Why Scenario-Based Examples?](#why-scenario-based-examples)
- [Scenario: Run Task and Wait for Completion](#scenario-run-task-and-wait-for-completion)
- [Quick Start](#quick-start)

## Why Scenario-Based Examples?

This project provides individual API examples, which are very useful for understanding how each API works. However, in real-world development, developers typically need to:

1. **Combine multiple APIs** to complete a full business workflow
2. **Understand relationships between APIs**, such as how to go from running a task to getting results
3. **Handle common business scenarios**, such as polling task status

Scenario-based examples help new developers:
- Quickly understand the platform's workflow
- Copy code and modify just a few parameters to use it
- Learn best practices and common patterns

## Scenario: Run Task and Wait for Completion ⭐ Most Common

**Business Need**: Start a workflow task, wait for it to complete, and then get the results.

**Use Cases**:
- Need to synchronously wait for task completion
- Need to get the complete output results of the task
- Suitable for single task execution
- Perfect for new users to understand the complete workflow

**APIs Involved**:
- `run_task` - Start a task
- `get_task_status` - Poll task status
- `get_task` - Get final results

**Example Code**: See `scenarios/scenario_1_run_and_wait.py`

---

## Quick Start

### 1. Configure API Key

The scenario example requires configuring your API Key. There are two ways:

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

### 2. Get Workflow ID or Template ID

**Option A: Using Custom Workflows**
1. Visit: https://www.browseract.com/reception/workflow-list
2. Copy the workflow ID you want to use
3. Or run: `python 6.list_workflows.py` to get workflow IDs programmatically

**Option B: Using Official Templates** (Recommended for beginners)
1. Run: `python 11.list_official_workflow_templates.py` to see all available templates
2. Copy the `templateId` from the response
3. Optionally run: `python 12.get_region_list.py` to see available proxy regions

### 3. Run Scenario Example

```bash
# Run task and wait for completion
python scenarios/scenario_1_run_and_wait.py
```

## Using Official Templates

BrowserAct provides official workflow templates that are ready to use without creating your own workflows. Templates are perfect for:
- Quick prototyping and testing
- Common use cases (web scraping, data collection, etc.)
- Users who don't want to create custom workflows

**To use templates:**
1. List templates: Run `11.list_official_workflow_templates.py`
2. Get template details: Use the template ID with `9.run_task_by_template.py`
3. Configure proxy region: Run `12.get_region_list.py` to see available regions

**Note**: Template-based tasks (`run-task-by-template`) don't support browser profile reuse (`save_browser_data` and `profile_id`). Use custom workflows (`run-task`) if you need these features.

## Next Steps

- View [API Documentation](https://www.browseract.com/reception/integrations/api-workflow)
- View [Individual API Examples](../README.md#example-files)
- Try the [Quick Start Guide](../README.md#-quick-start-guide) in README.md
- For questions, contact support: support@browseract.com
