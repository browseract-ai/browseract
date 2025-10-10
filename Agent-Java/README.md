# BrowserAct-Agent-API-Java-Demos

Note: this code runs successfully in Java version 8  

API documentation: https://www.browseract.com/reception/integrations/api-agent  
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

1. Navigate to the Agent-Java directory:
   ```bash
   cd Agent-Java
   ```

2. Install dependencies:
   ```bash
   mvn clean install
   ```

## Running Examples

### Method 1: Using Maven
```bash
# Run specific example
mvn exec:java -Dexec.mainClass="com.browseract.agent.demo.RunTask"
mvn exec:java -Dexec.mainClass="com.browseract.agent.demo.StopTask"
mvn exec:java -Dexec.mainClass="com.browseract.agent.demo.PauseTask"
mvn exec:java -Dexec.mainClass="com.browseract.agent.demo.ResumeTask"
mvn exec:java -Dexec.mainClass="com.browseract.agent.demo.GetTask"
mvn exec:java -Dexec.mainClass="com.browseract.agent.demo.GetTaskStatus"
mvn exec:java -Dexec.mainClass="com.browseract.agent.demo.ListTasks"
mvn exec:java -Dexec.mainClass="com.browseract.agent.demo.ListAgents"
```

### Method 2: Using IDEA

Open pom.xml with Idea, then run the main function in each Java file

## Example Files

| File | Description | API Endpoint |
|------|-------------|--------------|
| `RunTask.java` | Start a new agent task | POST /v2/agent/run-task |
| `StopTask.java` | Permanently terminate a task | PUT /v2/agent/stop-task |
| `PauseTask.java` | Temporarily suspend a task | PUT /v2/agent/pause-task |
| `ResumeTask.java` | Resume a paused task | PUT /v2/agent/resume-task |
| `GetTask.java` | Get detailed task information | GET /v2/agent/get-task |
| `GetTaskStatus.java` | Get task status only | GET /v2/agent/get-task-status |
| `ListTasks.java` | List all tasks | GET /v2/agent/list-tasks |
| `ListAgents.java` | List all agents | GET /v2/agent/list-agents |

## Configuration

Before running the examples, you need to:

1. Get your API key from: https://www.browseract.com/reception/integrations
2. Get your agent ID from: https://www.browseract.com/reception/agent-list
3. Update the following variables in each example file:
   - `authorization`: Replace `"app-abcdefghijklmn"` with your actual API key
   - `agentId`: Replace `1234567890L` with your actual agent ID
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
4. **API Error 10010**: Verify the agent ID exists and is accessible with your API key

### Getting Help

- API Documentation: https://www.browseract.com/reception/integrations/api-agent
- Support: Contact us via [discord](https://discord.gg/UpnCKd7GaU) or email: support@browseract.com
- GitHub Issues: Report bugs or request features

## License

This demo code is provided as-is for educational and development purposes.