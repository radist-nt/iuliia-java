# Releasing

## Before release checklist

- maven build is ok
- local repo sync with github

## Prepare release checklist

- incremented version in `pom.xml` and `README.md`
- added signed release commit with comment `release X.X.X`
- added tag `X.X.X` to commit
- commit and tag are pushed to github

## Release to maven

- Run command `mvn -P release deploy`
- Login to https://oss.sonatype.org/, navigate to staging repositories, verify repository and promote it




