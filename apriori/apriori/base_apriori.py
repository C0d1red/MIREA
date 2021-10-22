from apriori_python import apriori
import time


def run(item_set, min_sup, min_conf):
    start = time.time()
    freqItemSet, rules = apriori(item_set, minSup=min_sup, minConf=min_conf)
    end = time.time()
    execution_time = end - start
    return freqItemSet, rules, execution_time
