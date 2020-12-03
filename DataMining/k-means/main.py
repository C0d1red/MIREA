import random

ITER_COUNT = 2

file = open("data.txt", "r")
items = []
centers = []
for s in file:
    items.append([int(val) for val in s.split(",")])

while len(centers) < ITER_COUNT:
    pos = random.randint(0, len(items) - 1)
    if items[pos] not in centers:
        centers.append(items[pos].copy())

distance = [[0 for i in range(len(items[0]))] for j in range(len(items))]
closest = [0] * len(items)
counts = [0] * len(centers)

E = 0
for iter in range(500):
    for i in range(len(distance)):
        for j in range(len(distance[0])):
            distance[i][j] = 0

    for i in range(len(items)):
        for ITER_COUNT in range(len(centers)):
            for j in range(len(items[0])):
                distance[i][ITER_COUNT] += (items[i][j] - centers[ITER_COUNT][j]) ** 2
            distance[i][ITER_COUNT] **= 0.5

        closest[i] = 0
        for ITER_COUNT in range(1, len(centers)):
            if distance[i][ITER_COUNT] < distance[i][closest[i]]:
                closest[i] = ITER_COUNT

    prev_E = E
    E = 0

    for i in range(len(distance)):
        E += distance[i][closest[i]] ** 2

    if abs(prev_E - E) < 0.5:
        break

    for i in range(len(centers)):
        for j in range(len(centers[0])):
            centers[i][j] = 0
        counts[i] = 0

    for i in range(len(items)):
        for j in range(len(items[0])):
            centers[closest[i]][j] += items[i][j]
        counts[closest[i]] += 1

    for i in range(len(centers)):
        for j in range(len(centers[0])):
            centers[i][j] /= counts[i]

print("\nЦентры кластеров:")

for i, center in enumerate(centers):
    print("Кластер-{}: ({:>.2f}; {:>.2f})".format(i, center[0], center[1]))

print("\nКоординаты объектов и их кластеры:")
print("X     |Y     |Кластер")
for i, item in enumerate(items):
    print("----------------------\n{:<6.2f}|{:<6.2f}|{:^8}".format(item[0], item[1], closest[i]))
