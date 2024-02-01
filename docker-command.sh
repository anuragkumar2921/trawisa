#!/bin/bash
docker rmi -f trawisa-api || true && docker build -t trawisa-api . && docker run -p 8080:8080 -p 5005:5005 trawisa-api
# docker rmi -f trawisa-api || true && docker build -t trawisa-api .
