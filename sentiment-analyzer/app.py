from flask import Flask, request, jsonify
from textblob import TextBlob
import logging

app = Flask(__name__)

# Configuration du logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

@app.route('/analyze', methods=['POST'])
def analyze():
    try:
        data = request.get_json()
        text = data.get('text', '')

        if not text:
            return jsonify({"error": "Aucun texte fourni"}), 400

        # Analyse de sentiment
        analysis = TextBlob(text)
        polarity = analysis.sentiment.polarity

        # Classification
        if polarity > 0.1:
            sentiment = "POSITIVE"
        elif polarity < -0.1:
            sentiment = "NEGATIVE"
        else:
            sentiment = "NEUTRAL"

        logger.info(f"Analyse terminée : {text[:50]}... → {sentiment}")

        return jsonify({
            "sentiment": sentiment,
            "polarity": polarity,
            "text_sample": text[:50] + "..." if len(text) > 50 else text
        })

    except Exception as e:
        logger.error(f"Erreur : {str(e)}")
        return jsonify({"error": "Erreur de traitement"}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)