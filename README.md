# BrowserAct API Demo Collection

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-8+-blue.svg)](https://www.oracle.com/java/)
[![Node.js](https://img.shields.io/badge/Node.js-14+-green.svg)](https://nodejs.org/)
[![Python](https://img.shields.io/badge/Python-3.8+-yellow.svg)](https://www.python.org/)

> **AI Web Scraper & Automation** - Any Site, No Code, Zero Limits, Reliable Data

This repository contains comprehensive API demo code for [BrowserAct](https://www.browseract.com/), an AI-powered web scraping and automation platform. The demos showcase how to integrate BrowserAct's AI-Workflow capabilities into your applications using multiple programming languages.

## 🚀 Quick Start

Choose your preferred language and get started in minutes:

| Language | Workflow API | Quick Start |
|----------|--------------|-------------|
| **Python** | [Workflow-Python](./Workflow-Python/) | `cd Workflow-Python && python 1.run_task.py` |
| **Java** | [Workflow-Java](./Workflow-Java/) | `cd Workflow-Java && mvn exec:java -Dexec.mainClass="com.browseract.workflow.demo.RunTask"` |
| **Node.js** | [Workflow-NodeJs](./Workflow-NodeJs/) | `cd Workflow-NodeJs && node 1.runTask.js` |

## 📁 Project Structure

```
browseract-api-demos/
├── Workflow-Python/       # Python Workflow API demos
├── Workflow-Java/         # Java Workflow API demos
├── Workflow-NodeJs/       # Node.js Workflow API demos
├── .gitignore             # Git ignore rules
└── README.md              # This file
```

## 🎯 What is BrowserAct?

BrowserAct is an AI-powered web scraping and automation platform that provides two core capabilities:

### 🔄 AI-Workflow  
- **Predefined Templates**: Use pre-built workflows for common scraping tasks
- **Custom Workflows**: Create your own automation sequences
- **Batch Processing**: Handle multiple URLs and data sources efficiently

## 📚 API Capabilities

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

## 🛠 Language Support

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
2. **Workflow ID** (for Workflow API): Get your workflow ID from [Workflow List](https://www.browseract.com/reception/workflow-list)

Update the configuration in each example file:
- Replace `"app-abcdefghijklmn"` with your actual API key
- Replace `1234567890` with your actual workflow ID

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
- **Workflow API**: [Workflow API Reference](https://www.browseract.com/reception/integrations/api-workflow)

## 🤝 Support

- **Discord Community**: [Join our Discord](https://discord.gg/UpnCKd7GaU)
- **Email Support**: support@browseract.com
- **GitHub Issues**: [Report bugs or request features](https://github.com/browseract/browseract-api-demos/issues)

## 📄 License

This demo code is provided as-is for educational and development purposes under the [MIT License](LICENSE).

## 🌟 Features

- **Multi-language Support**: Python, Java, and Node.js implementations
- **Complete API Coverage**: All BrowserAct API endpoints included
- **Production Ready**: Enterprise-grade code with proper error handling
- **Easy to Use**: Simple examples that can be run immediately
- **Well Documented**: Comprehensive README files for each language
- **Modern Standards**: Follows best practices for each language

---

**Ready to get started?** Choose your language above and dive into the examples! 🚀

Visit [BrowserAct](https://www.browseract.com/) to learn more about our AI-powered web scraping platform.