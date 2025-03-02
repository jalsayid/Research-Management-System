# Research Center Management System 🏢

## Introduction 👋
Welcome to the Research Center Management System! This application is designed to streamline and organize the activities of the KFUPM Research Center by providing a centralized platform for managing projects, teams, members, and machines. This system aims to eliminate miscommunication, enhance collaboration, and optimize the utilization of resources within the research center.

## Getting Started 🚀

### User Authentication 🔐
Before you begin, ensure that you have a valid user account provided by our IT department. The authentication system in place will grant you access to the application based on your role—admin, team member, or team leader.

### Dashboard Overview 📊
Upon signing in, you will be directed to your personalized dashboard. Here's what you can expect:

#### Admin Dashboard: 👨‍💼
- **Manage Projects**: Add new projects, assign teams, and update project details 📝
- **Manage Teams**: Create teams, assign leaders and members, and track team activities 👥
- **Manage Machines**: Add new machines, schedule view, and view machine analytics 🔧
- **Analytics**: Visualize machine utilization, project distribution, and member activity 📈

#### Member/Leader Dashboard: 👤
- **View Teams**: Access information about all teams you are part of 👥
- **View Projects**: See details about projects assigned to your team 📋
- **View Machines**: Check the availability and schedule machines for your team's use 🔧
- **Reserve Machines**: If available, reserve machines for your project's research ⏰

## User Instructions 📖

### For Admins 👨‍💼

#### Managing Projects: 📝
1. Navigate to the "Manage Projects" section
2. Add new projects by providing project details
3. Assign teams to projects and update project information

#### Managing Teams: 👥
1. Go to the Teams section
2. Create teams, assign leaders, and add team members

#### Managing Machines: 🔧
1. Access the Machines section
2. Add new machines with relevant details
3. Schedule machine usage, and keep track of reservations

#### Analytics: 📊
- Explore the analytics section to visualize key metrics
- Identify the most utilized machines, active teams, and projects

### For Members and Team Leaders 👥

#### Viewing Team Information: 👀
- Check the "View Teams" section to see details about all teams you are a part of

#### Viewing Projects: 📋
- Navigate to the "View Projects" section to access information about projects assigned to your team

#### Viewing Machines: 🔧
- Explore the "View Machines" section to see the availability and schedule of machines for your team

#### Reserving Machines: ⏰
- If available, reserve machines for your project's research in the "Reserve Machines" section

## Developer Instructions 👨‍💻

1. Make sure the FXML file specifies the correct controller class by adding the fx:controller attribute to the root element of the FXML file
2. Check that the labels in the FXML file have the correct fx:id attributes set. The fx:id should match the corresponding @FXML-annotated field in the controller class
3. Verify that the file paths in the FileReader are correct. Make sure the file paths point to the actual location of the text files ("MachineFile.txt", "TeamsFile.txt", "Member.txt", "Project.txt", etc..). You can use absolute paths or relative paths depending on your file structure
4. Verify that the FXML file is being loaded from the correct location. Ensure that the FXML file is in the same directory as the Java source files or provide the correct relative or absolute path to the FXML file when calling FXMLLoader.load()
5. Implement error handling and validation to provide a better user experience and prevent unexpected crashes or data inconsistencies

## Implementation Difficulties and Their Solutions ⚠️

1. **Null Pointer Exception** ❌: Ensure that you have correctly annotated the UI components with @FXML and that they match the corresponding IDs in the FXML file. Also, verify that the FXML file is being loaded properly.

2. **Incorrect UI rendering** 🖥️: Check that the FXML file is structured correctly and that the necessary UI components are defined. Ensure that the controller class is associated with the FXML file when loading it.

3. **Event handling not working** 🔄: Confirm that you have defined the event handlers in the controller class and that they are properly wired to the UI components in the FXML file using the onAction or similar attributes.
