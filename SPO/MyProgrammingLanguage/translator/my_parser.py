def is_token_of_type(type, found_t):
    if type == found_t:
        return True
    else:
        return False


class Parser:
    def __init__(self, tokens):
        self.tokens = tokens
        self.position = 0
        self.fn_list = []

    def process(self):
        is_correct = True
        while self.position < len(self.tokens):
            is_correct, pos = self.__create_expr(self.position)
            if not is_correct:
                raise Exception('Error while parser processing')
        return is_correct

    def __create_expr(self, num):
        expr, new_pos = self.__create_assign_expr(num)
        expr_if, new_pos_if = self.__create_if_expr(num)
        expr_while, new_pos_while = self.__create_while_expr(num)
        expr_fn, new_pos_fn = self.__create_function_expr(num)
        self.position = new_pos_if + new_pos + new_pos_while + new_pos_fn
        return expr or expr_if or expr_while or expr_fn, self.position

    def __create_if_expr(self, num):
        if not self.__is_token(num):
            return False, 0
        is_expr, num = self.__create_head(num + 1)
        is_ex, num = self.__create_body(num)
        is_el = True
        num += 1
        if self.__else_t(num):
            is_el, num = self.__create_body(num + 1)
        return is_expr and is_ex and is_el, num + 1

    def __create_function_expr(self, num):
        if not (self.__function_t(num) and self.__op(num + 1)):
            return False, 0
        func_expr, num = self.__function_head(num + 2)
        if not func_expr:
            return False, 0
        else:
            is_function, num = self.__function_body(num + 1)
            return is_function, num

    def __function_body(self, num):
        body = self.__ob(num)
        num += 1
        return_expr, current_pos = self.__create_return_expr(num)
        if return_expr:
            return return_expr, current_pos + 1
        while num <= len(self.tokens):
            if self.__cb(num):
                return body, num
            num1 = num
            body, num = self.__create_expr(num)
            if not body:
                body, num = self.__create_return_expr(num1)
                if not body:
                    return False, 0
                else:
                    return True, num + 1
        return body, num

    def __create_return_expr(self, num):
        return self.__return_op(num) and self.__create_value(num + 1) and self.__semicolon(num + 2), num + 3

    def __function_head(self, num):
        i = num
        while self.__var(i):
            i += 1
        if self.__cp(i):
            return True, i
        else:
            return False, 0

    def __create_while_expr(self, num):
        if not self.__while_t(num):
            return False, 0
        while_expr, num = self.__create_head(num + 1)
        while_ex, num = self.__create_body(num)
        return while_expr and while_ex, num + 1

    def __create_body(self, num):
        body = self.__ob(num)
        num += 1
        while num <= len(self.tokens):
            if self.__cb(num):
                return body, num
            body, num = self.__create_expr(num)
            if not body:
                return False, 0
        return body, num

    def __create_head(self, num):
        return self.__op(num) and self.__create_log_expr(num + 1) and self.__cp(num + 4), num + 5

    def __create_log_expr(self, n):
        return self.__create_value(n) and self.__log_op(n + 1) and self.__create_value(n + 2)

    def __create_assign_expr(self, num):
        var = self.__var(num)
        assign = self.__assign(num + 1)
        if not (var and assign):
            return False, 0
        value_expr, new_pos = self.__create_value_expr(num + 2)
        if var and assign and value_expr:
            return True, 2 + new_pos
        else:
            return False, 0

    def __create_value_expr(self, n):
        op_c = 0
        cp_c = 0
        val2 = True
        while not self.__semicolon(n):
            if self.__cp(n):
                cp_c += 1
            if self.__op(n):
                op_c += 1
            val2 = val2 and (self.__create_value(n) or self.__ari_op(n) or self.__cp(n) or self.__op(n))
            n += 1
            if not val2:
                return False, 0
        if op_c != cp_c:
            return False, 0
        return val2, n - 1

    def __create_value(self, num):
        if num >= len(self.tokens):
            return False
        return self.__var(num) or self.__digit(num) or self.__create_function(num)

    def __create_function(self, num):
        if self.tokens[num].get_value() in self.fn_list and self.__op(num + 1):
            num = num + 2
            while self.__create_value(num):
                num += 1
            if self.__cp(num):
                return True
            else:
                return False
        else:
            return False

    def __semicolon(self, num):
        if num >= len(self.tokens):
            return False
        return is_token_of_type(self.tokens[num].get_type(), 'SEMICOLON')

    def __var(self, num):
        if num >= len(self.tokens):
            return False
        return is_token_of_type(self.tokens[num].get_type(), 'VAR')

    def __assign(self, num):
        if num >= len(self.tokens):
            return False
        return is_token_of_type(self.tokens[num].get_type(), 'ASSIGN_OP')

    def __digit(self, num):
        if num >= len(self.tokens):
            return False
        return is_token_of_type(self.tokens[num].get_type(), 'DIGIT')

    def __ari_op(self, num):
        if num >= len(self.tokens):
            return False
        return is_token_of_type(self.tokens[num].get_type(), 'ARI_OP')

    def __return_op(self, num):
        if num >= len(self.tokens):
            return False
        return is_token_of_type(self.tokens[num].get_type(), 'RETURN')

    def __function_t(self, num):
        if num >= len(self.tokens):
            return False
        exp = is_token_of_type(self.tokens[num].get_type(), 'FN')
        if exp:
            self.fn_list.append(self.tokens[num].get_value()[len('function '):])
        return is_token_of_type(self.tokens[num].get_type(), 'FN')

    def __op(self, num):
        if num >= len(self.tokens):
            return False
        return is_token_of_type(self.tokens[num].get_type(), 'OP')

    def __cp(self, num):
        if num >= len(self.tokens):
            return False
        return is_token_of_type(self.tokens[num].get_type(), 'CP')

    def __is_token(self, num):
        if num >= len(self.tokens):
            return False
        return is_token_of_type(self.tokens[num].get_type(), 'IF')

    def __while_t(self, num):
        if num >= len(self.tokens):
            return False
        return is_token_of_type(self.tokens[num].get_type(), 'WHILE')

    def __else_t(self, num):
        if num >= len(self.tokens):
            return False
        return is_token_of_type(self.tokens[num].get_type(), 'ELSE')

    def __ob(self, num):
        if num >= len(self.tokens):
            return False
        return is_token_of_type(self.tokens[num].get_type(), 'OB')

    def __cb(self, num):
        if num >= len(self.tokens):
            return False
        return is_token_of_type(self.tokens[num].get_type(), 'CB')

    def __log_op(self, num):
        if num >= len(self.tokens):
            return False
        return is_token_of_type(self.tokens[num].get_type(), 'LOG_OP')
