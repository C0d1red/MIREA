import re
from translator.my_token import Token


class Lexer:
    def __init__(self):
        self.tokens = []
        self.rules = [('0|([1-9][0-9]*)', 'DIGIT'),
                      ('if', 'IF'),
                      ('while', 'WHILE'),
                      ('function\s[a-zA-Z]+\d*', 'FN'),
                      ('else', 'ELSE'),
                      ('return', 'RETURN'),
                      ('[a-zA-Z]+\d*', 'VAR'),
                      ('\+|-|\*|/', 'ARI_OP'),
                      ('==|!=|<|>', 'LOG_OP'),
                      ('\(', 'OP'),
                      ('\)', 'CP'),
                      ('{', 'OB'),
                      ('}', 'CB'),
                      (';', 'SEMICOLON'),
                      ('=', 'ASSIGN_OP')]

    def print_tokens(self):
        for token in self.tokens:
            print('''({}: '{}')'''.format(token.get_type(), token.get_value()), end=' ')
        print()

    def process(self, text):
        regex = re.compile('function\s[a-zA-Z]+\d*|==|!=|\d+|[a-zA-Z]+\d*|[-+*/=;<>{}()]')
        words = regex.findall(text)
        for word in words:
            for regex, token_type in self.rules:
                if re.match(re.compile(regex), word):
                    self.tokens.append(Token(token_type, word))
                    break
        return self.tokens
