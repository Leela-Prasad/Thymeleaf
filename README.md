Thymeleaf will force all html to be XHTML, means the html should be a well formed XML Document.

<p>Start Line </p>
<br>
<p>End Line </p>
Above document is valid HTML but not valid XHTML because there is closing tag for "br"


In Spring Boot Thymeleaf will refer all html files/templates from src/main/resources/templates
and static contents like html,js from src/main/resources/static
In Thymeleaf all html files are referred as templates.

th:text will replace the text for a particular tag dynamically.
<p th:text="'First line'">Start Line </p>
Now this paragraph content "Start Line" will be replaced by "First Line" Content

now if
<p th:text="'a <strong>Good</strong> Programmer'">Your Description</p>
Now in the output we will get
a &lt;strong&gt;Good Programmer
because th:text will replace special characters with its equivalent value.

so keep this unchanged we need to use th:utext
But we need to keep in mind that th:utext raises some security issues, as users can inject some malicious
javascript code, so beware when you use utext.
<p th:utext="'a <strong>Good</strong> Programmer'">Your Description</p>
output: a Good Programmer

Utility Objects:
In Thymeleaf if we want to use utility objects then we need to use '#' symbol
eg: to format the date
<td th:text="${#dates.format(model.dob,'dd-MMM-yyyy')}">17-JAN-1946</td>

In Thymeleaf
${} means evaluate an expression against model object.
@{} means evaluate an expression as a url.

<p><a th:href=@{/} href="homepage.html">Click Here</a> to go back to Home Page</p>


In Thymeleaf url parameters are handled in a different way
<a th:href="@{/profile?id=} + ${id}" href="profile.html?id=id">View my profile</a>

<a th:href="@{/profile(id=${id})}" href="profile.html?id=id">View my profile</a>
Syntax : Replace ? with  (key1=value,key2=value2)
For multiple parameters
<a th:href="@{/profile(id=${id},trail=1)}" href="profile.html?id=id">View my profile</a>

Fragments are used to create reuseable HTML blocks that might be needed in different pages.
eg : header and footer
     stylesheets that are needed for each page.

     <html xmlns:th="http://www.thymeleaf.org">

     	<body>
     		<div th:fragment="header-menu">
     			<a href="/">Home Page</a>
     			<a href="/profile">User Profile</a>
     		</div>

     		<p th:fragment="footer-menu">
     			<a href="/privacy">Privacy</a>
     			<a href="terms">Terms and Conditions</a>
     		</p>
     	</body>

     </html>

Usage:
<div th:replace="fragments/menu :: header-menu">Main menu goes here</div>
<div th:replace="fragments/menu :: footer-menu">footer menu goes here</div>

Local Variables:
We can create variables in Thymeleaf using th:with
The scope of th:with is within the tag.
<body th:with="fullname='Leela'">

</body>
Now fullname variables is accessible in entire body section

<body>

<table th:with="fullname='Leela'">

</table>
</body>
Here fullname is accessible inside table only.


Binding Form data to Backing Bean:
----------------------------------
When user submit form then that data can be given to a controller through by using backing bean.
If we want to bind the form data with backing bean then we need to use "*" symbol.
eg : th:field *{user.firstname}
    this will bind the input tag value to user bean firstname field.

Here user object is passed.
    <form action="#">
      <input type="text" name="username" id="username" placeholder="username" required="required" th:field=*{user.username}/>
    </form>
To bind username form data to the user object we need to use *
th:field=*{user.username}

If we know that the form is having one backing bean object then we can move that object as form parameter
so that we no need to repeat the "user." in every field.
For this we need to use "th:object" as form parameter

eg:
<form action="#" th:object="${user}">
  <input type="text" name="username" id="username" placeholder="username" required="required" th:field=*{username}/>
  <input type="text" name="firstname" id="firstname" placeholder="First name" required="required" th:field=*{firstName}/>
</form>


$ - expression evaluation
@ - path evaluation
# - Thymeleaf utility methods
* - to map form data with backing bean

In Normal Spring Application if we want to configure thymeleaf we need define below 3 beans in dispatcher-servlet.xml file

<bean class="org.thymeleaf.spring3.view.ThymeleafViewResolver">
	<property name="templateEngine" ref="templateEngine" />
	<property name="order" value="1" />
	<property name="viewNames" value="/templates/*" />
</bean>

<bean id="templateEngine" class="org.thymeleaf.spring3.SpringTemplateEngine" >
	<property name="templateResolver" ref="templateResolver" />
</bean>

<bean id="templateResolver" class="org.thymeleaf.spring3.templateresolver.SpringResourceTemplateResolver">
	<property name="prefix" value="/WEB-INF/templates/" />
	<property name="suffix" value=".html" />
	<property name="templateMode" value="HTML5" />
</bean>


Layouts:
========
Fragments are used to reuse common pieces in a page like header, footer , css etc. but there is one disadvantage
in this approach
this is we are repeating the fragment syntax in each and every page.

<div th:replace="templates/fragments/header :: links">Header</div>
<div th:replace="templates/fragments/footer :: footer">Footer</div>

If we change the fragment page name form header to something then all pages will be affected
(or)
what happens if we want to add another fragment which is common to all pages, then we need to add this new fragment
to all pages which is really a pain point and this use case is most common in enterprises.

so to solve this we will create a layout file which will have fragment pieces and the content placeholder to replace the incoming
page actual content. In future if we want to add new fragment then we can add to this layout which will be refleccted in all pages.

Example :
Layout file:

<html xmlns:th="http://www.thymeleaf.org" th:fragment="layout">

<head>
	<title>All books for our store</title>
	<link th:href="@{/styles.css}" href="/styles.css" rel="Stylesheet" type="text/css"/>
</head>

<body>
	<div th:replace="templates/fragments/header :: links">Header</div>

	<div th:replace="this :: content1">Content1</div>
	<div th:replace="this :: content2">Content2</div>

	<div th:replace="templates/fragments/footer :: footer">Footer</div>
</body>
</html>

Actual Page:
<html xmlns:th="http://www.thymeleaf.org" th:include="layouts/StandardLayout :: layout">

<body>

	<div id="books" th:fragment="content1">
		<ul >
			<li th:each="nextBook : ${allBooks}">
				<h2 th:text="${nextBook.title}">Title</h2>
				<p>
					by <span th:text="${nextBook.author}">author</span>
					  $	<span th:text="${#numbers.formatDecimal(nextBook.price,3,2,'COMMA')}">price</span>


					<form method='post' th:action="@{addToCart.do}" action='addToCart.do'>
						<input type='hidden' name='id' th:value="${nextBook.id}" value='${nextBook.id}'/>
						<input type='image' th:src="@{/cart-button.png}" src='cart-button.png'/>
				    </form>

				</p>

			</li>
		</ul>
	</div>

	<div th:fragment="content2"></div>
</body>

</html>


Passing Parameters to layouts:
==============================
In general layouts require some parameters which are dynamic for every page eg: title
we can pass this using (key1=value1,key2=value2) in each page

Page:
=====
 <html xmlns:th="http://www.thymeleaf.org" th:include="layouts/StandardLayout :: layout(title='View All Books')">

 </html>

Layout:
=======
<html xmlns:th="http://www.thymeleaf.org" th:fragment="layout(title)">

<head>
	<title th:text="${title}">Title</title>
	<link th:href="@{/styles.css}" href="/styles.css" rel="Stylesheet" type="text/css"/>
</head>

<body>
	<div th:replace="templates/fragments/header :: links">Header</div>

	<div th:replace="this :: content1">Content1</div>
	<div th:replace="this :: content2">Content2</div>

	<div th:replace="templates/fragments/footer :: footer">Footer</div>
</body>
</html>
