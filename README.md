# Octofinsights 

Basic web application which allows for the 
Tracking of Sales, Leads, Expenses and Projects

## Features

- [x] Track Leads
- [x] Track Sales
- [x] Track Expenses
- [x] Track Projects
- [x] Dashboard Widgets
    - [x] Monthly Profits Graph
    - [x] Open Leads Widget
    - [x] Business Value over time Graph
- [ ] use Google Calendar API to track work on Projects


### Feedback and Feature Requests
Further Features are planned.
Feedback is welcome. Please open an issue or contact
alex23667@gmail.com 


### Steps to self-host this web app:

You can self host this web app easily.

- packages you should install on your server: `jdk>=1.8` , `maven`, `mysql` 
- clone this repository to your server
- it could be that you need some jar files for JOOQ to function correctly
    - `jaxb-api-2.2.12.jar`, `jaxb-core-2.2.11.jar`, 
    `jaxb-impl-2.3.0.jar`, `jooq-3.11.11.jar`,
    `jooq-codegen-3.11.11.jar`, `jooq-meta-3.11.11.jar`
- [optional] setup a reverse proxy and subdomains for octofinsights.yourdomain.tld
- create the 'octofinsights' database on your mysql server by running `setup_tables.sql`
    - https://stackoverflow.com/questions/11407349/how-to-export-and-import-a-sql-file-from-command-line-with-options
- create a file `credentials.txt`
    - on the first line, your db username
    - on the second line, your db password
- edit `library.xml` and put in your jdbc url, username,password
- `./generate_classes.sh` 
    - this creates some classes which are needed to build this project
    - for this, it needs to connect to your mysql database
- `./serverScript.sh`
    - this builds the project
- `./serverScriptDeploy.sh`
    - this starts the server

### Other Stuff

https://console.developers.google.com/apis/dashboard?project=octofinsights&supportedpurview=project
