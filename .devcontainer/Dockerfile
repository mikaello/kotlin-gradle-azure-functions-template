FROM mcr.microsoft.com/azure-functions/java:4-java17-core-tools

USER root

RUN apt-get install exa fd-find silversearcher-ag

# renovate: datasource=github-releases depName=pinterest/ktlint
ARG KTLINT_VERSION=0.46.0
RUN curl -sSfLO https://github.com/pinterest/ktlint/releases/download/${KTLINT_VERSION}/ktlint \
  && chmod a+x ktlint \
  && mv ktlint /usr/local/bin

# renovate: datasource=github-releases depName=JetBrains/kotlin
ARG KOTLIN_VERSION=1.7.0
RUN curl -sSfLo kotlinc.zip https://github.com/JetBrains/kotlin/releases/download/v${KOTLIN_VERSION}/kotlin-compiler-${KOTLIN_VERSION}.zip \
 && unzip kotlinc.zip -d /opt/ \
 && rm kotlinc.zip

USER vscode