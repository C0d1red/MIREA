import numpy as np


# Функция активации нейрона
def activate_func(x):
    return 1 / (1 + np.exp(-x))


# Производная функции активации нейрона
def derived_activate_func(x):
    return activate_func(x) * (1 - activate_func(x))


def print_separator():
    print('\n\n===========================================')


def matrix_to_array(matrix):
    return matrix.astype(int).flatten()


TRAIN_COUNT = 1000

# набор входных данных
x_matrix = np.array([[0, 0, 1],
                     [0, 1, 1],
                     [1, 0, 1],
                     [1, 1, 1]])

# выходные данные
y_matrix = np.array([[0],
                     [0],
                     [1],
                     [1]])

print("Обучение по правилу обратного распространения ошибки")
print_separator()
print("Учебные выходные данные: {}".format(matrix_to_array(y_matrix)))
print_separator()

# инициализируем веса случайным образом в пределах [-1; 2)
w_0 = 2 * np.random.random_sample((3, 1)) - 1

# Присваиваем 0-му слою матрицу входных данных
layer_0 = x_matrix

for train_num in range(TRAIN_COUNT):
    # прямое распространение
    s = np.dot(layer_0, w_0)
    layer_1 = activate_func(s)

    if train_num == 0:
        print("Выходные данные до тренировки: {}".format(matrix_to_array(np.around(layer_1))))

    # вычисляем ошибку
    layer_1_error = y_matrix - layer_1

    # обновим веса
    w_0 += np.dot(layer_0.T, layer_1_error * derived_activate_func(layer_1))

print("Выходные данные после тренировки: {}".format(matrix_to_array(np.around(layer_1))))
