<img width="693" height="661" alt="productsearch" src="https://github.com/user-attachments/assets/6ac8ba26-34dd-4b16-ad82-88b873036fb8" />


# ⚙ ProductSearch

A Spring Boot–based microservice that ingests product data via Kafka, persists to MongoDB, indexes in Elasticsearch, and provides REST APIs for product search.

- Spring Boot application persists data to Mongo db as source of truth
- Spring Boot app uses outbox pattern and saves message to Mongodb in 'Pending state'
- Spring Boot scheduler picks up message in 'Pending' and 'Failed' state and sends to Kafka for data sync
- Kafka-Connect for elastic search picks up message and syncs product metadata in Elasticsearch
- Spring boot uses Elasticsearch for fuzzy search and enhance user experience for product search


---

## 🔧 Technologies

- **Spring Boot** for REST APIs and backend logic  
- **Spring Kafka** for producing messages  
- **MongoDB** for primary persistence  
- **Elasticsearch** for full-text search indexing  
- **Kafka & Kafka Connect** (KRaft mode, no ZooKeeper)  
- **Docker / Docker Compose**

## 🚀 Quickstart Setup

You can launch the full stack with **Docker Compose** 

### ✅ Docker Compose

```bash
docker-compose up -d


