# 💬 Real-Time Chat Application

A real-time chat application built with **Spring Boot**, utilizing **WebSocket** for real-time messaging and **Apache Kafka** for message streaming and scalability.

---

## 📌 Features

- ✅ Real-time chat using WebSocket
- ✅ Kafka for message broadcasting and persistence
- ✅ Multi-room chat support
- ✅ Basic user authentication
- ✅ REST API for user and room management

---

## 🚀 Tech Stack

- **Backend**: Spring Boot, Spring WebSocket, Spring Security
- **Messaging**: Apache Kafka
- **Build Tool**: Maven / Gradle
- **Database**: Mongodb
- **Frontend **: React 

---

## 🛠️ Getting Started

### Prerequisites

- Java 17+
- Maven or Gradle
- Apache Kafka 

### Clone the Repository

```bash
git clone https://github.com/your-username/chat-application.git
cd chat-application
````

### Start Kafka (Docker Example)

```bash
docker-compose up -d
```

### Run the Application

```bash
./mvnw spring-boot:run
```

### Test WebSocket

* Connect to `ws://localhost:8080/ws/chat`
* Send a JSON message:

  ```json
  {
    "sender": "oussama",
    "room": "general",
    "message": "Hello!"
  }
  ```

---

## 📈 Roadmap

### ✅ Phase 1: MVP

* [ ] WebSocket setup
* [ ] Kafka producer & consumer integration
* [ ] Basic user login and chatroom support
* [ ] REST endpoints for chatroom/user creation

### 🚧 Phase 2: Improvements

* [ ] Store chat messages in database
* [ ] Support private (1-to-1) chats
* [ ] Typing indicators
* [ ] Message read receipts

### 🧠 Phase 3: Advanced Features

* [ ] WebSocket authentication via JWT
* [ ] Kafka message partitioning by chat room
* [ ] Delivery guarantees (Kafka acknowledgments)
* [ ] Admin panel for moderation

### 🎨 Phase 4: Frontend UI 

* [ ] Real-time chat UI (React/Vue)
* [ ] User presence indicators
* [ ] Mobile responsiveness

---

## 📂 Project Structure

```
chatApplication/
├── docker/            
├── services/
    ├── gateway
    ├── discovery
    ├── auth-service    
    ├── chat-service 
├── UI/               

```

---

## 🤝 Contributing

Contributions are welcome! Feel free to fork the project and open a PR.



