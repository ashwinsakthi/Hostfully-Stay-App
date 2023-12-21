
Please Use the below ways to access and hit the Rest Calls.

Get All Bookings

GET http://localhost:8080/v1/api/bookings

Get A Booking By ID

GET http://localhost:8080/v1/api/bookings/1000

Delete A Booking By ID

DELETE http://localhost:8080/v1/api/bookings/1

Count the Bookings

GET http://localhost:8080/v1/api/bookings/count

Create a Booking

POST http://localhost:8080/v1/api/bookings

Sample Update Below:

http://localhost:8080/v1/api/bookings/1001

PUT /v1/api/bookings/1001 HTTP/1.1
Content-Type: application/json
User-Agent: PostmanRuntime/7.36.0
Accept: */*
Postman-Token: 8a39f57e-4e26-41f5-87cb-11ad737f352f
Host: localhost:8080
Accept-Encoding: gzip, deflate, br
Connection: keep-alive
Content-Length: 138
 
{
"bookingId": 1001,
"dateFrom": "2023-12-24",
"dateTo": "2023-12-31",
"guestName": "guest",
"state": "BOOKED"
}
 
HTTP/1.1 200 OK
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 21 Dec 2023 22:20:10 GMT
Keep-Alive: timeout=60
Connection: keep-alive
 
{"bookingId":1001,"dateFrom":"2023-12-24","dateTo":"2023-12-31","guestName":"guest","state":"BOOKED"}

![image](https://github.com/ashwinsakthi/Hostfully-Stay-App/assets/2928596/3730d7bd-d11f-4a70-b5f2-b322e70ef308)

