# nodejs-aws-deployment/Dockerfile

FROM node:18

WORKDIR /usr/src/app

COPY app/package*.json ./

RUN npm install

COPY app/ .

EXPOSE 8080
CMD [ "node", "server.js" ]