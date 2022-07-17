# Kotlin and Gradle template for Azure functions

Run Azure Function locally:

```shell
./gradlew jar --info
./gradlew azureFunctionsRun
```

## Prerequisites

To develop functions using Kotlin, you must have the following installed:

- [Java Developer Kit](https://docs.microsoft.com/en-us/azure/developer/java/fundamentals/java-jdk-long-term-support), version 8
- [Optional] [Azure CLI](https://docs.microsoft.com/en-us/cli/azure) (needed to deploy)
- [Azure Functions Core Tools](https://docs.microsoft.com/en-us/azure/azure-functions/functions-run-local#v2) version 2.6.666 or above
- [Gradle](https://gradle.org/), version 4.10 and above

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

## Related

See also:

* Documentation for the Gradle plugin used in this project: [com.microsoft.azure.azurefunctions](https://github.com/microsoft/azure-gradle-plugins/wiki/Azure-Functions-Gradle-Plugin)
* [Azure-Samples/azure-functions-samples-java](https://github.com/Azure-Samples/azure-functions-samples-java) (azure functions + java + gradle)
* [Starter doc](https://docs.microsoft.com/en-us/azure/azure-functions/functions-create-maven-kotlin-intellij) for creating azure functions + kotlin + maven
