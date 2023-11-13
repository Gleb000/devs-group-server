## How to run

1. Provide to a run configuration next required env variables:
```
DATABASE_URL
DATABASE_USERNAME
DATABASE_PASSWORD
```
Also if it's required you can provide custom port: `SERVER_PORT`.

#### OR

2. Update `application-local.yml` file and provide your own values:
```yml
server:
  port: {value} - optional

spring:
  datasource:
    url: {value} - required
    username: {value} - required
    password: {value} - required
```

## Spent time on server part development
40m - reviewing requirements and implementing design;
4h-5h - implementation;
1h - manual testing and bugs fixing;
30m - connecting swagger and fixing issues with it.
