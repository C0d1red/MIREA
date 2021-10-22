import csv
from collections import defaultdict


def read(file_name):
    csv_data = [[]]

    with open(file_name, newline='', encoding='utf-8') as csvfile:
        data_dict = defaultdict(list)
        spamreader = csv.reader(csvfile, delimiter=',', quotechar='|')
        next(spamreader, None)
        for row in spamreader:
            if row[0] in data_dict:
                data_dict.get(row[0]).append(row[1])
            else:
                data_dict[row[0]] = [row[1]]

    for key in data_dict.keys():
        csv_data.append(data_dict.get(key))
    return csv_data
