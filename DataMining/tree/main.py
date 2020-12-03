import math
import pandas as pd
from functools import reduce


# Преобразование дерева в человекочитабельный вид
def tree_transform(tree, indent=""):
    s = indent + tree["name"] + str(frequency(tree["df"].iloc[:, -1]) if len(tree["edges"]) == 0 else "") + "\n"
    for e in tree["edges"]:
        s += tree_transform(e, indent + "  ")
        pass
    return s


# Дата сет
data = {
    "Жанр": ["комедия", "триллер", "триллер", "детектив", "драма", "ужасы", "ужасы", "боевик",
             "комедия", "драма", "триллер", "боевик", "комедия", "боевик"],
    "Оценка_критиков": ["высокая", "высокая", "высокая", "высокая", "средняя", "средняя", "средняя",
                        "высокая", "средняя", "средняя", "средняя", "высокая", "средняя", "высокая"],
    "Оценка_пользователей": ["высокая", "средняя", "средняя", "низкая", "низкая", "высокая", "средняя",
                             "средняя", "средняя", "высокая", "средняя", "средняя", "низкая", "низкая"],
    # Целевая переменная, показывающая результат, основывающийся на предыдущих данных
    "Понравился": ["✓", "×", "✓", "✓", "✓", "×", "✓", "×", "✓", "✓", "✓", "×", "✓", "×"]
}
data_formatted = pd.DataFrame(data)

# Находим частоту каждого из значений
frequency = lambda s: [k + ":" + str(v) for k, v in sorted(s.value_counts().items())]

# Структура данных дерева решений
decision_tree = {
    "name": "дерево решений " + data_formatted.columns[-1] + " " + str(frequency(data_formatted.iloc[:, -1])),
    "df": data_formatted,
    "edges": [],
}
tree = [decision_tree]

# Вычисление энтропии
entropy = lambda s: -reduce(lambda x, y: x + y, map(lambda x: (x / len(s)) * math.log2(x / len(s)), s.value_counts()))
while len(tree) != 0:
    n = tree.pop(0)
    df_n = n["df"]

    if 0 == entropy(df_n.iloc[:, -1]):
        continue
    attrs = {}
    for attr in df_n.columns[:-1]:
        attrs[attr] = {"entropy": 0, "dfs": [], "values": []}
        for value in sorted(set(df_n[attr])):
            # Фильтруем данные по значению атрибута
            df_m = df_n.query(attr + "=='" + value + "'")
            attrs[attr]["entropy"] += entropy(df_m.iloc[:, -1]) * df_m.shape[0] / df_n.shape[0]
            attrs[attr]["dfs"] += [df_m]
            attrs[attr]["values"] += [value]
            pass
        pass
    if len(attrs) == 0:
        continue
    attr = min(attrs, key=lambda x: attrs[x]["entropy"])
    for data, v in zip(attrs[attr]["dfs"], attrs[attr]["values"]):
        m = {"name": attr + "=" + v, "edges": [], "df": data.drop(columns=attr)}
        n["edges"].append(m)
        tree.append(m)
    pass

# Выводим дата сет
print(data_formatted, "\n-------------")

# Выводим древо в его символьном представлении.
print(tree_transform(decision_tree))
