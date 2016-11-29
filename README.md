# BlogPlus

BlogPlus is a simple and lightweight blogging platform built using Spring framework, jQuery and MongoDB that provides the following features:

- [X] User registration
- [X] Authentication
- [X] Session management
- [X] Create a blog post
- [X] Add tags while creating a blog post
- [x] Edit a blog post
- [X] Comment on a blog post
- [X] Like or Dislike a blog post
- [X] View the latest and most popular posts in home page
- [X] View the posts created by a user
- [X] Add tags to an existing post
- [x] View posts created by a particular user



It also provides a "guest" browsing mode in which guests are provided a subset of above features like:

- [X] View the latest and most popular posts in home page
- [x] View posts created by a user


## Setting up locally:

### Prerequisites:
- Install MongoDB by following the steps detailed [here](https://docs.mongodb.com/v3.2/installation/)
- Download the latest version of jQeury library from [here](https://jquery.com/download/). Rename it to "jquery.js".

### Running the blog application
- Clone the repository.
- Copy the downloaded "jquery.js" file to "src/main/resources/public/js/" folder.
- In "src/main/resources/application.properties" file: 
  - change the "server.port" property value to the port number on which you wish the Blog Application be run.
  - Set the MongoDB details: host, port, db name, username and password.
- Run BlogApplication.java


