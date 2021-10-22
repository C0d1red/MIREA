from util import csv_data_reader
from util import result_printer
from apriori import base_apriori, my_efficient_apriori, fpgrowth
from PyARMViz import PyARMViz


def run_lab(data, min_sup, min_conf):
    base_apriori_freq, base_apriori_rules, base_apriori_time = base_apriori.run(data, min_sup, min_conf)
    efficient_apriori_freq, efficient_apriori_rules, efficient_apriori_time = my_efficient_apriori.run(data, min_sup, min_conf)
    fpgrowth_freq, fpgrowth_rules, fpgrowth_time = fpgrowth.run(data, min_sup, min_conf)

    result_printer.print_result("Base Apriori", base_apriori_freq, base_apriori_rules, base_apriori_time)
    result_printer.print_result("Efficient Apriori", efficient_apriori_freq, efficient_apriori_rules, efficient_apriori_time)
    result_printer.print_result("Fpgrowth", fpgrowth_freq, fpgrowth_rules, fpgrowth_time)

    PyARMViz.generate_parallel_category_plot(efficient_apriori_rules)


external_data = csv_data_reader.read('resources/BreadBasket_DMS.csv')
my_data = csv_data_reader.read('resources/MyData.csv')

run_lab(external_data, 0.05, 0.05)
run_lab(my_data, 0.4, 0.4)
