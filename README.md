# central-unit-microservice
1. To access this microservice from the validation-unit-microservice you need a user and passwod. It uses basic authentication send by the validation-unit
   via Feign Client.
2. The main purpose of this microservice is to calculate the prices of parts in EUR, RON and USD. To do that it makes an axternal API call to a third party
   to get the rates for the currencies. 
   
![Untitled Diagram drawio(5)](https://user-images.githubusercontent.com/58910040/168304784-895d7f4f-3202-4e6d-b7db-f27bcba9749c.png)
