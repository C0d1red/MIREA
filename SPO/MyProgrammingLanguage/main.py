from translator.my_lexer import Lexer
from translator.my_parser import Parser
from translator.my_polish_notation_converter import PN
from translator.my_stack_machine import StackMachine
from translator.my_triad import Triad
from translator.my_thread import ThreadManager
from translator.my_thread import Thread
import inquirer

SINGLE_MODE = 'Single'
MULTI_MODE = 'Multi'
FILE_NAME_1 = 'example_1.lng'
FILE_NAME_2 = 'example_2.lng'
thread_mode_questions = [inquirer.List('mode', message="Which thread mode to use?", choices=[SINGLE_MODE, MULTI_MODE])]
file_questions = [inquirer.List('file', message="Which example-file to use?", choices=[FILE_NAME_1, FILE_NAME_2])]

selected_file = inquirer.prompt(file_questions)['file']
f = open('res/code_examples/' + selected_file)
code = f.read()
f.close()

print('\nLexer:')
lexer = Lexer()
tokens = lexer.process(code)
lexer.print_tokens()

print('\nParser:')
parser = Parser(tokens)
parser_result = parser.process()
print(parser_result)

if parser_result:
    pn = PN(tokens)
    transfer, function = pn.process()
    tr = Triad(transfer, function)
    t, val = tr.process()
    for i in range(len(function)):
        print("\nFunctions triads processing:")
        triad = Triad(function[i][-1], function)
        function[i][-1] = triad.process(False)
    thread_mode = inquirer.prompt(thread_mode_questions)['mode']
    if thread_mode == MULTI_MODE:
        main_th = Thread('main', StackMachine(t, val, function))
        th_manager = ThreadManager([main_th])
        th_manager.run()
    else:
        stack_machine = StackMachine(t, val, function)
        print('\nStack machine\nValue table:')
        stack_machine.process()
