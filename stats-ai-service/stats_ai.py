from flask import Flask, request, jsonify
from flask_cors import CORS
import numpy as np
from sklearn.linear_model import LinearRegression

app = Flask(__name__)
CORS(app)  # Autoriser les appels depuis d'autres origines

@app.route('/predict-registrations', methods=['POST'])
def predict():
    data = request.json['registrations']  # [{"date": "2024-05-01", "count": 12}, ...]
    X = np.array([i for i in range(len(data))]).reshape(-1, 1)
    y = np.array([d['count'] for d in data])
    model = LinearRegression().fit(X, y)
    prediction = model.predict([[len(data)]])[0]

    return jsonify({
        "predicted_tomorrow": int(prediction)
    })

if __name__ == '__main__':
    app.run(debug=True)
