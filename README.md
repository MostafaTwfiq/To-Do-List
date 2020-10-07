# To-Do-List Application

## :wave: Overview : 

Being always on the run has become the day to day norm in the modern world, every decision inches you closer to your perfect ideal, hence we made To-Do. To-Do is a minimalist todo application where you tailor and manage your day to day activities to your liking. With To-Do you can create tasks, prioritize them and sort them in out in to categories using the built-in tagging system. You can further add notes to your task for when it comes to execution.

## :tv: Preview :
![preview gif](https://media.giphy.com/media/W66PVvrZb2XiuKgzNn/giphy.gif)

## :page_with_curl: Installation instructions :

1) Follow the Docker installation guide from [here](https://docs.docker.com/engine/install/) .
2) Make Sure you have JDK13 installed. Can be downloaded from [here](https://www.oracle.com/java/technologies/javase-jdk13-downloads.html) .
3) Download and run the To-Do installer from [here](https://github.com/MostafaTwfiq/To-Do-List/releases/download/v1.0.0/TodoListAppInstaller.jar) .
4) Profit and live your day to the fullest potential :thumbsup:

## :wrench: Manual Installation instructions :
  ### :gear: Compiling from Source Files:
   1) Clone this Repo: 
        > ```git clone https://github.com/MostafaTwfiq/To-Do-List.git```
   2) Compile with maven using the following Commmand:
        > ```mvn clean javafx:run -e```
   3) Ensure that either mysql or the docker conatainer are runnning in the background before double clicking the jar.
   
 ### :floppy_disk: Setting Up the Database Client:
  #### :whale: Using Docker
   1) Follow the Docker installation guide from [here](https://docs.docker.com/engine/install/) .
   2) Pull the docker container from docker hub:
       > ```docker pull am429/todolist_app_database_container```
   3) run the docker container:
       > ```docker run -d -p 1212:3306 --name ToDoListAppContainer am429/todolist_app_database_container```
  #### :dolphin: Using MySQL WorkBench
   1) Clone this Repo: 
        > ```git clone https://github.com/MostafaTwfiq/To-Do-List.git```
   2) Download and install MySQL from [here](https://dev.mysql.com/doc/mysql-installation-excerpt/5.7/en/)
   3) Navigate to ``/src/Main/java/DataBase/DataBaseCreation``
   4) Ensure that MYSQL command-line client is in your ``PATH``
   5) Run the ```create_todo_list_data_base.sql``` using the following command:
        > ``` mysql -uroot -p <ROOT PASSSWORD> < create_todo_list_data_base.sql```
   6) Change the database connection in the ```src/Main/java/DataBase/DataBaseConnection.java``` class :
        <br/>
          from ```jdbc:mysql://0.0.0.0:1212/``` to ```jdbc:mysql://localhost:3306/```
   7) Compile the application from it's source files.                                                                               
## :raised_hands:	Acknowledgments :
* Bcrypt implementation by [jeremyh](https://github.com/jeremyh/jBCrypt)

## :books: Libraries used:
* [JFoenix](https://github.com/jfoenixadmin/JFoenix)
* [TrayNotification](https://github.com/PlusHaze/TrayNotification)
* [MySQL Driver](https://mvnrepository.com/artifact/mysql/mysql-connector-java)

## :handshake: Contributions : 
Any Contributions, issues and  bug reports are welcome!
<br/>
Feel free to open a pull request and make To-Do a better App.
