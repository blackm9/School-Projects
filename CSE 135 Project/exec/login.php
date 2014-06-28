#!/usr/local/bin/php-cgi
<?php 

  $email= htmlspecialchars($_POST['phpemail']);
  $password  = htmlspecialchars($_POST['phppassword']);

 echo '<!DOCTYPE html>';
 echo '<html>';
 echo '<head>';
 echo '<meta charset="utf-8">';
 echo '<title>';
 echo "LOGIN IN PHP";
 echo '</title>';
 echo '<body>';
 echo '<h1>';
 echo "ECHOING LOGIN INPUT IN PHP", '<br>';
 echo "Email: $email", '<br>';
 echo "Password: $password", '<br>';
 echo '</h1>';
 echo '</body>';
 echo '</html>';
 ?> 


