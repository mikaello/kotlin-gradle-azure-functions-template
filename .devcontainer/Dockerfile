FROM mcr.microsoft.com/azure-functions/java:4-java11-core-tools

### CHANGES FROM https://github.com/microsoft/vscode-dev-containers/tree/main/containers/azure-functions-java-11/

# Avoid warnings by switching to noninteractive
ENV DEBIAN_FRONTEND=noninteractive

# Comment out these lines if you want to use zsh.
ARG INSTALL_ZSH=true
ARG USERNAME=vscode
ARG USER_UID=1000
ARG USER_GID=$USER_UID

 RUN apt-get update && curl -ssL https://raw.githubusercontent.com/microsoft/vscode-dev-containers/main/script-library/common-debian.sh -o /tmp/common-script.sh \
   && /bin/bash /tmp/common-script.sh "$INSTALL_ZSH" "$USERNAME" "$USER_UID" "$USER_GID" \
   && rm /tmp/common-script.sh

### END CHANGES


RUN apt-get update && apt-get -y vim tree install exa fd-find silversearcher-ag

# renovate: datasource=github-releases depName=pinterest/ktlint
ENV KTLINT_VERSION=0.46.0
RUN curl -sSfLO https://github.com/pinterest/ktlint/releases/download/${KTLINT_VERSION}/ktlint \
  && chmod a+x ktlint \
  && mv ktlint /usr/local/bin

# renovate: datasource=github-releases depName=JetBrains/kotlin
ENV KOTLIN_VERSION=1.7.0
RUN curl -sSfLo kotlinc.zip https://github.com/JetBrains/kotlin/releases/download/v${KOTLIN_VERSION}/kotlin-compiler-${KOTLIN_VERSION}.zip \
 && unzip kotlinc.zip -d /opt/ \
 && rm kotlinc.zip
# Added kotlinc to PATH in devcontainer.json (remoteEnv)
