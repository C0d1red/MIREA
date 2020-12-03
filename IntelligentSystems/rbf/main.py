import numpy as np
import matplotlib.pyplot as plt
from rbf import RBF

# создание тестовых данных
x = np.linspace(0, 10, 100)
y = np.sin(x)

# предсказание с помощью RBF-сети
model = RBF(hidden_shape=10, sigma=1.)
model.fit(x, y)
y_pred = model.predict(x)

# отображение на графие
plt.plot(x, y, 'b-', label='тест')
plt.plot(x, y_pred, 'r-', label='RBF')
plt.legend(loc='upper right')
plt.title('Интерполяция при использовании RBF-сети')
plt.show()
