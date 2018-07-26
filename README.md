# Scala SQL Injection

This is a project to show both unsafe and safe methods of saving user-entered data into a database, whilst protecting against malicious attacks via SQL injection.

## Running

Run this using [sbt](http://www.scala-sbt.org/).

```
sbt run
```

The original, insecure form will be on the URL:
`http://localhost:9000/original`

The following URLs correspond to three additional incorrect attempts to prevent SQL injection, and one secure solution.
`http://localhost:9000/incorrect1`
`http://localhost:9000/incorrect2`
`http://localhost:9000/incorrect3`
`http://localhost:9000/secure`