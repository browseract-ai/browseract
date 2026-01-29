# BrowserAct Workflow API - Scenario-Based Examples (Java)

This directory contains scenario-based examples that demonstrate how to combine multiple APIs to accomplish real-world use cases. These examples are perfect for new users who want to quickly understand how to use BrowserAct API.

## 📋 Available Scenarios

### Scenario 1: Run Task and Wait for Completion ⭐ Most Common

**Description**: Start a custom workflow task, wait for it to complete, and then get the results.

**Use Cases**:
- Need to synchronously wait for task completion
- Need to get the complete output results of the task
- Suitable for single task execution

**File**: `src/main/java/com/browseract/scenarios/Scenario1RunAndWait.java`

**Run**:
```bash
mvn exec:java -Dexec.mainClass="com.browseract.scenarios.Scenario1RunAndWait"
```

---

### Scenario 2: Run Template Task and Wait for Completion ⭐ Recommended for Beginners

**Description**: Start a workflow task using an official template, wait for it to complete, and then get the results.

**Use Cases**:
- Need to synchronously wait for task completion
- Need to get the complete output results of the task
- Suitable for single task execution
- Perfect for users who want to use official templates without creating custom workflows

**File**: `src/main/java/com/browseract/scenarios/Scenario2RunTemplateAndWait.java`

**Run**:
```bash
mvn exec:java -Dexec.mainClass="com.browseract.scenarios.Scenario2RunTemplateAndWait"
```

---

## 🚀 Quick Start

### 1. Prerequisites

- Java 8 or higher
- Maven 3.6 or higher

### 2. Build the Project

```bash
cd Scenarios-Java
mvn clean compile
```

### 3. Configure API Key

**Method 1: Use environment variables (Recommended)**
```bash
# Windows PowerShell
$env:BROWSERACT_API_KEY="your-api-key-here"

# Linux/Mac
export BROWSERACT_API_KEY="your-api-key-here"
```

**Method 2: Modify variables in code**
Edit the `API_KEY` constant in each scenario file.

### 4. Get Workflow ID or Template ID

**For Scenario 1 (Custom Workflow)**:
- Visit: https://www.browseract.com/reception/workflow-list
- Copy the workflow ID you want to use

**For Scenario 2 (Official Template)**:
- Visit: https://www.browseract.com/template?platformType=0
- Copy the template ID you want to use

### 5. Run Scenario

```bash
# Scenario 1
mvn exec:java -Dexec.mainClass="com.browseract.scenarios.Scenario1RunAndWait"

# Scenario 2
mvn exec:java -Dexec.mainClass="com.browseract.scenarios.Scenario2RunTemplateAndWait"
```

## 📝 Configuration

### Dependencies

**Required**: Jackson library for JSON processing

Add to your `pom.xml`:
```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.15.2</version>
</dependency>
```

### Configuration Variables

Before running scenarios, modify the following variables in each scenario file:

- `API_KEY`: Your API key from https://www.browseract.com/reception/integrations
- `WORKFLOW_ID`: Your workflow ID (for Scenario 1)
- `WORKFLOW_TEMPLATE_ID`: Your template ID (for Scenario 2)
- `INPUT_PARAMETERS`: Input parameters according to your workflow/template
- `POLL_INTERVAL`: How often to check task status (default: 5 seconds)
- `MAX_WAIT_TIME`: Maximum wait time (default: 1800 seconds / 30 minutes)

## 📋 Copy-Paste Ready

**Important**: Each scenario file is **self-contained** and includes all necessary HTTP utility methods at the bottom of the file. You can copy the entire file directly into your IDE without needing the `HttpUtil` class.

The HTTP utility methods are included inline at the bottom of each scenario file for easy copy-paste functionality.

## 🔗 Related Files

- Individual API examples: See `../Workflow-Java/` directory
- API Documentation: https://www.browseract.com/reception/integrations/api-workflow
- Support: support@browseract.com
