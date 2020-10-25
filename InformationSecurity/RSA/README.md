# RSA
1) Create RSA object with random p, q (prime numbers). Also you can set own p and q.
2) Create public key and private key.
3) Now you can encrypt and decrypt terms.
Terms will be crypt in number format like this: 
<p>          term "adl" -> 1.4.12</p>
<p>          crypt 1.4.12 -> cryptedNumber.cryptedNumber.cryptedNumber</p>
Crypted terms will be decrypt back to terms like this: 
<p>          decrypt cryptedNumber.cryptedNumber.cryptedNumber -> derypted numbers 1.4.12</p>
<p>          1.4.12 to term -> term "adl"</p>
Supports the following characters:
1) Сyrillic letters:
<p>          1.1) АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ</p>
<p>          1.2) абвгдеёжзийклмнопрстуфхцчшщъыьэюя</p>
2) Latin alphabet:
<p>          2.1) ABCDEFGHIJKLMNOPQRSTUVWXYZ</p>
<p>          2.2) abcdefghijklmnopqrstuvwxyz</p>
3) Punctuation marks: .,;:!?[]-
4) Digits: 0123456789

---------
25.10.2020 Bogomolov R.D.
