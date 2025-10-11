# BrowserAct-Agent-API-NodeJs-Demos

Note: this code runs successfully in Node.js version v20.19.4  

API documentation: https://www.browseract.com/reception/integrations/api-agent  
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

1. Navigate to the Agent-NodeJs directory:
   ```bash
   cd Agent-NodeJs
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
npm run pause-task
npm run resume-task
npm run get-task
npm run get-task-status
npm run list-tasks
npm run list-agents
```

### Method 2: Using Node.js directly
```bash
# Run specific example
node 1.runTask.js
node 2.stopTask.js
node 3.pauseTask.js
node 4.resumeTask.js
node 5.getTask.js
node 6.getTaskStatus.js
node 7.listTasks.js
node 8.listAgents.js
```

## Project Structure

```
Agent-NodeJs/
├── package.json              # Node.js dependencies and scripts
├── README.md                 # This file
├── 1.runTask.js             # Start a new agent task
├── 2.stopTask.js            # Permanently terminate a task
├── 3.pauseTask.js           # Temporarily suspend a task
├── 4.resumeTask.js          # Resume a paused task
├── 5.getTask.js             # Get detailed task information
├── 6.getTaskStatus.js       # Get task status only
├── 7.listTasks.js           # List all tasks
└── 8.listAgents.js          # List all agents
```

## Example Files

| File | Description | API Endpoint |
|------|-------------|--------------|
| `1.runTask.js` | Start a new agent task | POST /v2/agent/run-task |
| `2.stopTask.js` | Permanently terminate a task | PUT /v2/agent/stop-task |
| `3.pauseTask.js` | Temporarily suspend a task | PUT /v2/agent/pause-task |
| `4.resumeTask.js` | Resume a paused task | PUT /v2/agent/resume-task |
| `5.getTask.js` | Get detailed task information | GET /v2/agent/get-task |
| `6.getTaskStatus.js` | Get task status only | GET /v2/agent/get-task-status |
| `7.listTasks.js` | List all tasks | GET /v2/agent/list-tasks |
| `8.listAgents.js` | List all agents | GET /v2/agent/list-agents |

## Configuration

Before running the examples, you need to:

1. Get your API key from: https://www.browseract.com/reception/integrations
2. Get your agent ID from: https://www.browseract.com/reception/agent-list
3. Update the following variables in each example file:
   - `authorization`: Replace `"app-abcdefghijklmn"` with your actual API key
   - `agentId`: Replace `1234567890` with your actual agent ID
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
4. **API Error 10010**: Verify the agent ID exists and is accessible with your API key

### Getting Help

- API Documentation: https://www.browseract.com/reception/integrations/api-agent
- Support: Contact us via [discord](https://discord.gg/UpnCKd7GaU) or email: support@browseract.com
- GitHub Issues: Report bugs or request features

## License

This demo code is provided as-is for educational and development purposes.
