#!/usr/local/bin/perl
use CGI qw(:standard);
$data1 = param('perlemail') || '<i>(No input)</i>';
$data2 = param('perlpassword') || '<i>(No input)</i>';
print <<END;
Content-Type: text/html; charset=utf-8


<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>ECHOING LOGIN INPUT IN PERL</title>
<body>
<h1>
ECHOING LOGIN INPUT IN PERL<br>
Email: $data1<br>
Password: $data2<br>
</h1>
</body>
</html>


END
