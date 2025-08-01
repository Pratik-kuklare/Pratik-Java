# VS Code Setup Guide for Chat Application

## Required VS Code Extensions

Install these extensions for the best development experience:

### Essential Extensions
1. **Extension Pack for Java** (Microsoft)
   - Includes: Language Support, Debugger, Test Runner, Maven, Project Manager
2. **JavaFX Support** (Gluon)
   - For JavaFX development and FXML editing
3. **MySQL** (Jun Han)
   - For database management within VS Code

### Recommended Extensions
4. **Maven for Java** (Microsoft)
5. **Project Manager for Java** (Microsoft)
6. **Debugger for Java** (Microsoft)
7. **XML** (Red Hat) - for FXML files
8. **CSS Peek** (Pranay Prakash) - for CSS editing

## VS Code Configuration

### 1. Java Configuration
Create `.vscode/settings.json`:
```json
{
    "java.configuration.updateBuildConfiguration": "automatic",
    "java.compile.nullAnalysis.mode": "automatic",
    "java.debug.settings.onBuildFailureProceed": true,
    "java.maven.downloadSources": true,
    "java.maven.downloadJavadoc": true,
    "java.format.settings.url": "https://raw.githubusercontent.com/google/styleguide/gh-pages/eclipse-java-google-style.xml"
}
```

### 2. Launch Configuration
Create `.vscode/launch.json`:
```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Launch Chat Server",
            "request": "launch",
            "mainClass": "com.chatapp.server.ChatServer",
            "projectName": "realtime-chat",
            "console": "integratedTerminal"
        },
        {
            "type": "java",
            "name": "Launch Chat Client",
            "request": "launch",
            "mainClass": "com.chatapp.ChatApplication",
            "projectName": "realtime-chat",
            "vmArgs": "--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml",
            "console": "integratedTerminal"
        }
    ]
}
```

### 3. Tasks Configuration
Create `.vscode/tasks.json`:
```json
{
    "version": "2.0.0",
    "tasks": [
        {
            "label": "compile",
            "type": "shell",
            "command": "mvn clean compile",
            "group": "build",
            "presentation": {
                "echo": true,
                "reveal": "always",
                "focus": false,
                "panel": "shared"
            }
        },
        {
            "label": "run server",
            "type": "shell",
            "command": "mvn exec:java",
            "group": "build",
            "presentation": {
                "echo": true,
                "reveal": "always",
                "focus": false,
                "panel": "shared"
            }
        },
        {
            "label": "run client",
            "type": "shell",
            "command": "mvn javafx:run",
            "group": "build",
            "presentation": {
                "echo": true,
                "reveal": "always",
                "focus": false,
                "panel": "shared"
            }
        }
    ]
}
```

## How to Import Project

### Method 1: Clone/Copy Project
1. Copy the entire project folder to your desired location
2. Open VS Code
3. File → Open Folder → Select the chat-application folder
4. VS Code will automatically detect it as a Java/Maven project

### Method 2: Import Existing Project
1. Open VS Code
2. Ctrl+Shift+P → "Java: Import Projects"
3. Select the project folder
4. Wait for Maven to download dependencies

## Running the Application in VS Code

### Option 1: Using Integrated Terminal
1. Open Terminal in VS Code (Ctrl+`)
2. Run server: `mvn exec:java`
3. Open new terminal tab
4. Run client: `mvn javafx:run`

### Option 2: Using Debug Configuration
1. Go to Run and Debug (Ctrl+Shift+D)
2. Select "Launch Chat Server" and click play
3. Select "Launch Chat Client" and click play

### Option 3: Using Tasks
1. Ctrl+Shift+P → "Tasks: Run Task"
2. Select "run server" or "run client"

## Debugging

### Server Debugging
1. Set breakpoints in server code
2. Use "Launch Chat Server" debug configuration
3. Debug with F5

### Client Debugging
1. Set breakpoints in client code
2. Use "Launch Chat Client" debug configuration
3. Debug with F5

## Database Management

### Using MySQL Extension
1. Install MySQL extension
2. Connect to your MySQL server
3. View tables and data directly in VS Code
4. Run SQL queries from the editor

### Using External Tools
- MySQL Workbench
- phpMyAdmin
- Command line mysql client

## Troubleshooting

### Common Issues

1. **JavaFX Module Path Error**
   - Download JavaFX SDK
   - Update launch.json with correct path
   - Or use Maven JavaFX plugin

2. **Maven Dependencies Not Loading**
   - Ctrl+Shift+P → "Java: Reload Projects"
   - Delete .vscode folder and restart VS Code

3. **Database Connection Issues**
   - Check MySQL is running
   - Verify credentials in DatabaseManager.java
   - Run database-setup.sql

4. **Port Already in Use**
   - Change port in ChatServer.java
   - Or kill existing process

## Useful VS Code Shortcuts

- `Ctrl+Shift+P`: Command Palette
- `Ctrl+`` `: Toggle Terminal
- `F5`: Start Debugging
- `Ctrl+F5`: Run Without Debugging
- `Ctrl+Shift+D`: Debug View
- `Ctrl+Shift+E`: Explorer View
- `Ctrl+Shift+G`: Source Control View

## Project Customization

### Adding New Features
1. Create new classes in appropriate packages
2. Update FXML files for UI changes
3. Modify CSS for styling
4. Update database schema if needed

### Changing Styling
- Edit `src/main/resources/css/style.css`
- Colors, fonts, layouts can be customized
- JavaFX CSS reference: https://openjfx.io/javadoc/17/javafx.graphics/javafx/scene/doc-files/cssref.html

### Database Changes
- Modify `DatabaseManager.java` for schema changes
- Update SQL files for new tables/columns
- Test with fresh database setup