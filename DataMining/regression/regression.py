import csv
import numpy as np
import matplotlib.pyplot as plot

data_file = open("data.csv", "r")

# считывание данных
data_table = []
for line in csv.reader(data_file):
    data_table.append(list(map(int, line)))

# регрессоры
x = np.array(data_table[0])

# предикторы
y = np.array(data_table[1])

# общее число регрессеров
n = np.size(x)

# среднее значение входной переменной
x_mean = np.mean(x)

# среднее значение выходной переменной
y_mean = np.mean(y)

Sxy = np.sum(x * y) - n * x_mean * y_mean
Sxx = np.sum(x * x) - n * x_mean * x_mean

# зависимый коэффициент
b1 = Sxy / Sxx
print("Зависимый коэффициент: {:.2f}".format(b1))

# свободный коэффициент
b0 = y_mean - b1 * x_mean
print("Свободный коэффициент: {:.2f}\n".format(b0))

# уравнение регрессии
y_pred = b1 * x + b0
print("Уравнение регрессии: y = {:.2f}x + {:.2f}\n".format(b1, b0))

error = y - y_pred
se = np.sum(error ** 2)
error_mean = se / n
print("Среднеквадратическая ошибка {}".format(error_mean))

def_error = np.sqrt(error_mean)
print("Стандартная ошибка {}".format(def_error))

SSt = np.sum((y - y_mean) ** 2)
det_cf = 1 - (se / SSt)
print("Коэффициент детерминации {}".format(det_cf))

# Отображение результата на графике
plot.xlabel("x")
plot.ylabel("y")
plot.scatter(x, y, color="green")
plot.plot(x, y_pred, color="red")
plot.show()
