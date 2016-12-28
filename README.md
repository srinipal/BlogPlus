# BlogPlus

Blog plus is a simple and light weight blogging platform built using Spring framework, jQuery and MongoDB which provides all the features that you expect in a blogging platform in the most intuitive and uncomplicated way. It follows the "inline edit" design pattern which lets users edit their posts in-place without having to navigate to a new web page. Similarly, the users can like/dislike posts, post their comments on existing posts, all in-place. All this is achieved by asynchronous AJAX requests made in the background which provide faster response to the user input while also providing a seamless user experience. In addition, the application also provides infinite scrolling feature which enables users to potentially scroll through all the blog posts in the different views(home page, user profile page, tagged posts, e.t.c.,) that are presented.

Following are the list of features provided:

- [X] User registration
- [X] Authentication
- [X] Session management
- [X] Create a blog post
- [X] Add tags while creating a blog post
- [x] Edit a blog post
- [X] Comment on a blog post
- [X] Like or Dislike a blog post
- [X] View the latest and most popular posts in home page
- [x] View posts created by a particular user
- [X] View posts by tag
- [X] Share post on Facebook and Twitter


It also provides a "guest" browsing mode in which guests are provided a subset of above features like:

- [X] View the latest and most popular posts in home page
- [x] View posts created by a user
- [X] View posts by tag

## Setting up locally:

### Prerequisites:
- Install MongoDB by following the steps detailed [here](https://docs.mongodb.com/v3.2/installation/) and run it on a desired port.
- Download the latest version of jQeury library from [here](https://jquery.com/download/). Rename it to "jquery.js".
- Download the latest version of Underscore library from [here](http://underscorejs.org/). Rename it to "underscore.js".

### Running the blog application
- Clone the repository.
- Copy the downloaded "jquery.js" and "underscore.js"  files to "src/main/resources/public/js/" folder.
- In "src/main/resources/application.properties" file: 
  - change the "server.port" property value to the port number on which you wish the Blog Application be run.
  - Set the MongoDB details: host, port, db name, username and password.
- Run BlogApplication.java


