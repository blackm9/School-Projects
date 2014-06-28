#!/usr/local/bin/perl
use CGI qw(:standard);

$data1 = param('perlemail') || '<i>(No input)</i>';
$data2 = param('perlpassword') || '<i>(No input)</i>';
$data3 = param('perlconfirmemail') || '<i>(No input)</i>';
$data4 = param('perlconfirmpassword') || '<i>(No input)</i>';
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
Confirm Email: $data3<br>
Password: $data2<br>
Confirm Password: $data4<br>
</h1>
</body>
</html>

END
