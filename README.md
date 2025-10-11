# BrowserAct API Demo Collection

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-8+-blue.svg)](https://www.oracle.com/java/)
[![Node.js](https://img.shields.io/badge/Node.js-14+-green.svg)](https://nodejs.org/)
[![Python](https://img.shields.io/badge/Python-3.8+-yellow.svg)](https://www.python.org/)

> **AI Web Scraper & Automation** - Any Site, No Code, Zero Limits, Reliable Data

This repository contains comprehensive API demo code for [BrowserAct](https://www.browseract.com/), an AI-powered web scraping and automation platform. The demos showcase how to integrate BrowserAct's AI-Agent and AI-Workflow capabilities into your applications using multiple programming languages.

## 🚀 Quick Start

Choose your preferred language and get started in minutes:

| Language | Agent API | Workflow API | Quick Start |
|----------|-----------|--------------|-------------|
| **Python** | [Agent-Python](./Agent-Python/) | [Workflow-Python](./Workflow-Python/) | `cd Agent-Python && python 1.run_task.py` |
| **Java** | [Agent-Java](./Agent-Java/) | [Workflow-Java](./Workflow-Java/) | `cd Agent-Java && mvn exec:java -Dexec.mainClass="com.browseract.agent.demo.RunTask"` |
| **Node.js** | [Agent-NodeJs](./Agent-NodeJs/) | [Workflow-NodeJs](./Workflow-NodeJs/) | `cd Agent-NodeJs && node 1.runTask.js` |

## 📁 Project Structure

```
browseract-api-demos/
├── Agent-Python/          # Python Agent API demos
├── Agent-Java/            # Java Agent API demos  
├── Agent-NodeJs/          # Node.js Agent API demos
├── Workflow-Python/       # Python Workflow API demos
├── Workflow-Java/         # Java Workflow API demos
├── Workflow-NodeJs/       # Node.js Workflow API demos
├── .gitignore             # Git ignore rules
└── README.md              # This file
```

## 🎯 What is BrowserAct?

BrowserAct is an AI-powered web scraping and automation platform that provides two core capabilities:

### 🤖 AI-Agent
- **Natural Language Instructions**: Describe what you want to scrape in plain English
- **Intelligent Automation**: AI handles complex interactions, form filling, and data extraction
- **Dynamic Adaptation**: Automatically adapts to website changes and new structures

### 🔄 AI-Workflow  
- **Predefined Templates**: Use pre-built workflows for common scraping tasks
- **Custom Workflows**: Create your own automation sequences
- **Batch Processing**: Handle multiple URLs and data sources efficiently

## 📚 API Capabilities

### Agent API Endpoints
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/v2/agent/run-task` | POST | Start a new agent task |
| `/v2/agent/stop-task` | PUT | Permanently terminate a task |
| `/v2/agent/pause-task` | PUT | Temporarily suspend a task |
| `/v2/agent/resume-task` | PUT | Resume a paused task |
| `/v2/agent/get-task` | GET | Get detailed task information |
| `/v2/agent/get-task-status` | GET | Get task status only |
| `/v2/agent/list-tasks` | GET | List all tasks |
| `/v2/agent/list-agents` | GET | List all agents |

### Workflow API Endpoints
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/v2/workflow/run-task` | POST | Start a new workflow task |
| `/v2/workflow/stop-task` | PUT | Permanently terminate a task |
| `/v2/workflow/get-task` | GET | Get detailed task information |
| `/v2/workflow/get-task-status` | GET | Get task status only |
| `/v2/workflow/list-tasks` | GET | List all tasks |
| `/v2/workflow/list-workflows` | GET | List all workflows |
| `/v2/workflow/get-workflow` | GET | Get workflow details |

## 🛠️ Language Support

### Python
- **Version**: Python 3.8+
- **Dependencies**: `requests` for HTTP requests
- **Features**: Simple, readable code with comprehensive error handling
- **Best for**: Quick prototyping and data science workflows

### Java
- **Version**: Java 8+
- **Build Tool**: Maven
- **Dependencies**: Jackson for JSON processing, HttpURLConnection for HTTP requests
- **Features**: Enterprise-grade code with proper package structure
- **Best for**: Enterprise applications and microservices

### Node.js
- **Version**: Node.js 14+
- **Dependencies**: Built-in `https` module
- **Features**: Lightweight, async-first approach
- **Best for**: Web applications and serverless functions

## 🔧 Configuration

Before running any examples, you'll need:

1. **API Key**: Get your API key from [BrowserAct Integrations](https://www.browseract.com/reception/integrations)
2. **Agent ID** (for Agent API): Get your agent ID from [Agent List](https://www.browseract.com/reception/agent-list)
3. **Workflow ID** (for Workflow API): Get your workflow ID from [Workflow List](https://www.browseract.com/reception/workflow-list)

Update the configuration in each example file:
- Replace `"app-abcdefghijklmn"` with your actual API key
- Replace `1234567890` with your actual agent/workflow ID

## 🔒 Security Best Practices

**Important**: The examples use placeholder API keys for demonstration purposes. In production:

1. **Never hardcode API keys** in your source code
2. **Use environment variables** or secure configuration files
3. **Keep your API keys confidential** and rotate them regularly

### Environment Variable Examples

```python
# Python
import os
authorization = os.getenv("BROWSERACT_API_KEY")
```

```java
// Java
String authorization = System.getenv("BROWSERACT_API_KEY");
```

```javascript
// Node.js
const authorization = process.env.BROWSERACT_API_KEY;
```

## 📖 Documentation

- **API Documentation**: [BrowserAct API Docs](https://www.browseract.com/reception/integrations)
- **Agent API**: [Agent API Reference](https://www.browseract.com/reception/integrations/api-agent)
- **Workflow API**: [Workflow API Reference](https://www.browseract.com/reception/integrations/api-workflow)

## 🤝 Support

- **Discord Community**: [Join our Discord](https://discord.gg/UpnCKd7GaU)
- **Email Support**: support@browseract.com
- **GitHub Issues**: [Report bugs or request features](https://github.com/browseract/browseract-api-demos/issues)

## 📄 License

This demo code is provided as-is for educational and development purposes under the [MIT License](LICENSE).

## 🌟 Features

- ✅ **Multi-language Support**: Python, Java, and Node.js implementations
- ✅ **Complete API Coverage**: All BrowserAct API endpoints included
- ✅ **Production Ready**: Enterprise-grade code with proper error handling
- ✅ **Easy to Use**: Simple examples that can be run immediately
- ✅ **Well Documented**: Comprehensive README files for each language
- ✅ **Modern Standards**: Follows best practices for each language

---

**Ready to get started?** Choose your language above and dive into the examples! 🚀

Visit [BrowserAct](https://www.browseract.com/) to learn more about our AI-powered web scraping platform.