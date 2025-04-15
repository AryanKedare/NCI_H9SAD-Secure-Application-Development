BUILD OUTPUT DESCRIPTION

When you build an Java application project that has a main class, the IDE
automatically copies all of the JAR
files on the projects classpath to your projects dist/lib folder. The IDE
also adds each of the JAR files to the Class-Path element in the application
JAR files manifest file (MANIFEST.MF).

Notes:

* If two JAR files on the project classpath have the same name, only the first
JAR file is copied to the lib folder.
* Only JAR files are copied to the lib folder.
If the classpath contains other types of files or folders, these files (folders)
are not copied.
* If a library on the projects classpath also has a Class-Path element
specified in the manifest,the content of the Class-Path element has to be on
the projects runtime path.
* To set a main class in a standard Java project, right-click the project node
in the Projects window and choose Properties. Then click Run and enter the
class name in the Main Class field. Alternatively, you can manually type the
class name in the manifest Main-Class element.

Java Version : 1.8
Link: https://www.azul.com/core-post-download/?endpoint=zulu&uuid=70dfad0a-f00b-4f73-a773-850c93ba0979

MySQL Version: 8.0.41
Link: https://dev.mysql.com/get/Downloads/MySQLInstaller/mysql-installer-community-8.0.41.0.msi

Building
Download ANT Target Runner from VS Code Extension Link: https://marketplace.visualstudio.com/items/?itemName=nickheap.vscode-ant

```ant clean```

```ant jar```

```ant -buildfile .\build.xml default```

```java -jar "dist/securityProject.jar"```
