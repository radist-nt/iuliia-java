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

- run command `mvn -P release deploy`
- login to https://oss.sonatype.org/, navigate to staging repositories, verify repository, close and release it

## After release checklist

- navigate to https://github.com/radist-nt/iuliia-java/tags and create the release
- attach artifacts from target directory (3 jars, 1 pom, signatures) to release


