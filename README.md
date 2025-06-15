# 💬 Real-Time Chat Application

A real-time microservice chat application built with **Spring Boot**, utilizing **WebSocket** with the STOMP framework for real-time messaging.

---

## 📌 Features

- ✅ Real-time chat using WebSocket
- ✅ Contact and Conversation management
- ✅ User and WebSockets authentication using  jwt tokens
- ✅ REST API for users and  messages management

---

## 🚀 Tech Stack

- **Backend**: Spring Boot, Spring WebSocket, Spring Security,Spring Gateway,Spring Discovery
- **Messaging**: STOMP framework
- **Build Tool**: Maven / Gradle
- **Database**: Mongodb,Mysql
- **Frontend **: React ,Typecript

---

## 🛠️ Getting Started

### Prerequisites

- Java 17+
- Maven or Gradle
- Docker
### Clone the Repository

```bash
git clone https://github.com/your-username/chat-application.git
cd chat-application
````

### Build and run  Services

```bash
docker-compose up --build  -d
```



### See User Interface 

* Go to `http//localhost:8080`

---

## 📈 Roadmap

* [X] WebSocket setup
* [X] User creation and management
* [X] User login and Authentication using JWT
* [X] REST endpoints for chats/user creation
* [X] Store chat messages in the database
* [X] Support private (1-to-1) chats
* [X] Message read receipts
* [X] WebSocket authentication via JWT
* [X] Real-time chat UI (React/Vue)
* [ ] User presence indicators
* [ ] Mobile responsiveness

---

## 📂 Project Structure

```
chatApplication/
├── docker/            
├── services/ (backend services)
    ├── gateway
    ├── discovery
    ├── auth-service    
    ├── chat-service 
├── UI/     (user interface)          

```

---




