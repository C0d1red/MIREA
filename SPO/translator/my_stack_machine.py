from translator.my_token import Token


def fun_val_search(name, list):
    for i in range(len(list)):
        if list[i][0] == name:
            return i
    return -1


def operation(e1, e2, op):
    if op == '-':
        return Token('DIGIT', float(e1) - float(e2))
    if op == '+':
        return Token('DIGIT', float(e1) + float(e2))
    if op == '*':
        return Token('DIGIT', float(e1) * float(e2))
    if op == '/':
        return Token('DIGIT', round(float(e1) / float(e2), 3))
    if op == '==':
        return Token('BOOL', float(e1) == float(e2))
    if op == '!=':
        return Token('BOOL', float(e1) != float(e2))
    if op == '>':
        return Token('BOOL', float(e1) > float(e2))
    if op == '<':
        return Token('BOOL', float(e1) < float(e2))


class StackMachine:
    def __init__(self, tokens, value_table, functions):
        self.tokens = tokens
        self.stack = []
        self.output = []
        self.functions = functions
        self.value_table = value_table
        self.token_count = 0

    def add_value(self, value):
        self.stack.append(value)

    def process(self, thread_fl=False):
        while self.token_count < len(self.tokens):
            if self.tokens[self.token_count].get_type() == 'VAR' or self.tokens[self.token_count].get_type() == 'DIGIT':
                self.stack.append(self.tokens[self.token_count])
            elif self.tokens[self.token_count].get_type() == 'FN_VALUE':
                if thread_fl:
                    out = self.__fun_calculate(self.tokens[self.token_count].get_value(),
                                               self.tokens[self.token_count + 1], True)
                    self.token_count += 2
                    return 'wait', out
                else:
                    out = self.__fun_calculate(self.tokens[self.token_count].get_value(), self.tokens[self.token_count + 1])
                self.stack.append(out)
                self.token_count += 1

            elif self.tokens[self.token_count].get_type() == 'ARI_OP':
                    self.stack.append(self.__calculate(self.tokens[self.token_count]))
            elif self.tokens[self.token_count].get_type() == 'ASSIGN_OP':
                self.__assign_op()
            elif self.tokens[self.token_count].get_type() == 'LOG_OP':
                self.stack.append(self.__calculate(self.tokens[self.token_count]))
            elif self.tokens[self.token_count].get_type() == 'GO_F':
                flag = self.stack.pop().get_value()
                if not flag:
                    self.token_count = self.tokens[self.token_count].get_value()
            elif self.tokens[self.token_count].get_type() == 'GO_A':
                self.token_count = self.tokens[self.token_count].get_value()
            if thread_fl and (self.tokens[self.token_count].get_type() in ['ARI_OP', 'ASSIGN_OP', 'LOG_OP']) and self.token_count + 2 < len(self.tokens):
                self.token_count += 1
                return 'ready', []
            self.token_count += 1
        if thread_fl:
            if self.tokens[-1].get_type() != 'RETURN':
                return 'exit', self.value_table
            else:
                out = self.stack.pop()
                if out.get_type() == 'VAR':
                    var = self.__find_value(out.get_value())
                    return 'exit', Token('DIGIT', var)
                else:
                    return 'exit', Token('DIGIT', out.get_value())
        print(self.value_table)

    def __fun_calculate(self, fn_value, fn_name, fn_fl = False):
        fun_val = []
        fun_ind = self.__search_fun_index(fn_name.get_value())
        fun_tokens = self.functions[fun_ind][-1]
        fun_stack = []
        for i in range(len(fn_value)):
            if fn_value[i].get_type() == 'VAR':
                e = self.__find_value(fn_value[i].get_value())
                fun_val.append([self.functions[fun_ind][1][i], e])
            else:
                fun_val.append([self.functions[fun_ind][1][i], fn_value[i].get_value()])
        if fn_fl:
            return [fn_name, fun_tokens, fun_val, self.functions]
        for i in range(len(fun_tokens)-1):
            if fun_tokens[i].get_type() == 'VAR' or fun_tokens[i].get_type() == 'DIGIT':
                fun_stack.append(fun_tokens[i])
            elif fun_tokens[i].get_type() == 'ARI_OP':
                e2 = fun_stack.pop()
                e1 = fun_stack.pop()
                if e1.get_type() == 'VAR':
                    ind = fun_val_search(e1.get_value(), fun_val)
                    if ind != -1:
                        e1 = fun_val[ind][-1]
                    else:
                        e1 = self.__find_value(e1.get_value())
                else:
                    e1 = e1.get_value()
                if e2.get_type() == 'VAR':
                    ind = fun_val_search(e2.get_value(), fun_val)
                    if ind != -1:
                        e2 = fun_val[ind][-1]
                    else:
                        e2 = self.__find_value(e2.get_value())
                else:
                    e2 = e2.get_value()
                fun_stack.append(operation(e1, e2, fun_tokens[i].get_value()))
            elif fun_tokens[i].get_type() == 'ASSIGN_OP':
                e2 = fun_stack.pop()
                e1 = fun_stack.pop()
                flag = True
                for i in range(len(fun_val)):
                    if e1.get_value() == fun_val[i][0]:
                        flag = False
                        fun_val[i][-1] = e2.get_value()
                if flag:
                    fun_val.append([e1.get_value(), e2.get_value()])
            elif fun_tokens[i].get_type() == 'FN_VALUE':
                func = self.__fun_calculate(fun_tokens[i].get_value(), fun_tokens[i + 1])
                fun_stack.append(func)
                i += 1
        out = fun_stack.pop()
        if out.get_type() == 'VAR':
            index = fun_val_search(out.get_value(), fun_val)
            return Token('DIGIT',fun_val[index][-1])
        else:
            return Token('DIGIT',out.get_value())

    def __calculate(self, op):
        e2 = self.stack.pop()
        e1 = self.stack.pop()
        if e1.get_type() == 'VAR':
            e1 = self.__find_value(e1.get_value())
        else:
            e1 = e1.get_value()
        if e2.get_type() == 'VAR':
            e2 = self.__find_value(e2.get_value())
        else:
            e2 = e2.get_value()
        return operation(e1, e2, op.get_value())

    def __assign_op(self):
        e2 = self.stack.pop()
        e1 = self.stack.pop()
        flag = True
        for i in range(len(self.value_table)):
            if e1.get_value() == self.value_table[i][0]:
                flag = False
                self.value_table[i][-1] = e2.get_value()
        if flag:
            self.value_table.append([e1.get_value(), e2.get_value()])

    def __find_value(self, name):
        for i in range(len(self.value_table)):
            if name == self.value_table[i][0]:
                return self.value_table[i][-1]

    def __search_fun_index(self, name):
        for i in range(len(self.functions)):
            if self.functions[i][0] == name:
                return i
        return -1
