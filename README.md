[![concourse](https://concourse.devboclips.net/api/v1/pipelines/boclips/jobs/build-gateway/badge)]()

# gateway

A reactive API gateway funneling all the API requests and delegating it to the internal services.

Uses [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway) under the hood.

It is in charge of:
* Rate limiting the API
* Aggregating table of contents/links over all services

## Development

Run tests:
```
./gradlew test
```
