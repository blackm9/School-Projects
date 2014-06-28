#!/usr/local/bin/php-cgi
<?php 
  $email= htmlspecialchars($_POST['phpemail']);
  $password  = htmlspecialchars($_POST['phppassword']);
  $confirmemail= htmlspecialchars($_POST['phpconfirmemail']);
  $confirmpassword  = htmlspecialchars($_POST['phpconfirmpassword']);

 echo '<!DOCTYPE html>';
 echo '<html>';
 echo '<head>';
 echo '<meta charset="utf-8">';
 echo '<title>';
 echo "SIGNUP IN PHP";
 echo '</title>'; 
 echo '<body>';
 echo '<h1>';
 echo "ECHOING SIGNUP INPUT IN PHP", '<br>';;
 echo "Email: $email", '<br>';
 echo "Confirm Email: $confirmemail", '<br>';
 echo "Password: $password", '<br>';
 echo "Confirm Password: $confirmpassword";
 echo '</h1>';
 echo '</body>';
 echo '</html>';
 ?> 


