# Köttelbrücke

Implements a web service that runs [venNom](https://github.com/aidandelaney/vennom) to draw Euler diagrams.  It is primarily an interface between GNU R and the vennom drawing package.

## Building

This project uses a Java standard maven build system.  For ease of deployment the project also has a Makefile.  To build the product the following is currently needed:

1. An obscenely hacked version of [javaGeom](https://github.com/AidanDelaney/javaGeom)
2. https://github.com/aidandelaney/vennom
3. https://github.com/AidanDelaney/venn-diagram-types/

we are working on having a single-shot build process.

The actual build commands are either

```bash
$ mvn package
```

or
```bash
$ make build
```

## Deploying

Köttelbrücke will deploy on [Docker](https://www.docker.com/) using [docker-compose](https://docs.docker.com/compose/)

```bash
$ make
```

You should then be able access it using
```
$ curl -X POST http://localhost:8080
```
