# Kotlin and Gradle template for Azure functions

Run Azure Function locally:

```shell
./gradlew jar --info
./graldew azureFunctionsRun
```

## Tests

Run tests by:

```shell
./gradlew test
```

## Troubleshooting

### WSL

You may need to change the Java path when running on WSL, find the Java Worker config:

```shell
readlink -f $(which func)
# E.g. /usr/lib/azure-functions-core-tools-3/func
```

Go into the directory of the Java Worker:

```shell
cd /usr/lib/azure-functions-core-tools-3/workers/java
```

Edit the worker config and specify the correct Java executable:

```shell
# Find java executable:
which java
# E.g. /usr/bin/java
```

Edit `workers.config.json`:

```diff
  {
      "description": {
          "language": "java",
          "extensions": [".jar"],
-         "defaultExecutablePath": "%JAVA_HOME%/bin/java",
+         "defaultExecutablePath": "/usr/bin/java",
          "defaultWorkerPath": "azure-functions-java-worker.jar",
          "arguments": ["-XX:+TieredCompilation -XX:TieredStopAtLevel=1 -noverify -Djava.net.preferIPv4Stack=true -jar", "%JAVA_OPTS%", "%AZURE_FUNCTIONS_MESH_JAVA_OPTS%"]
      }
  }
```

Restart IntelliJ and try again.
