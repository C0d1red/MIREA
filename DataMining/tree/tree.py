# Структура данных Decision Tree
tree = {
    # name: Название этого нода (узла)
    "name": "decision tree " + df0.columns[-1] + " " + str(frequency(df0.iloc[:, -1])),
    # df: Данные, связанные с этим нодом (узлом)
    "df": df0,
    # edges: Список ребер (ветвей), выходящих из этого узла,
    # или пустой массив, если ниже нет листового узла.
    "edges": [],
}