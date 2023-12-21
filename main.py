from fastapi import FastAPI, File, UploadFile, HTTPException
from fastapi.responses import JSONResponse
import tensorflow as tf
from keras.preprocessing import image
from keras.applications.inception_v3 import preprocess_input
from keras.models import Sequential 
from keras.layers import Dense
import json
import numpy as np
import uvicorn
import os
from google.cloud import storage, firestore
from io import BytesIO

app = FastAPI()

model_path = 'model.h5'

if not os.path.isfile(model_path):
    model = Sequential([
        Dense(64, activation='relu', input_shape=(10,)),
        Dense(1, activation='sigmoid')
    ])
    model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])
    
    model.save(model_path)
else:
    model = tf.keras.models.load_model(model_path)

os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = "serviceAccountKey.json"

def predict_image(img_array):
    img_array = preprocess_input(img_array)

    predictions = model.predict(img_array)
    prs = np.max(predictions, axis=1)
    print(prs)

    if(prs[0] < 0.5):
        return 'Not Food'
    
    pr = np.argmax(predictions, axis=1)

    print(pr)

    with open('data.json') as f:
        data = json.load(f)

    key_to_return = ['Food Name', 'Calories', 'Total Fat', 'Protein', 'Sugars'];
    result = data[int(pr[0])]

    output_data = {}
    for key in key_to_return:
        output_data[key] = result[key]


    print(output_data)

    return output_data



def upload_to_bucket(blob_name, file_content, bucket_name):
    storage_client = storage.Client()
    bucket = storage_client.get_bucket(bucket_name)
    blob = bucket.blob(blob_name)
    file_content.seek(0)  
    blob.upload_from_file(file_content, content_type='image/jpeg')

   
    image_url = blob.public_url

    return image_url

def store_data_in_firestore(data):
    db = firestore.Client()

    data['image_url'] = upload_to_bucket(data['blob_name'], data['file_content'], data['bucket_name'])

    existing_docs = db.collection('foods').where('food_name', '==', data['predictions']['Food Name']).stream()

    
    existing_docs_list = list(existing_docs)

    
    print(len(existing_docs_list))

    
    if len(existing_docs_list) == 0:
        document_data = {
            'image_url': data['image_url'],
            'food_name': data['predictions']['Food Name'],
            'calories': data['predictions']['Calories'],
            'total_fat': data['predictions']['Total Fat'],
            'protein': data['predictions']['Protein'],
            'sugars': data['predictions']['Sugars'],
        }
        
      
        db.collection('foods').add(document_data)
    
    else:
        
        return 'Document already exists'

def get_data_from_firestore(food_name):
    db = firestore.Client()

    docs = db.collection('foods').where('food_name', '==', food_name).stream()

    docs_list = list(docs)

    if len(docs_list) == 0:
        return 'No such document'

    else:
        doc = docs_list[0]

        return {'id': doc.id, **doc.to_dict()} 



@app.post("/predict")
async def predict(file: UploadFile = File(...)):
    try:
        bucket_name = 'food-recognizer-mlengine'
        blob_name = file.filename

        file_content = BytesIO(await file.read())

        upload_data = {
            'bucket_name': bucket_name,
            'blob_name': blob_name,
            'file_content': file_content
        }
        upload_to_bucket(**upload_data)

        file_content.seek(0)
      
        img = image.load_img(file_content, target_size=(150, 150))
        img_array = image.img_to_array(img)
        img_array = np.expand_dims(img_array, axis=0)

        predictions = predict_image(img_array)

        if predictions == 'Not Food':
            return JSONResponse(content={"predictions": predictions}, status_code=404)

    
        firestore_data = {
            'predictions': predictions,
            'bucket_name': bucket_name,
            'blob_name': blob_name,
            'file_content': file_content
        }
        store_data_in_firestore(firestore_data)

        predict_food_name = predictions['Food Name']
        
        data = get_data_from_firestore(predict_food_name)

        return JSONResponse(content={"predictions": data}, status_code=200)

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

if __name__ == '__main__':
    uvicorn.run(app, debug=True, host='0.0.0.0', port=14045)