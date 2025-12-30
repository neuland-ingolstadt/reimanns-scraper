# reimanns-scraper

This service provides a GraphQL API for Reimann's weekly menu. It uses OpenAI to provide structured data from an
[otherwise unstructured WordPress based website](http://reimanns.in/mittagsgerichte-wochenkarte/).

The application is built as a **native binary** using GraalVM/Mandrel for optimal performance and minimal resource usage.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You need to provide your own OpenAI API key using the property `quarkus.langchain4j.openai.api-key`. For security reasons,
it is recommended to use an `.env` file located in the project’s root folder. This file is excluded from version control
through an entry in the `.gitignore` file.

Content of the `.env` file:

```properties
QUARKUS_LANGCHAIN4J_OPENAI_API_KEY=sk-your-api-key
```

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

This project is configured to build native executables by default in CI/CD. Native builds provide:
- **Fast startup time**: ~30ms vs several seconds for JVM
- **Low memory footprint**: Minimal runtime overhead
- **Small container images**: Using `Dockerfile.native-micro` for optimal size

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/reimanns-scraper-1.0.5-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Docker Deployment

### Building the Docker Image

The project includes multiple Dockerfile options:

- **`Dockerfile.native-micro`** (default in CI/CD): Minimal native image using UBI9 minimal base (~200MB total)
- **`Dockerfile.native`**: Native image using UBI9 minimal base (same size as native-micro)
- **`Dockerfile.jvm`**: Traditional JVM-based image (larger, slower startup)

To build the native Docker image locally:

```shell script
# Build the native binary
./mvnw package -Dnative -Dquarkus.native.container-build=true

# Build the Docker image
docker build -f src/main/docker/Dockerfile.native-micro -t reimanns-scraper:native .
```

### Running the Container

```shell script
docker run -i --rm -p 8080:8080 \
  -e QUARKUS_LANGCHAIN4J_OPENAI_API_KEY=your-api-key \
  reimanns-scraper:native
```

### CI/CD

The GitHub Actions workflow automatically builds and publishes native container images to `ghcr.io/neuland-ingolstadt/reimanns-scraper`.