import math
import random


def load_from_file(x_list, XS):
    input_file = open(FILENAME_INPUT, "r")

    for string in input_file:
        SS = string.split("\t")
        x_list.append(SS)
        XS.append(string)

    input_file.close()


def print_separator():
    print('\n\n===========================================')


def calculate_a(M, m):
    return 1 / (M - m)


def calculate_b(M, m):
    return -m / (M - m)


def normalize(x_list):
    # массивы для хранения коэффициентов для нормировки
    a_list = []
    b_list = []

    # нормировка исходных данных
    for i in range(len(x_list[0])):
        m_0 = m_1 = float(x_list[0][i])
        for j in range(len(x_list)):
            x_list[j][i] = float(x_list[j][i])
            if x_list[j][i] > m_0:
                m_0 = x_list[j][i]
            elif x_list[j][i] < m_1:
                m_1 = x_list[j][i]

        # коэффициенты нормирования
        a = calculate_a(m_0, m_1)
        b = calculate_b(m_0, m_1)

        # сохранить коэффициенты
        a_list.append(a)
        b_list.append(b)

        for j in range(len(x_list)):
            x_list[j][i] = a * x_list[j][i] + b


def generate_initialize_value(vector_length):
    z = random.random() * (2.0 / math.sqrt(vector_length))
    return 0.5 - (1 / math.sqrt(vector_length)) + z


def calculate_distance(w_vector, x_vector):
    r = 0
    for i in range(len(w_vector)):
        r = r + (w_vector[i] - x_vector[i]) * (w_vector[i] - x_vector[i])

    r = math.sqrt(r)
    return r


def find_near_vector(w_vector, x):
    wm = w_vector[0]
    r = calculate_distance(wm, x)
    i = 0
    i_n = i
    for w in w_vector:
        if calculate_distance(w, x) < r:
            r = calculate_distance(w, x)
            wm = w
            i_n = i
        i = i + 1

    return wm, i_n


print("Классификацию сетью Кохонена")
print_separator()

FILENAME_INPUT = "data.txt"

# Значения
x_list = []

# Символьные значения
XS = []

# Загрузка исходных данных из файла
load_from_file(x_list, XS)

# нормировать исходные данные
normalize(x_list)

# массив весов
w_list = []

# количество классов
CLASSES_NUMBERS = 2

# инициализировать веса
for i in range(CLASSES_NUMBERS):
    w_list.append(list())
    for j in range(len(x_list[0])):
        w_list[i].append(generate_initialize_value(len(x_list)) * 0.5)

la = 0.3  # коэффициент обучения
delta_la = 0.05  # уменьшение коэффициента обучения
STUDY_NUMBERS = 10  # количество обучений

# процесс обучения
while la >= 0:
    for study_number in range(STUDY_NUMBERS):
        for x in x_list:
            w = find_near_vector(w_list, x)[0]

            # корректировка весов
            for i in range(len(w)):
                w[i] = w[i] + la * (x[i] - w[i])

    # уменьшение коэффициента обучения
    la -= delta_la

# создать классы
class_list = []

for i in range(len(w_list)):
    class_list.append([])

# отнести исходные данные к своему классу
class_data_list = []
i = 0
for x in x_list:
    i_n = find_near_vector(w_list, x)[1]
    class_list[i_n].append(x)
    class_data_list.append([i_n, XS[i]])
    i = i + 1

# напечатать количество элементов в классах
i = 0
class_n = []
for d in class_list:
    print("Класс {} состоит из {} элементов".format(i, len(d)))
    class_n.append(len(d))
    i = i + 1


# распечатать по классам
output_file = []

for i in range(CLASSES_NUMBERS):
    file_name = str(i) + FILENAME_INPUT
    output_file.append(open(file_name, "w"))

for ds in class_data_list:
    output_file[ds[0]].write(ds[1])
    output_file[ds[0]].write("\n")

for i in range(CLASSES_NUMBERS):
    output_file[i].close()
