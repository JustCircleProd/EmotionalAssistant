<h1 align="center">
  🎭 Android app to recognize a person's emotion and determine their emotional state 🏝
</h1>

<p align="center">
  <img src="https://github.com/user-attachments/assets/0a8e78e7-76c4-4bcf-a820-1d65460f0013"/>

</p>

## Features🔭

The functionality of this application includes identifying emotions, taking tests to determine emotional state, viewing recommendations, and working with the user's state history:

![Use Case](https://github.com/user-attachments/assets/c01aa4df-9977-44e3-8311-0795994ce7d9)


## Images🖼

![Регистрация](https://github.com/user-attachments/assets/5791da60-672c-4234-8d9a-721b10fbb227)
![Распознавание эмоции галерея](https://github.com/user-attachments/assets/4967ce84-bfe1-4124-9ff8-6446b14ded1b)
![Распознавание эмоции камера](https://github.com/user-attachments/assets/6749f2d8-4234-473c-ab1a-2919d15fd806)
![Дополнительная информация + рекомендации](https://github.com/user-attachments/assets/5146fe1b-5951-4147-b0b9-7ff23736fe5f)
![Оствашиеся эмоции](https://github.com/user-attachments/assets/a77827fc-5757-416b-bef6-545f301bc644)
![Тест](https://github.com/user-attachments/assets/284777ca-a99d-47cb-ba26-b0ca6443abb0)
![История, добавление эмоции и удаление](https://github.com/user-attachments/assets/a3e7193e-0c4f-41d8-8fe4-5ced5d0c857a)
![Изменение эмоции](https://github.com/user-attachments/assets/f8605cda-cad6-424f-99e6-7a519efef1be)


## Technologies💻

**Language: Kotlin🏝**  
*Minimum SDK level 24*

Tech stack:
- **Jetpack Compose**
- Navigation Component
- ViewModel
- Coroutines + Flow
- **Hilt**
- **Realm**
- **ML Kit**
- **TensorFlow Lite**
- Lottie
- Coil
- Architecture
  - MVVM Architecture
  - Repository Pattern
- Custom Views
  - [Calendar](https://github.com/kizitonwose/Calendar)


### Tech explanation

The design is 100% based on Compose.

The principle of "Single Activity" is used. The application is built using MVVM. It is developed using Сlean Architecture principles.

Realm is used to store users, their emotions and emotional states:

![физическая](https://github.com/user-attachments/assets/352b6e32-8d0f-4d3d-b62d-f829d8a1d45d)


ML Kit is used to recognize the face region of a person. TensorFlow Lite in turn is used to integrate its own machine learning model.

The [FER-2013](https://www.kaggle.com/datasets/msambare/fer2013/code?datasetId=786787&sortBy=voteCount) public dataset was used to train the human emotion recognition model.

The model is a convolutional neural network for classifying face images into one of seven emotion categories. The architecture consists of convolutional layers for visual feature extraction and full-connectivity layers for combining these features into high-level representations for classification.

![image](https://github.com/user-attachments/assets/39d31cac-4d7b-49a3-833d-aa68940ad2ff)


Training Results:

![image](https://github.com/user-attachments/assets/c2147444-6057-4197-a1ae-bad626524fcc)

  
## Copying is prohibited🚫

**Copying the source code and the application is prohibited.**
