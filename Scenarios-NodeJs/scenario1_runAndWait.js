/**
 * Scenario 1: Run Task and Wait for Completion ⭐ Most Common
 * 
 * Business Need:
 * Start a workflow task, wait for it to complete, and then get the results.
 * 
 * Use Cases:
 * - Need to synchronously wait for task completion
 * - Need to get the complete output results of the task
 * - Suitable for single task execution
 * 
 * Usage Steps:
 * 1. Modify API_KEY and WORKFLOW_ID below
 * 2. Modify input_parameters according to your workflow
 * 3. Run: node scenario1_runAndWait.js
 */

const https = require('https');

// ============ Configuration Area - Please modify the following variables ============
// API Key, get from: https://www.browseract.com/reception/integrations
const API_KEY = process.env.BROWSERACT_API_KEY || "app-abcdefghijklmn";

// Workflow ID, get from: https://www.browseract.com/reception/workflow-list
const WORKFLOW_ID = 1234567890;

// Polling configuration
const POLL_INTERVAL = 5;  // Check task status every 5 seconds
const MAX_WAIT_TIME = 1800;  // Maximum wait time: 30 minutes (1800 seconds)
// ================================================

const API_BASE_URL = "https://api.browseract.com/v2/workflow";

function makeRequest(options, data) {
    return new Promise((resolve, reject) => {
        const req = https.request(options, (res) => {
            let responseData = '';
            
            res.on('data', (chunk) => {
                responseData += chunk;
            });
            
            res.on('end', () => {
                if (res.statusCode === 200) {
                    resolve(JSON.parse(responseData));
                } else {
                    reject(new Error(`HTTP ${res.statusCode}: ${responseData}`));
                }
            });
        });
        
        req.on('error', (error) => {
            reject(error);
        });
        
        req.setTimeout(30000, () => {
            req.destroy();
            reject(new Error('Request timeout'));
        });
        
        if (data) {
            req.write(data);
        }
        req.end();
    });
}

function runTask(workflowId, inputParameters) {
    return new Promise((resolve, reject) => {
        const data = JSON.stringify({
            workflow_id: workflowId,
            input_parameters: inputParameters,
            save_browser_data: true
        });
        
        const options = {
            hostname: 'api.browseract.com',
            port: 443,
            path: '/v2/workflow/run-task',
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${API_KEY}`,
                'Content-Type': 'application/json',
                'Content-Length': Buffer.byteLength(data)
            }
        };
        
        makeRequest(options, data)
            .then(result => {
                const taskId = result.id;
                console.log(`✅ Task started, Task ID: ${taskId}`);
                if (result.profileId) {
                    console.log(`   Profile ID: ${result.profileId}`);
                }
                resolve(taskId);
            })
            .catch(error => {
                console.log(`❌ Failed to start task: ${error.message}`);
                resolve(null);
            });
    });
}

function getTaskStatus(taskId) {
    return new Promise((resolve) => {
        const options = {
            hostname: 'api.browseract.com',
            port: 443,
            path: `/v2/workflow/get-task-status?task_id=${taskId}`,
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${API_KEY}`
            }
        };
        
        makeRequest(options)
            .then(result => {
                resolve(result.status);
            })
            .catch(() => {
                // Network error, will retry in next polling cycle
                resolve(null);
            });
    });
}

function getTask(taskId) {
    return new Promise((resolve) => {
        const options = {
            hostname: 'api.browseract.com',
            port: 443,
            path: `/v2/workflow/get-task?task_id=${taskId}`,
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${API_KEY}`
            }
        };
        
        makeRequest(options)
            .then(result => {
                resolve(result);
            })
            .catch(error => {
                console.log(`⚠️ Network error while getting task details: ${error.message}`);
                resolve(null);
            });
    });
}

async function waitForTaskCompletion(taskId) {
    const startTime = Date.now();
    let previousStatus = null;
    
    console.log(`\n⏳ Waiting for task completion (max wait time: ${MAX_WAIT_TIME / 60} minutes)...`);
    
    while (true) {
        // Check if timeout
        const elapsedTime = Math.floor((Date.now() - startTime) / 1000);
        if (elapsedTime > MAX_WAIT_TIME) {
            console.log(`\n⏰ Wait timeout (waited ${elapsedTime} seconds)`);
            return null;
        }
        
        // Get task status
        const status = await getTaskStatus(taskId);
        
        if (status === null) {
            // Network error or API error, continue waiting
            console.log();
            process.stdout.write(`   Network error, retrying... (waited ${elapsedTime} seconds)\r`);
        } else if (status === "finished") {
            console.log(`\n✅ Task completed!`);
            return "finished";
        } else if (status === "failed") {
            console.log(`\n❌ Task execution failed`);
            return "failed";
        } else if (status === "canceled") {
            console.log(`\n🚫 Task canceled`);
            return "canceled";
        } else {
            // running, created, paused, etc.
            // If status changed, print on new line; otherwise update same line
            if (status !== previousStatus) {
                console.log();  // New line when status changes
                process.stdout.write(`   Status: ${status} (waited ${elapsedTime} seconds)\r`);
                previousStatus = status;
            } else {
                // Same status, update same line
                process.stdout.write(`   Status: ${status} (waited ${elapsedTime} seconds)\r`);
            }
        }
        
        // Wait before checking again
        await new Promise(resolve => setTimeout(resolve, POLL_INTERVAL * 1000));
    }
}

async function main() {
    console.log("============================================================");
    console.log("Scenario 1: Run Task and Wait for Completion");
    console.log("============================================================");
    
    try {
        // Step 1: Start task
        console.log("\n📤 Step 1: Starting task...");
        
        // Workflow input parameters (modify according to your workflow definition)
        const inputParameters = [
            {
                name: "target_url",
                value: "https://www.google.com/search?q=iphone17"
            },
            {
                name: "product_limit",
                value: "10"
            }
        ];
        
        const taskId = await runTask(WORKFLOW_ID, inputParameters);
        
        if (!taskId) {
            console.log("❌ Unable to start task, exiting");
            return;
        }
        
        // Step 2: Wait for task completion
        console.log("\n📥 Step 2: Waiting for task completion...");
        const finalStatus = await waitForTaskCompletion(taskId);
        
        // Step 3: Get task results
        console.log("\n📊 Step 3: Getting task results...");
        const taskInfo = await getTask(taskId);
        
        if (taskInfo) {
            console.log("\n============================================================");
            console.log("Task Result (JSON):");
            console.log("============================================================");
            // Output complete task result as formatted JSON
            console.log(JSON.stringify(taskInfo, null, 2));
            console.log("============================================================");
        } else {
            console.log("⚠️ Unable to get task details");
        }
        
    } catch (error) {
        console.error("\n❌ Error occurred:");
        console.error(error);
    }
}

main();
