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

### Method 1: Using Python directly
```bash
# Run specific example
python 1.run_task.py
python 2.stop_task.py
python 3.get_task.py
python 4.get_task_status.py
python 5.list_tasks.py
python 6.list_workflows.py
python 7.get_workflow.py
```

## Project Structure

```
Workflow-Python/
├── README.md                 # This file
├── requirements.txt          # Python dependencies
├── 1.run_task.py            # Start a new workflow task
├── 2.stop_task.py           # Permanently terminate a task
├── 3.get_task.py            # Get detailed task information
├── 4.get_task_status.py     # Get task status only
├── 5.list_tasks.py          # List all workflow tasks
├── 6.list_workflows.py      # List all workflows
└── 7.get_workflow.py        # Get workflow details
```

## Example Files

| File | Description | API Endpoint |
|------|-------------|--------------|
| `1.run_task.py` | Start a new workflow task | POST /v2/workflow/run-task |
| `2.stop_task.py` | Permanently terminate a task | PUT /v2/workflow/stop-task |
| `3.get_task.py` | Get detailed task information | GET /v2/workflow/get-task |
| `4.get_task_status.py` | Get task status only | GET /v2/workflow/get-task-status |
| `5.list_tasks.py` | List all workflow tasks | GET /v2/workflow/list-tasks |
| `6.list_workflows.py` | List all workflows | GET /v2/workflow/list-workflows |
| `7.get_workflow.py` | Get workflow details | GET /v2/workflow/get-workflow |

## Configuration

Before running the examples, you need to:

1. Get your API key from: https://www.browseract.com/reception/integrations
2. Get your workflow ID from: https://www.browseract.com/reception/workflow-list
3. Update the following variables in each example file:
   - `authorization`: Replace `"app-abcdefghijklmn"` with your actual API key
   - `workflowId`: Replace `1234567890` with your actual workflow ID
   - `taskId`: Replace the actual task ID returned by 1.run_task.py

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

### Getting Help

- API Documentation: https://www.browseract.com/reception/integrations/api-workflow
- Support: Contact us via [discord](https://discord.gg/UpnCKd7GaU) or email: support@browseract.com
- GitHub Issues: Report bugs or request features

## License

This demo code is provided as-is for educational and development purposes.
