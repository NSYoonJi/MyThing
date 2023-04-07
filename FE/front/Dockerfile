# base image
FROM node:16.9.0-alpine AS build

# set working directory
WORKDIR /app

# install dependencies
COPY package*.json ./
RUN npm install

# build app
COPY . .
RUN npm run build

FROM nginx:alpine

# copy custom Nginx config
COPY nginx.conf /etc/nginx/conf.d/default.conf

# set working directory
WORKDIR /app

# copy build files from build container
COPY --from=build /app/build /app

# expose port
EXPOSE 80

# start Nginx server
CMD ["nginx", "-g", "daemon off;"]
