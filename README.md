# protobuf-broker-cli
explicar como he serializado el protobuf y que acepta mensajes JSON y poner un ejemplo  
poner que se envia a una cola y ponerla como parametro

explicar que los mensajes se quedan en la cola para ser consumidos por cualquier cliente  
hacer un consumidor psando otro parametro

# Running the Protobuf Sender with RabbitMQ

## Start RabbitMQ

Run RabbitMQ and its management console with the following command:

```bash
docker-compose up rabbitmq
```
This will start the RabbitMQ broker and the management console, accessible at:
http://localhost:15672/

Use the default credentials:  
Username: guest  
Password: guest  

## Build the application image
Before running the sender, make sure to build the Docker image for the protobuf-sender service:

```bash
docker-compose build protobuf-sender
```
##  Sending files to RabbitMQ
To send a file stored locally, mount the folder containing the file as a volume inside the container, and specify the file path with the --file parameter:  

Example for a file stored at C:/tmp/folder1/example1.json:
```bash
docker-compose run --rm -v "C:/tmp/folder1:/app/data" protobuf-sender --file=/app/data/example1.json
```
Example for a file stored at C:/tmp/folder2/example2.json:

```bash
docker-compose run --rm -v "C:/tmp/folder2:/app/data" protobuf-sender --file=/app/data/example2.json
```