group08
=======

Homework 2:
-----------
We made a static page driven clickable prototype, focusing on a solid and thorough treatment of all of the states, links and functionality. You can view an overview of all of the pages built for this assignment by checking out our sitemap http://s8.level3.pint.com/sitemap.html.

A bit on our thought process and workflow. We started by working on an outline of all of the needed pages, what functions would appear and what other pages would link to. This process really illuminated just how many pages and states can arise out of even seemingly simple site functions. You can view our outline, which represents a rough draft of our site structure on Google Docs at https://docs.google.com/document/d/1l5rmWK5WJO4i41pAP08pjszH9rXrfIYs1MDuwTIvvNs/edit?usp=sharing

Then we focused on our prototype, adding and changing pages as we realized they were needed for a link or function on a particular page. We made separate static pages to represent different states, based on which type of user you are logged in and the state of the quiz you are interacting with. For example there are different pages for viewing your graded quizzes to add comments, and a grader's view of a regrade request with the student's comments. You can change which type of user you are viewing the prototype as by using the (PROTOTYPE) links on our login page at http://s8.level3.pint.com/login.html. Note that due to the static nature of the prototype, some links will end up taking you back to the student logged in state, like clicking on settings and then "quiz maker" to go back to the dashboard.

A note on setting passwords for new users. The functionality we had in mind for this was that when you create an account for another user (as a Teacher), it will send them an email with a link to activate their account. There is no password set for them initially. it is in a state like a password reset, where the first thing they must do is set their own new password.

For the CGI demonstration, we chose LOLCODE for our third language. Curious about LOLCODE? Here's a block style comment from one of our .lol source files:
OBTW
  LOLCODE IZ AN ESOTERIC PROGRAMMIN LANGUAGE INSPIRD BY LOLSPEAK,
  TEH LANGUAGE EXPRESD IN EXAMPLEZ OV TEH LOLCAT INTERNET MEME.
  LERN MOAR AT http://en.wikipedia.org/wiki/LOLCODE
TLDR

We hope you enjoy reading these scripts as much as we did writing them. You can view our CGI demo forms under CGI Demos on our site map at http://s8.level3.pint.com/sitemap.html.

-Group 8

Homework 1:
-----------

Links to the content generated as part of this assignment can be found by browsing to the Homework #1 page on our team page at http://s8.level3.pint.com/homework1.html

robots.txt:
	Currently we don't have anything we don't want indexed by robots. So we put some red herring entries that make it look like there is a wordpress install. There is also a "zz" entry that there is no reference to besides in robots.txt, so if someone is trying to load that, we know someone is up to no good.

GitHub deployment:
	We installed git on the web server, and have a checkout of our GitHub master branch in our /usr/local/www directory. The DocumentRoot for Apache is set to /usr/local/www/content, so only files from the content directory of our repository will be accesible on the web. There is a simple bash script in user's home directory /user/home/deploy.sh which will pull the latest specifically from the master branch. This way we can have in progress changes in a development or other personal branch and they will not be pulled by the server until they are merged into master.

Apache logging:
	Apache is logging to the standard /var/log/httpd-access.log and /var/log/httpd-error.log files. We installed the AWStats log analyzer and made a report available at http://s8.level3.pint.com/stats/awstats.pl

Compression:
	We are compressing textual content using mod_deflate. We generated some compression stats based on content size of a file of each type (html,css and javascript) as reported by Chrome's developer tools.

	index.html compressed from 1.0 KB to 682 B, 32% saved.
	css/error.css compressed from 1.4KB to 968 B, 31% saved.
	scripts/jquery-1.10.2.min.js compressed from 91.2 KB to 32.4 KB, 64% saved.

Server identity obfuscation:
	We are employing several techniques to obscure our server identity. We have custom 404 and 403 error pages. We have configured mod_security to remove identifying information from the Server header. We turned off ServerSignature in Apache so it hides server information from its error pages.

URL rewriting:
	We have configured mod_rewrite to deliver clean urls. There is not yet much in the way of significant php content, but our php test page can be accessed at http://s8.level3.pint.com/php and any .php file can be accessed without a .php extension, hiding the extension from our links. We also learned how to use mod_rewrite to convert urls like example.com/user.php?id=3 to something like example.com/user/3/ which we hope to utilize in the future.

PHP:
	expose_php is disabled in the php.ini so it does not add the powered by php header. We are using production settings for php to avoid showing php error pages.

HTML and CSS validation:
	All content has been validated on the w3c validator and validation links can be found on our Homework #1 page.