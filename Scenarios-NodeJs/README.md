# BrowserAct Workflow API - Scenario-Based Examples (Node.js)

This directory contains scenario-based examples that demonstrate how to combine multiple APIs to accomplish real-world use cases. These examples are perfect for new users who want to quickly understand how to use BrowserAct API.

## 📋 Available Scenarios

### Scenario 1: Run Task and Wait for Completion ⭐ Most Common

**Description**: Start a custom workflow task, wait for it to complete, and then get the results.

**Use Cases**:
- Need to synchronously wait for task completion
- Need to get the complete output results of the task
- Suitable for single task execution

**File**: `scenario1_runAndWait.js`

**Run**:
```bash
node scenario1_runAndWait.js
```

---

### Scenario 2: Run Template Task and Wait for Completion ⭐ Recommended for Beginners

**Description**: Start a workflow task using an official template, wait for it to complete, and then get the results.

**Use Cases**:
- Need to synchronously wait for task completion
- Need to get the complete output results of the task
- Suitable for single task execution
- Perfect for users who want to use official templates without creating custom workflows

**File**: `scenario2_runTemplateAndWait.js`

**Run**:
```bash
node scenario2_runTemplateAndWait.js
```

---

## 🚀 Quick Start

### 1. Prerequisites

- Node.js 14.0 or higher
- npm 6.0 or higher

### 2. Configure API Key

**Method 1: Use environment variables (Recommended)**
```bash
# Windows PowerShell
$env:BROWSERACT_API_KEY="your-api-key-here"

# Linux/Mac
export BROWSERACT_API_KEY="your-api-key-here"
```

**Method 2: Modify variables in code**
Edit the `API_KEY` constant in each scenario file.

### 3. Get Workflow ID or Template ID

**For Scenario 1 (Custom Workflow)**:
- Visit: https://www.browseract.com/reception/workflow-list
- Copy the workflow ID you want to use
- Or run: `node ../Workflow-NodeJs/6.listWorkflows.js`

**For Scenario 2 (Official Template)**:
- Run: `node ../Workflow-NodeJs/11.listOfficialWorkflowTemplates.js` to see all available templates
- Copy the `templateId` from the response

### 4. Run Scenario

```bash
# From Scenarios-NodeJs directory
# Scenario 1
node scenario1_runAndWait.js

# Scenario 2
node scenario2_runTemplateAndWait.js
```

## 📝 Configuration

Before running scenarios, modify the following variables in each scenario file:

- `API_KEY`: Your API key from https://www.browseract.com/reception/integrations
- `WORKFLOW_ID`: Your workflow ID (for Scenario 1)
- `WORKFLOW_TEMPLATE_ID`: Your template ID (for Scenario 2)
- `INPUT_PARAMETERS`: Input parameters according to your workflow/template
- `POLL_INTERVAL`: How often to check task status (default: 5 seconds)
- `MAX_WAIT_TIME`: Maximum wait time (default: 1800 seconds / 30 minutes)

## 🔗 Related Files

- Individual API examples: See `../Workflow-NodeJs/` directory
- API Documentation: https://www.browseract.com/reception/integrations/api-workflow
- Support: support@browseract.com
