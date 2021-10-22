def line():
    print("-----------------------------------------------------------------------------------------------------------")


def print_result(alg_name, freq, rules, execution_time):
    line()
    print("Алгоритм '{}'\n"
          "Частые предметные наборы: {}\n"
          "Ассоциативные правила: {}\n"
          "Время выполнения алгоритмы: {}"
          .format(alg_name, freq, rules, execution_time))
    line()
    print("\n")
