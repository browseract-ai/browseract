# BrowserAct-Workflow-API-NodeJs-Demos

Note: this code runs successfully in Node.js version v20.19.4  

API documentation: https://www.browseract.com/reception/integrations/api-workflow  
Site: https://www.browseract.com   

## Environment Requirements

- Node.js 14 or higher
- npm (comes with Node.js)

## Installation

1. Install Node.js from [nodejs.org](https://nodejs.org/)
2. Verify installation:
   ```bash
   node --version
   npm --version
   ```

## Project Setup

1. Navigate to the Workflow-NodeJs directory:
   ```bash
   cd Workflow-NodeJs
   ```

2. Install dependencies (if any):
   ```bash
   npm install
   ```

## Running Examples

### Method 1: Using npm scripts
```bash
# Run specific example
npm run run-task
npm run stop-task
npm run get-task
npm run get-task-status
npm run list-tasks
npm run list-workflows
npm run get-workflow
```

### Method 2: Using Node.js directly
```bash
# Run specific example
node 1.runTask.js
node 2.stopTask.js
node 3.getTask.js
node 4.getTaskStatus.js
node 5.listTasks.js
node 6.listWorkflows.js
node 7.getWorkflow.js
```

## Project Structure

```
Workflow-NodeJs/
├── package.json              # Node.js dependencies and scripts
├── README.md                 # This file
├── 1.runTask.js             # Start a new workflow task
├── 2.stopTask.js            # Permanently terminate a task
├── 3.getTask.js             # Get detailed task information
├── 4.getTaskStatus.js       # Get task status only
├── 5.listTasks.js           # List all workflow tasks
├── 6.listWorkflows.js       # List all workflows
└── 7.getWorkflow.js         # Get workflow details
```

## Example Files

| File | Description | API Endpoint |
|------|-------------|--------------|
| `1.runTask.js` | Start a new workflow task | POST /v2/workflow/run-task |
| `2.stopTask.js` | Permanently terminate a task | PUT /v2/workflow/stop-task |
| `3.getTask.js` | Get detailed task information | GET /v2/workflow/get-task |
| `4.getTaskStatus.js` | Get task status only | GET /v2/workflow/get-task-status |
| `5.listTasks.js` | List all workflow tasks | GET /v2/workflow/list-tasks |
| `6.listWorkflows.js` | List all workflows | GET /v2/workflow/list-workflows |
| `7.getWorkflow.js` | Get workflow details | GET /v2/workflow/get-workflow |

## Configuration

Before running the examples, you need to:

1. Get your API key from: https://www.browseract.com/reception/integrations
2. Get your workflow ID from: https://www.browseract.com/reception/workflow-list
3. Update the following variables in each example file:
   - `authorization`: Replace `"app-abcdefghijklmn"` with your actual API key
   - `workflowId`: Replace `1234567890` with your actual workflow ID
   - `taskId`: Replace the actual task ID returned by 1.runTask.js

## API Key Security

**Important**: The examples use placeholder API keys for demonstration purposes. In production:

1. Never hardcode API keys in your source code
2. Use environment variables or secure configuration files
3. Keep your API keys confidential and rotate them regularly

Example of using environment variables:
```javascript
const authorization = process.env.BROWSERACT_API_KEY;
```

## Error Handling

All examples include comprehensive error handling for:
- Network connectivity issues
- API authentication errors
- Invalid parameters
- Server errors

## Dependencies

- **Node.js Built-in Modules**: Uses `https` module for HTTP requests
- **JSON**: Built-in JSON parsing for API responses

## Troubleshooting

### Common Issues

1. **Node.js Version Error**: Ensure Node.js 14+ is installed
2. **Module Not Found**: Verify you're in the correct directory
3. **API Error 401**: Check your API key and ensure it's valid
4. **API Error 10118**: Verify the workflow ID exists and is accessible with your API key

### Getting Help

- API Documentation: https://www.browseract.com/reception/integrations/api-workflow
- Support: Contact us via [discord](https://discord.gg/UpnCKd7GaU) or email: support@browseract.com
- GitHub Issues: Report bugs or request features

## License

This demo code is provided as-is for educational and development purposes.
