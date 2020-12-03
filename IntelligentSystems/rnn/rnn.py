import numpy as np
from numpy.random import randn


class RNN:

    def __init__(self, input_size, output_size, hidden_size=64):
        # Веса
        self.Whh = randn(hidden_size, hidden_size) / 1000
        self.Wxh = randn(hidden_size, input_size) / 1000
        self.Why = randn(output_size, hidden_size) / 1000

        # Смещения
        self.bh = np.zeros((hidden_size, 1))
        self.by = np.zeros((output_size, 1))

    def forward(self, inputs):
        h = np.zeros((self.Whh.shape[0], 1))

        self.last_inputs = inputs
        self.last_hs = {0: h}

        # Выполнение каждого шага в нейронной сети RNN
        for i, x in enumerate(inputs):
            h = np.tanh(self.Wxh @ x + self.Whh @ h + self.bh)
            self.last_hs[i + 1] = h

        y = self.Why @ h + self.by
        return y, h

    # Выполнение фазы обратного распространения RNN.
    def backprop(self, d_y, learn_rate=2e-2):
        n = len(self.last_inputs)

        # Подсчет dL/dWhy и dL/dby.
        d_Why = d_y @ self.last_hs[n].T
        d_by = d_y

        # Инициализация dL/dWhh, dL/dWxh, и dL/dbh к нулю.
        d_Whh = np.zeros(self.Whh.shape)
        d_Wxh = np.zeros(self.Wxh.shape)
        d_bh = np.zeros(self.bh.shape)

        # Вычисление dL/dh для последнего h.
        d_h = self.Why.T @ d_y

        # Обратное распространение во времени.
        for t in reversed(range(n)):
            # Среднее значение: dL/dh * (1 - h^2)
            temp = ((1 - self.last_hs[t + 1] ** 2) * d_h)

            # dL/db = dL/dh * (1 - h^2)
            d_bh += temp

            # dL/dWhh = dL/dh * (1 - h^2) * h_{t-1}
            d_Whh += temp @ self.last_hs[t].T

            # dL/dWxh = dL/dh * (1 - h^2) * x
            d_Wxh += temp @ self.last_inputs[t].T

            # Далее dL/dh = dL/dh * (1 - h^2) * Whh
            d_h = self.Whh @ temp

        # Отсекаем, чтобы предотвратить разрыв градиентов.
        for d in [d_Wxh, d_Whh, d_Why, d_bh, d_by]:
            np.clip(d, -1, 1, out=d)

        # Обновляем вес и смещение с использованием градиентного спуска.
        self.Whh -= learn_rate * d_Whh
        self.Wxh -= learn_rate * d_Wxh
        self.Why -= learn_rate * d_Why
        self.bh -= learn_rate * d_bh
        self.by -= learn_rate * d_by
