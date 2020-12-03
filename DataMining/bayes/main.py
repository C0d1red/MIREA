from sklearn.naive_bayes import GaussianNB
import numpy as np

# тренировочные входы и выходы
x = np.array([
    [-3, 7],
    [1, 5],
    [1, 2],
    [-2, 0],
    [2, 3],
    [-4, 0],
    [-1, 1],
    [1, 1],
    [-2, 2],
    [2, 7],
    [-4, 1],
    [-2, 7]])
Y = np.array([-1, 1, 1, -1, 1, -1, -1, 1, -1, 1, -1, -1])

# Создание гауссовского классификатора
model = GaussianNB()

# Тренировка модели
model.fit(x, Y)

# Прогнозирование
predicted = model.predict([
    [-1, 2],
    [3, 4]])

print(predicted)
