# Wave PayRoll Challenge

# How to build and run the web app (Ubuntu)

1. Install Maven - This is a build automation tool used for various Java projects
    ```
    $ Sudo apt-get install maven
    ```
    ```
    Sudo apt-get install maven
    ```
2. Install Postgresql - This is an open source database used to archive data for the application as requested 
    ```
    sudo apt-get install postgresql postgresql-contrib
    ```
3. Setup a password for Postgresql for the default user - This is a requirement before the application can use it
     ```
    sudo -u postgres psql
    ```
    ```
    ALTER USER postgres with encrypted password 'bA%&Y66HkT';
    ```
    *Please enter the password specified here as that is what the application expects to be able to to connect to the database. No other password will work. 
    
4. Install Tomcat and start it - this is a web server the application will be deployed to
    ```
    sudo apt-get install tomcat8
    ```
     ```
    systemctl start tomcat8
    ```
5. Install the Java Development Kit Package if not already present
    ```
    sudo apt-get install default-jdk
    ```

6. Go to the location where the git bundle was extracted and make sure you are in the root directory. Then enter the following build maven command in the terminal:

    ```
    mvn clean package
    ```

7. If the above command was successful, you should see a new "Target" folder in the directory. In there will be a file named PayrollChallenge.war. This needs to be deployed to the web server. The easiest way is to move PayrollChallenge.war to inside /var/lib/tomcat8/webapps. 

8. Start up Tomcat and go to http://localhost:8080/PayrollChallenge
    This is a simple web page that allows you to test out the applications functionality easily. You can upload new valid .csv files from here as well as get the expected report. Note you can also just go to http://localhost:8080/PayrollChallenge/GetReport? directsly to retrieve it. 

# Followup Questions

Q. How did you test that your implementation was correct?
A. I added multiple unit tests to test the different components seperately. 

Q. If this application was destined for a production environment, what would you add or change?
Q.What compromises did you have to make as a result of the time constraints of this challenge?

