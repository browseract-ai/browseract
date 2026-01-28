# BrowserAct-Workflow-API-Python-Demos

Note: this code runs successfully in Python version 3.13.5  

API documentation: https://www.browseract.com/reception/integrations/api-workflow  
Site: https://www.browseract.com   

## Environment Requirements

- Python 3.8 or higher
- pip (comes with Python)

## Installation

1. Install Python from [python.org](https://www.python.org/downloads/)
2. Verify installation:
   ```bash
   python --version
   pip --version
   ```

## Project Setup

1. Navigate to the Workflow-Python directory:
   ```bash
   cd Workflow-Python
   ```

2. Install dependencies:
   ```bash
   pip install -r requirements.txt
   ```

## Running Examples

### 🎯 Scenario-Based Examples (Recommended for New Users)

If you're new to BrowserAct API, we recommend starting with **scenario-based examples** that demonstrate how to combine multiple APIs to accomplish real-world use cases:

📖 **View Scenario Guide**: [SCENARIOS.md](./SCENARIOS.md)

**Quick Start with Scenario Examples**:
```bash
# Scenario 1: Run task and wait for completion (Most Common)
python scenarios/scenario_1_run_and_wait.py

# Scenario 2: Batch run tasks
python scenarios/scenario_2_batch_run.py

# Scenario 3: Monitor and manage tasks
python scenarios/scenario_3_monitor_tasks.py

# Scenario 4: Get workflow info and run
python scenarios/scenario_4_get_workflow_and_run.py

# Scenario 5: Use callbacks for task completion
python scenarios/scenario_5_callback_based.py
```

### 📚 Individual API Examples (Learn Specific API Usage)

If you want to understand how each specific API works, you can run the following examples:

```bash
# Run specific example
python 1.run_task.py
python 2.stop_task.py
python 3.get_task.py
python 4.get_task_status.py
python 5.list_tasks.py
python 6.list_workflows.py
python 7.get_workflow.py
python 8.resume_task.py
python 9.run_task_by_template.py
python 11.list_official_workflow_templates.py
python 12.get_region_list.py
```

### 🚀 Quick Start Guide

**Option 1: Using Custom Workflows**
1. Get your workflow ID: Run `6.list_workflows.py` or visit https://www.browseract.com/reception/workflow-list
2. Get workflow details: Run `7.get_workflow.py` to see required input parameters
3. Run the task: Run `1.run_task.py` with your workflow ID and parameters
4. Check status: Run `3.get_task.py` or `4.get_task_status.py` to monitor progress

**Option 2: Using Official Templates** (Easier for beginners)
1. List available templates: Run `11.list_official_workflow_templates.py` to see all templates
2. Get region list: Run `12.get_region_list.py` to see available proxy regions
3. Run template task: Run `9.run_task_by_template.py` with a template ID from step 1
4. Check status: Run `3.get_task.py` or `4.get_task_status.py` to monitor progress

**Recommended**: Start with **Scenario 1** (`scenarios/scenario_1_run_and_wait.py`) for a complete example that combines multiple APIs.

## Project Structure

```
Workflow-Python/
├── README.md                      # This file
├── SCENARIOS.md                   # Scenario-based usage guide
├── requirements.txt               # Python dependencies
├── scenarios/                     # Scenario-based examples directory
│   ├── scenario_1_run_and_wait.py           # Scenario 1: Run task and wait for completion
│   ├── scenario_2_batch_run.py              # Scenario 2: Batch run tasks
│   ├── scenario_3_monitor_tasks.py          # Scenario 3: Monitor and manage tasks
│   ├── scenario_4_get_workflow_and_run.py   # Scenario 4: Get workflow info and run
│   └── scenario_5_callback_based.py         # Scenario 5: Use callbacks for task completion
├── 1.run_task.py                  # Start a new workflow task
├── 2.stop_task.py                 # Permanently terminate a task
├── 3.get_task.py                  # Get detailed task information
├── 4.get_task_status.py           # Get task status only
├── 5.list_tasks.py                # List all workflow tasks
├── 6.list_workflows.py            # List all workflows
├── 7.get_workflow.py              # Get workflow details
├── 8.resume_task.py               # Resume a paused task
├── 9.run_task_by_template.py     # Start a new workflow task using template
├── 11.list_official_workflow_templates.py  # List official workflow templates
└── 12.get_region_list.py         # Get supported region list
```

## Example Files

### Task Management

| File | Description | API Endpoint |
|------|-------------|--------------|
| `1.run_task.py` | Start a new workflow task (custom workflow) | POST /v2/workflow/run-task |
| `9.run_task_by_template.py` | Start a new workflow task using official template | POST /v2/workflow/run-task-by-template |
| `2.stop_task.py` | Permanently terminate a task | PUT /v2/workflow/stop-task |
| `8.resume_task.py` | Resume a paused task | PUT /v2/workflow/resume-task |
| `3.get_task.py` | Get detailed task information | GET /v2/workflow/get-task |
| `4.get_task_status.py` | Get task status only | GET /v2/workflow/get-task-status |
| `5.list_tasks.py` | List all workflow tasks | GET /v2/workflow/list-tasks |

### Workflow Management

| File | Description | API Endpoint |
|------|-------------|--------------|
| `6.list_workflows.py` | List all custom workflows | GET /v2/workflow/list-workflows |
| `7.get_workflow.py` | Get workflow details | GET /v2/workflow/get-workflow |
| `11.list_official_workflow_templates.py` | List official workflow templates | GET /v2/workflow/list-official-workflow-templates |

### Utility APIs

| File | Description | API Endpoint |
|------|-------------|--------------|
| `12.get_region_list.py` | Get supported region list for proxy | GET /v2/workflow/get-region-list |

## Configuration

Before running the examples, you need to:

1. Get your API key from: https://www.browseract.com/reception/integrations
2. Get your workflow ID or workflow template ID:
   - **For custom workflows**: Get workflow ID from https://www.browseract.com/reception/workflow-list
   - **For official templates**: Use `11.list_official_workflow_templates.py` to get template IDs
3. Update the following variables in each example file:
   - `authorization`: Replace `"app-abcdefghijklmn"` with your actual API key
   - `workflowId`: Replace `1234567890` with your actual workflow ID (for custom workflows)
   - `workflow_template_id`: Replace `"1234567890"` with your actual template ID (for template-based tasks)
   - `taskId`: Replace the actual task ID returned by `1.run_task.py` or `9.run_task_by_template.py`
   - `proxyRegion`: Use region code from `12.get_region_list.py` (default: "US")

## API Key Security

**Important**: The examples use placeholder API keys for demonstration purposes. In production:

1. Never hardcode API keys in your source code
2. Use environment variables or secure configuration files
3. Keep your API keys confidential and rotate them regularly

Example of using environment variables:
```python
import os
authorization = os.getenv("BROWSERACT_API_KEY");
```

## Using Official Templates

BrowserAct provides official workflow templates that you can use without creating your own workflows:

1. **List available templates**:
   ```bash
   python 11.list_official_workflow_templates.py
   ```
   This will show you all available official templates with their IDs, names, and descriptions.

2. **Get region list** (for proxy configuration):
   ```bash
   python 12.get_region_list.py
   ```
   This will show you all supported regions and their codes for use with `proxyRegion` parameter.

3. **Run a task using a template**:
   ```bash
   python 9.run_task_by_template.py
   ```
   Make sure to:
   - Set `workflow_template_id` to a template ID from step 1
   - Set `proxyRegion` to a region code from step 2 (optional, defaults to "US")
   - Configure `input_parameters` according to the template's requirements

**Note**: Template-based tasks don't support `save_browser_data` and `profile_id` parameters. These features are only available for custom workflows.

## Error Handling

All examples include comprehensive error handling for:
- Network connectivity issues
- API authentication errors
- Invalid parameters
- Server errors

## Dependencies

- **Requests**: HTTP library for making API requests
- **JSON**: Built-in JSON parsing for API responses

## Troubleshooting

### Common Issues

1. **Python Version Error**: Ensure Python 3.8+ is installed
2. **Module Not Found**: Verify you're in the correct directory and dependencies are installed
3. **API Error 401**: Check your API key and ensure it's valid
4. **API Error 10118**: Verify the workflow ID exists and is accessible with your API key
5. **Template Not Found**: Ensure the `workflow_template_id` is valid. Use `11.list_official_workflow_templates.py` to get available template IDs
6. **Invalid Region Code**: Ensure the `proxyRegion` value is valid. Use `12.get_region_list.py` to get supported region codes

### Getting Help

- API Documentation: https://www.browseract.com/reception/integrations/api-workflow
- Support: Contact us via [discord](https://discord.gg/UpnCKd7GaU) or email: support@browseract.com
- GitHub Issues: Report bugs or request features

## License

This demo code is provided as-is for educational and development purposes.
