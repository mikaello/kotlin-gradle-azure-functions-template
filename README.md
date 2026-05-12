# Kotlin and Gradle template for Azure functions

[![Kotlin / Gradle CI](https://github.com/mikaello/kotlin-gradle-azure-functions-template/actions/workflows/build-and-test.yml/badge.svg)](https://github.com/mikaello/kotlin-gradle-azure-functions-template/actions/workflows/build-and-test.yml)

Run the Azure Function locally:

```shell
./gradlew azureFunctionsRun
```

Then invoke it:

```shell
curl "http://localhost:7071/api/HttpTrigger-Java?name=World"
# or
curl -d "World" "http://localhost:7071/api/HttpTrigger-Java"
```

## Prerequisites

To develop functions using Kotlin, you must have the following installed:

- [Java Developer Kit](https://learn.microsoft.com/en-us/java/openjdk/download), version 21
- [Optional] [Azure CLI](https://docs.microsoft.com/en-us/cli/azure) (needed to deploy)
- [Azure Functions Core Tools](https://docs.microsoft.com/en-us/azure/azure-functions/functions-run-local) version 4.x or above

A [Dev Container](.devcontainer/devcontainer.json) is provided with all required tools pre-installed.

## Tests

Run tests by:

```shell
./gradlew test
```

The CI runs `./gradlew build` (which includes `test`) on every push and pull request — see [`.github/workflows/build-and-test.yml`](.github/workflows/build-and-test.yml).

## Deploy

Update the `azurefunctions { ... }` block in [`build.gradle`](build.gradle) (at minimum `resourceGroup`, `appName`, `region`) to match your Azure subscription, then:

```shell
az login
./gradlew azureFunctionsDeploy
```

See the [plugin docs](https://github.com/microsoft/azure-gradle-plugins/wiki/Azure-Functions-Gradle-Plugin) for all available configuration options.

## Customizing this template

After cloning, you will typically want to:

1. Rename the package `org.example` and the `group` in `build.gradle`.
2. Rename `Function.kt` and the `@FunctionName(...)` value to something descriptive.
3. Update `rootProject.name` in [`settings.gradle`](settings.gradle).
4. Update the `azurefunctions { ... }` block as described above.

## Troubleshooting

### WSL

You may need to change the Java path when running on WSL, find the Java Worker config:

```shell
readlink -f $(which func)
# E.g. /usr/lib/azure-functions-core-tools-4/func
```

Go into the directory of the Java Worker:

```shell
cd /usr/lib/azure-functions-core-tools-4/workers/java
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
