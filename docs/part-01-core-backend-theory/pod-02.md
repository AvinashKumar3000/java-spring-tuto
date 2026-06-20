# LEARNING-MATERIAL

## HTTP

- Tim berner lee create HTTP ( Hyper Text transfer protocol ) around 1989–1991 while working at CERN.

The web originally needed 3 things:
- URL → locate the document         ( address           )
- HTTP → transfer the document      ( delivery method   )
- HTML → structure the document     ( package content   )

### what is HTTP ?
- HTTP is an application-layer protocol used for communication between:
    - Client (browser/mobile app/frontend)
    - Server (backend)

- client ---- ( request  )   ----> server
- client <--- ( response )   ----- server

### client send 
GET /users HTTP/1.1
Host: example.com

### Server responds:
HTTP/1.1 200 OK
Content-Type: application/json
Body: 
    {
        "name": "avi"
    }

- HTTP is stateless, text-based, request-response model

### HTTPS
- HTTPS = HTTP + SSL/TLS encryption
- TLS -> Transport Layer Security

client -> encrypted -> server

| Feature     | HTTP            | HTTPS           |
| ----------- | --------------- | --------------- |
| Security    | No encryption   | Encrypted       |
| Port        | 80              | 443             |
| Speed       | Slightly faster | Slight overhead |
| Data safety | Unsafe          | Safe            |

### HOW HTTP WORKS
01. User enters URL
02. DNS lookup          ( Domain converts to IP. )
03. TCP connection
04. HTTP request sent
05. server receive the request.
06. server send the response.
07. Client receive the response.

## What is idempotent?
- Calling same API multiple times gives same result.

### Short memory trick:

GET → Read
POST → Create
PUT → Replace
PATCH → Modify
DELETE → Remove








