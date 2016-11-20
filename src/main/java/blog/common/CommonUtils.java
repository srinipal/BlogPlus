package blog.common;

/**
 * Created by srinivas.g on 18/11/16.
 */
public class CommonUtils {



    public static void main(String[] args) {
        String str = "<!DOCTYPE html>\n" +
                "<html xmlns:th=“http://www.thymeleaf.org”><head th:fragment=“site-head”>\n" +
                "    <meta charset=“UTF-8” />\n" +
                "    <link rel=“stylesheet” href=“../public/css/styles.css” th:href=“@{/css/styles.css}” />\n" +
                "    <link rel=“icon” href=“../public/img/favicon.ico” th:href=“@{/img/favicon.ico}” />\n" +
                "    <script src=“../public/js/jquery-3.1.1.min.js” th:src=“@{/js/jquery-3.1.1.min.js}”></script>\n" +
                "    <script src=“../public/js/blog-scripts.js” th:src=“@{/js/blog-scripts.js}”></script>\n" +
                "    <meta th:include=“this :: head” th:remove=“tag”/>\n" +
                "</head><body><header th:fragment=“site-header”>\n" +
                "    <a href=“index.html” th:href=“@{/}”><img src=\"../public/img/site-logo.png\" th:src=\"@{/img/site-logo.png}\" /></a>\n" +
                "    <a href=\"index.html\" th:href=\"@{/}\">Home</a>\n" +
                "    <a href=\"users/login.html\" th:href=\"@{/users/login}\">Login</a>\n" +
                "    <a href=\"users/register.html\" th:href=\"@{/users/register}\">Register</a>\n" +
                "    <a href=\"posts/index.html\" th:href=\"@{/posts}\">Posts</a>\n" +
                "    <a href=\"posts/create.html\" th:href=\"@{/posts/create}\">Create Post</a>\n" +
                "    <a href=\"users/index.html\" th:href=\"@{/users}\">Users</a>\n" +
                "    <div id=\"logged-in-info\"> <span>Hello, <b>(user)</b></span>\n" +
                "        <form method=\"post\" th:action=\"@{/users/logout}\">\n" +
                "            <input type=\"submit\" value=\"Logout\"/>\n" +
                "        </form>\n" +
                "    </div>\n" +
                "</header> <h1>Welcome</h1>\n" +
                "<p>Welcome to the Spring MVC Blog.</p> <footer th:fragment=\"site-footer\">\n" +
                "    &copy; Spring MVC Blog System, 2016\n" +
                "</footer></body></html>";
        //str = str.replace('\"','\"');
        str = str.replace((char)8220,'\"');
        str = str.replace((char)8221,'\"');

        System.out.println(str);
    }
}
