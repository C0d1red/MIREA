from efficient_apriori import apriori
import time


def run(item_set, min_sup, min_conf):
    start = time.time()
    freqItemSet, rules = apriori(item_set, min_support=min_sup, min_confidence=min_conf)
    end = time.time()
    execution_time = end - start
    return freqItemSet, rules, execution_time
