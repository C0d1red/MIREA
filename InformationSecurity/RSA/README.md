# RSA
1) Create RSA object with random p, q (prime numbers). Also you can set own p and q.
2) Create public key and private key.
3) Now you can encrypt and decrypt terms.
<pre>
Terms will be crypt in number format like this:
          term "adl" -> 1.4.12
          crypt 1.4.12 -> cryptedNumber.cryptedNumber.cryptedNumber
Crypted terms will be decrypt back to terms like this:
          decrypt cryptedNumber.cryptedNumber.cryptedNumber -> derypted numbers 1.4.12
          1.4.12 to term -> term "adl"
Supports the following characters:
1) Сyrillic letters:
          1.1) АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ
          1.2) абвгдеёжзийклмнопрстуфхцчшщъыьэюя
2) Latin alphabet:
          2.1) ABCDEFGHIJKLMNOPQRSTUVWXYZ
          2.2) abcdefghijklmnopqrstuvwxyz
3) Punctuation marks: .,;:!?[]-
4) Digits: 0123456789
</pre>
---------
25.10.2020 Bogomolov R.D.
