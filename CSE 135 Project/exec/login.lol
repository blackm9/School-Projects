#!/usr/bin/perl /usr/local/bin/yali
OBTW
  LOLCODE IZ AN ESOTERIC PROGRAMMIN LANGUAGE INSPIRD BY LOLSPEAK,
  TEH LANGUAGE EXPRESD IN EXAMPLEZ OV TEH LOLCAT INTERNET MEME.
  LERN MOAR AT http://en.wikipedia.org/wiki/LOLCODE
TLDR

HAI
  VISIBLE "Content-type: text/html\n"
  VISIBLE "<!DOCTYPE html>"
  VISIBLE "<html>\n<head>\n<meta charset='utf-8'>\n<title>Login in LOLCODE</title>\n<body>"
  
  BTW READ IN DA HTTP POST DATA
  I HAS A infoz
  GIMMEH LINE infoz

  BTW PARSE TEH PARAMETERS FRUM TEH POST DATA
  I HAS A addres ITZ ""
  I HAS A paasword ITZ ""

  BTW REGULAR EXPRESHUN 4 QUERY STRIN
  I HAS A seekret ITZ "email=(.*)&password=(.*)"

  IZ infoz SORTA seekret O RLY?
    YA RLY
      I HAS A addres ITZ SORTA 1
      I HAS A paasword ITZ SORTA 2
  KTHX

  BTW HANDLE %40 ENCODIN OV @ SYMBOL IN EMAIL ADDRES
  I HAS A email ITZ "(.*)%40(.*)"
  IZ addres SORTA email O RLY?
    YA RLY
      I HAS A usr ITZ SORTA 1
      I HAS A websiet ITZ SORTA 2
      LOL addres R usr N "@" N websiet
  KTHX

  BTW OUTPUT TEH PARSD DATA
  VISIBLE "<b>Form Data from LOLCODE</b><hr />"
  VISIBLE "<b>Email:</b> " N addres N  "<br />"
  VISIBLE "<b>Password:</b> " N paasword N  "<br />"

  VISIBLE "</body>\n</html>"

KTHXBYE
