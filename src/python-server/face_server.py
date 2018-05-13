import datetime
import json
import pickle

import face_recognition
import os
from PIL import Image
from flask import Flask, request

isSaveFaceEncoding = True
app = Flask(__name__)


@app.route('/')
def hello_world():
    return 'Hello World!'

@app.route('/faceNum', methods = ['POST'])
def faceNum():
    data = request.get_data()
    j_data = json.loads(data)
    print(data)
    image = face_recognition.load_image_file(j_data["path"])
    locations = face_recognition.face_locations(image)
    num = len(locations)
    return str(num)

@app.route('/faceCut',methods = ['POST'])
def face_cut():
    data = request.get_data()
    j_data = json.loads(data)
    print(j_data)
    image = face_recognition.load_image_file(j_data['srcPath'])
    face_locations = face_recognition.face_locations(image)
    if (len(face_locations) != 1):  #不是一张脸
        raise Exception("有且只有一张脸")
    face_location = face_locations[0]
    top,right, bottom, left = face_location
    face_image = image[top:bottom, left:right]
    pil_image = Image.fromarray(face_image)
    pil_image.save(j_data['dstPath'])
    return "success"
    #pil_image.show()


def face_encoding(facePath, isSaveFaceEncoding=isSaveFaceEncoding):
    t2 = datetime.datetime.now()
    arr = os.path.splitext(facePath)
    faceFile = arr[0] + ".face"    #硬盘保存的face文件
    #直接从硬盘读取
    if (isSaveFaceEncoding):
        if os.path.exists(faceFile):
            encoding = pickle.load(open(faceFile, 'rb'))
            return encoding
    #硬盘没有，则读取图片encoding，并保存到硬盘
    img = face_recognition.load_image_file(facePath)
    encoding = face_recognition.face_encodings(img)[0]
    if (isSaveFaceEncoding and facePath.find("/photo") != -1):
        fp = open(faceFile, 'wb')
        pickle.dump(encoding, fp)
        fp.close()
    t3 = datetime.datetime.now()
    #print("读取一张照片用时：" + str(t3 - t2))
    return encoding

@app.route('/faceDistance',methods = ['POST'])
def face_distance():
    data = request.get_data()
    j_data = json.loads(data)
    print(j_data)
    compareFaces = j_data['compareFaces']
    unknowFace = j_data['unknowFace']
    t1 = datetime.datetime.now()
    faceEncodings = []
    for path in compareFaces:
        encoding = face_encoding(path)
        t6 = datetime.datetime.now()
        faceEncodings.append(encoding)
    unknowEncoding = face_encoding(unknowFace)
    t2 = datetime.datetime.now()
    distance = face_recognition.face_distance(faceEncodings, unknowEncoding)
    t3 = datetime.datetime.now()
    distance = 1 - distance
    print(distance)
    print("读取处理阶段用时：" + str(t2-t1))
    print("识别阶段用时：" + str(t3 - t2))
    print("总用时：" + str(t3-t1))
    return json.dumps(distance.tolist())

if __name__ == '__main__':
   app.run()
   #  print("start")
   #  face_distance([r"C:\Users\Lenovo\Pictures\wo.jpg", r"C:\Users\Lenovo\Pictures\wei.jpg"], r"C:\Users\Lenovo\Pictures\wo2.jpg")

