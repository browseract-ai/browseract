# BrowserAct-Agent-API-Python-Demos

Note: this code runs successfully in Python version 3.13.5  

API documentation: https://www.browseract.com/reception/integrations/api-agent  
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

1. Navigate to the Agent-Python directory:
   ```bash
   cd Agent-Python
   ```

2. Install dependencies:
   ```bash
   pip install -r requirements.txt
   ```

## Running Examples

```bash
# Run specific example
python 1.run_task.py
python 2.stop_task.py
python 3.pause_task.py
python 4.resume_task.py
python 5.get_task.py
python 6.get_task_status.py
python 7.list_tasks.py
python 8.list_agents.py
```

## Example Files

| File | Description | API Endpoint |
|------|-------------|--------------|
| `1.run_task.py` | Start a new agent task | POST /v2/agent/run-task |
| `2.stop_task.py` | Permanently terminate a task | PUT /v2/agent/stop-task |
| `3.pause_task.py` | Temporarily suspend a task | PUT /v2/agent/pause-task |
| `4.resume_task.py` | Resume a paused task | PUT /v2/agent/resume-task |
| `5.get_task.py` | Get detailed task information | GET /v2/agent/get-task |
| `6.get_task_status.py` | Get task status only | GET /v2/agent/get-task-status |
| `7.list_tasks.py` | List all tasks | GET /v2/agent/list-tasks |
| `8.list_agents.py` | List all agents | GET /v2/agent/list-agents |

## Configuration

Before running the examples, you need to:

1. Get your API key from: https://www.browseract.com/reception/integrations
2. Get your agent ID from: https://www.browseract.com/reception/agent-list
3. Update the following variables in each example file:
   - `authorization`: Replace `"app-abcdefghijklmn"` with your actual API key
   - `agentId`: Replace `1234567890` with your actual agent ID
   - `taskId`: Replace the actual task ID returned by 1.run_task.py

## API Key Security

**Important**: The examples use placeholder API keys for demonstration purposes. In production:

1. Never hardcode API keys in your source code
2. Use environment variables or secure configuration files
3. Keep your API keys confidential and rotate them regularly

Example of using environment variables:
```python
import os
authorization = os.getenv("BROWSERACT_API_KEY")
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
4. **API Error 10010**: Verify the agent ID exists and is accessible with your API key

### Getting Help

- API Documentation: https://www.browseract.com/reception/integrations/api-agent
- Support: Contact us via [discord](https://discord.gg/UpnCKd7GaU) or email: support@browseract.com
- GitHub Issues: Report bugs or request features

## License

This demo code is provided as-is for educational and development purposes.
