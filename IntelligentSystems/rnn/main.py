import numpy as np
import random
from rnn import RNN
from data import train_data, test_data

# Создание словаря
vocab = list(set([w for text in train_data.keys() for w in text.split(' ')]))
vocab_size = len(vocab)
print('Уникальных слов в тренеровочных данных: {}'.format(vocab_size))

# Присвоение каждому слову из словаря индекс
word_to_idx = {w: i for i, w in enumerate(vocab)}
idx_to_word = {i: w for i, w in enumerate(vocab)}


def create_inputs(text):
    inputs = []
    for w in text.split(' '):
        v = np.zeros((vocab_size, 1))
        v[word_to_idx[w]] = 1
        inputs.append(v)
    return inputs


def softmax(xs):
    return np.exp(xs) / sum(np.exp(xs))


# Инициализация RNN
rnn = RNN(vocab_size, 2)


# Возврат потери рекуррентной нейронной сети и точности для данных
def process_data(data, backprop=True):
    items = list(data.items())
    random.shuffle(items)

    loss = 0
    num_correct = 0

    # Цикл для каждого примера тренировки
    for x, y in items:
        inputs = create_inputs(x)
        target = int(y)

        # Прямое распределение
        out, _ = rnn.forward(inputs)
        probs = softmax(out)

        # Вычисление потери / точности
        loss -= np.log(probs[target])
        num_correct += int(np.argmax(probs) == target)

        if backprop:
            # Создание dL/dy
            d_L_d_y = probs
            d_L_d_y[target] -= 1

            # Обратное распределение
            rnn.backprop(d_L_d_y)

    return loss / len(data), num_correct / len(data)


# Цикл тренировки
for train_num in range(1000):
    train_loss, train_acc = process_data(train_data)

    # Выводим результат каждые 100
    if train_num % 100 == 99:
        print('--- Тренировка %d' % (train_num + 1))
        print('Тренировка:\tПотеря %.3f | Точность: %.3f' % (train_loss, train_acc))

        test_loss, test_acc = process_data(test_data, backprop=False)
        print('Тренировка:\tПотеря %.3f | Точность: %.3f' % (test_loss, test_acc))
