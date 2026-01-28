# BrowserAct Workflow API - Scenario-Based Examples

This directory contains scenario-based examples that demonstrate how to combine multiple APIs to accomplish real-world use cases. These examples are perfect for new users who want to quickly understand how to use BrowserAct API.

## 📋 Available Scenarios

### Scenario 1: Run Task and Wait for Completion ⭐ Most Common

**Description**: Start a custom workflow task, wait for it to complete, and then get the results.

**Use Cases**:
- Need to synchronously wait for task completion
- Need to get the complete output results of the task
- Suitable for single task execution

**File**: `scenario_1_run_and_wait.py`

**Run**:
```bash
python Scenarios-Python/scenario_1_run_and_wait.py
```

---

### Scenario 2: Run Template Task and Wait for Completion ⭐ Recommended for Beginners

**Description**: Start a workflow task using an official template, wait for it to complete, and then get the results.

**Use Cases**:
- Need to synchronously wait for task completion
- Need to get the complete output results of the task
- Suitable for single task execution
- Perfect for users who want to use official templates without creating custom workflows

**File**: `scenario_2_run_template_and_wait.py`

**Run**:
```bash
python Scenarios-Python/scenario_2_run_template_and_wait.py
```

---

## 🚀 Quick Start

### 1. Install Dependencies

Make sure you have the required dependencies installed:

```bash
cd Workflow-Python
pip install -r requirements.txt
```

### 2. Configure API Key

**Method 1: Use environment variables (Recommended)**
```bash
# Windows PowerShell
$env:BROWSERACT_API_KEY="your-api-key-here"

# Linux/Mac
export BROWSERACT_API_KEY="your-api-key-here"
```

**Method 2: Modify variables in code**
Edit the `API_KEY` variable in each scenario file.

### 3. Get Workflow ID or Template ID

**For Scenario 1 (Custom Workflow)**:
- Visit: https://www.browseract.com/reception/workflow-list
- Copy the workflow ID you want to use
- Or run: `python Workflow-Python/6.list_workflows.py`

**For Scenario 2 (Official Template)**:
- Run: `python Workflow-Python/11.list_official_workflow_templates.py` to see all available templates
- Copy the `templateId` from the response
- Optionally run: `python Workflow-Python/12.get_region_list.py` to see available proxy regions

### 4. Run Scenario

```bash
# From project root directory
python Scenarios-Python/scenario_1_run_and_wait.py
# or
python Scenarios-Python/scenario_2_run_template_and_wait.py
```

## 📝 Configuration

Before running scenarios, modify the following variables in each scenario file:

- `API_KEY`: Your API key from https://www.browseract.com/reception/integrations
- `WORKFLOW_ID`: Your workflow ID (for Scenario 1)
- `WORKFLOW_TEMPLATE_ID`: Your template ID (for Scenario 2)
- `INPUT_PARAMETERS`: Input parameters according to your workflow/template
- `PROXY_REGION`: Proxy region code (for Scenario 2, default: "US")
- `POLL_INTERVAL`: How often to check task status (default: 5 seconds)
- `MAX_WAIT_TIME`: Maximum wait time (default: 600 seconds)

## 🔗 Related Files

- Individual API examples: See `Workflow-Python/` directory
- API Documentation: https://www.browseract.com/reception/integrations/api-workflow
- Support: support@browseract.com
