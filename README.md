<h1 align="center">
Garzweiler
</h1>

<p align="center">
    <a href="https://github.com/bruinkool/garzweiler/commits/" title="Last Commit"><img src="https://img.shields.io/github/last-commit/bruinkool/garzweiler?style=flat"></a>
    <a href="https://github.com/bruinkool/garzweiler/issues" title="Open Issues"><img src="https://img.shields.io/github/issues/bruinkool/garzweiler?style=flat"></a>
    <a href="https://github.com/bruinkool/garzweiler/blob/main/LICENSE" title="License"><img src="https://img.shields.io/badge/License-MIT-green.svg"></a>
    <a href="https://bruinkool.land/" title="Bruinkoolland"><img src="https://img.shields.io/website?down_color=red&down_message=offline&label=bruinkool.land&up_color=green&up_message=online&url=https%3A%2F%2Fbruinkool.land"></a>
</p>

<p align="center">
  <a href="./LICENSE">Licensing</a>
</p>

Backend application for bruinkoolland.

**This project is under active development. Current API specification is very likely to change.**

## Development

### Prerequisites
- Java
- Maven
- MySQL database

After you've checked out this repository, you can run the application using [Maven](https://maven.apache.org) on your local machine.

```bash
  ./mvnw spring-boot:run
```

## OpenAPI

This repository exposes a RESTful server application using Java and Spring.
The OpenAPI specification can be browsed through the included Swagger UI:

e.g. http://localhost:8080/swagger-ui.html

### Generator

[OpenAPI Generator](https://openapi-generator.tech/docs/generators/) includes a list of templates that can be used to generate different clients for this API server.
