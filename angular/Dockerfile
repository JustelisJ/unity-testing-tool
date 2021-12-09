FROM node:14.17.0-alpine

# get the app
WORKDIR /app
COPY . /app

# install packages
RUN npm install
RUN npm install -g @angular/cli

# start app
CMD ng serve --host 0.0.0.0