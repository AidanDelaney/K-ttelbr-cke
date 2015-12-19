# Köttelbrücke

Implements a web service that runs [venNom](https://github.com/aidandelaney/vennom) to draw Euler diagrams.  It is primarily an interface between GNU R and the vennom drawing package.

## Building

This project uses a Java standard maven build system.  For ease of deployment the project also has a Makefile.  To build the product the following is currently needed:

1. A pre-release version of [javaGeom](https://github.com/dlegland/javaGeom) and note their installation instructions that include installing [gpcj](https://github.com/dlegland/javaGeom/wiki/Configuration).
2. A pre-release version of [vennom](https://github.com/aidandelaney/vennom): this is a straightforward `mvn install`.
3. And similarly, a pre-release version of [venn-diagram-types](https://github.com/AidanDelaney/venn-diagram-types/): again `mvn install`.

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

### Non-docker deployment

If you're running the bridge for development purposes, then point your IDE at `main.java` and tell it to run it.
