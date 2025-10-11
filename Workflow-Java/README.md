# BrowserAct-Workflow-API-Java-Demos

Note: this code runs successfully in Java version 8  

API documentation: https://www.browseract.com/reception/integrations/api-workflow  
Site: https://www.browseract.com   

## Environment Requirements

- Java 8 or higher
- Maven 3.6 or higher

## Installation

1. Install Java 8+ from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
2. Install Maven from [Apache Maven](https://maven.apache.org/download.cgi)
3. Verify installation:
   ```bash
   java -version
   mvn -version
   ```

## Project Setup

1. Navigate to the Workflow-Java directory:
   ```bash
   cd Workflow-Java
   ```

2. Install dependencies:
   ```bash
   mvn clean install
   ```

## Running Examples

### Method 1: Using Maven
```bash
# Run specific example
mvn exec:java -Dexec.mainClass="com.browseract.workflow.demo.RunTask"
mvn exec:java -Dexec.mainClass="com.browseract.workflow.demo.StopTask"
mvn exec:java -Dexec.mainClass="com.browseract.workflow.demo.GetTask"
mvn exec:java -Dexec.mainClass="com.browseract.workflow.demo.GetTaskStatus"
mvn exec:java -Dexec.mainClass="com.browseract.workflow.demo.ListTasks"
mvn exec:java -Dexec.mainClass="com.browseract.workflow.demo.ListWorkflows"
mvn exec:java -Dexec.mainClass="com.browseract.workflow.demo.GetWorkflow"
```

### Method 2: Using IDEA

Open pom.xml with Idea, then run the main function in each Java file


## Project Structure

```
Workflow-Java/
├── pom.xml                   # Maven configuration
├── README.md                 # This file
└── src/main/java/com/browseract/workflow/demo/
    ├── RunTask.java          # Start a new workflow task
    ├── StopTask.java         # Permanently terminate a task
    ├── GetTask.java          # Get detailed task information
    ├── GetTaskStatus.java    # Get task status only
    ├── ListTasks.java        # List all workflow tasks
    ├── ListWorkflows.java    # List all workflows
    ├── GetWorkflow.java      # Get workflow details
    └── util/
        └── HttpUtil.java     # HTTP utility class
```

## Example Files

| File | Description | API Endpoint |
|------|-------------|--------------|
| `RunTask.java` | Start a new workflow task | POST /v2/workflow/run-task |
| `StopTask.java` | Permanently terminate a task | PUT /v2/workflow/stop-task |
| `GetTask.java` | Get detailed task information | GET /v2/workflow/get-task |
| `GetTaskStatus.java` | Get task status only | GET /v2/workflow/get-task-status |
| `ListTasks.java` | List all workflow tasks | GET /v2/workflow/list-tasks |
| `ListWorkflows.java` | List all workflows | GET /v2/workflow/list-workflows |
| `GetWorkflow.java` | Get workflow details | GET /v2/workflow/get-workflow |

## Configuration

Before running the examples, you need to:

1. Get your API key from: https://www.browseract.com/reception/integrations
2. Get your workflow ID from: https://www.browseract.com/reception/workflow-list
3. Update the following variables in each example file:
   - `authorization`: Replace `"app-abcdefghijklmn"` with your actual API key
   - `workflowId`: Replace `1234567890L` with your actual workflow ID
   - `taskId`: Replace the actual task ID returned by RunTask.java

## API Key Security

**Important**: The examples use placeholder API keys for demonstration purposes. In production:

1. Never hardcode API keys in your source code
2. Use environment variables or secure configuration files
3. Keep your API keys confidential and rotate them regularly

Example of using environment variables:
```java
String authorization = System.getenv("BROWSERACT_API_KEY");
```

## Error Handling

All examples include comprehensive error handling for:
- Network connectivity issues
- API authentication errors
- Invalid parameters
- Server errors

## Dependencies

- **Jackson**: JSON processing library for parsing API responses
- **Java HTTP Client**: Built-in HttpURLConnection for making HTTP requests

## Troubleshooting

### Common Issues

1. **Compilation Error**: Ensure Java 8+ is installed and JAVA_HOME is set correctly
2. **Maven Error**: Verify Maven installation and check internet connectivity for dependency download
3. **API Error 401**: Check your API key and ensure it's valid
4. **API Error 10118**: Verify the workflow ID exists and is accessible with your API key

### Getting Help

- API Documentation: https://www.browseract.com/reception/integrations/api-workflow
- Support: Contact us via [discord](https://discord.gg/UpnCKd7GaU) or email: support@browseract.com
- GitHub Issues: Report bugs or request features

## License

This demo code is provided as-is for educational and development purposes.