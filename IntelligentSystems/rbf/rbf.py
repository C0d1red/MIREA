import numpy as np


class RBF(object):

    def __init__(self, hidden_shape, sigma=1.0):
        self.hidden_shape = hidden_shape
        self.sigma = sigma
        self.centers = None
        self.weights = None

    def _kernel_function(self, center, data_point):
        return np.exp(-self.sigma*np.linalg.norm(center-data_point)**2)

    def _calculate_interpolation_matrix(self, x):
        G = np.zeros((len(x), self.hidden_shape))
        for data_point_arg, data_point in enumerate(x):
            for center_arg, center in enumerate(self.centers):
                G[data_point_arg, center_arg] = self._kernel_function(
                        center, data_point)
        return G

    def _select_centers(self, x):
        random_args = np.random.choice(len(x), self.hidden_shape)
        centers = x[random_args]
        return centers

    # Подгонка весов с помощью линейной регрессии
    def fit(self, x, y):
        self.centers = self._select_centers(x)
        G = self._calculate_interpolation_matrix(x)
        self.weights = np.dot(np.linalg.pinv(G), y)

    def predict(self, x):
        G = self._calculate_interpolation_matrix(x)
        predictions = np.dot(G, self.weights)
        return predictions
