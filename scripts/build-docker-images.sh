#!/bin/bash

sh ./gradlew build &&
docker build -t front:latest ./front/ &&
docker build -t gateway:latest ./gateway/ &&
docker build -t notifications:latest ./notifications/ &&
docker build -t accounts:latest ./accounts/ &&
docker build -t cash:latest ./cash/ &&
docker build -t transfer:latest ./transfer/