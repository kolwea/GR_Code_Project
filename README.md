# GR_Code_Project
## A record parsing RESTful API written in Kotlin.

### Objective
Create a command line program capable of parsing text files for user records and rendering the results in one of three ordering schemas. Then to expose the logic as an RESTful API accessible from http endpoints.

## Process
I began by researching the aspects I was most unfaimilar with, creating test in the kotlin and RESTful libraries. After some research  I decided to build the project using SpringBoot's web library for java/kotlin. I then began implementing the core logic of selecting a file, parsing it for record data, storing this data locally. Next I moved on to error checking and building test for the core logic. After achieving a acceptable coverage percentage I moved on to coding the three sorting methods. Again after this was completed I moved on to creating more tests. Finally I could begin work on to exposing the logic as a REST service and testing the endpoints using a Postman, an API development application. I closed out the project with some more unit testing.

## Images
### Coverage
![Coverage](https://raw.githubusercontent.com/kolwea/GR_Code_Project/master/Images/GR_Coverage.PNG "Code Coverage")
![Coverage](https://raw.githubusercontent.com/kolwea/GR_Code_Project/master/Images/GR_CoverageB.PNG "Code Coverage")

### Postman Endpoint Test
POST Records
![POST Records](https://raw.githubusercontent.com/kolwea/GR_Code_Project/master/Images/GR_Records.PNG "POST Records")
GET Gender
![GET Gender](https://raw.githubusercontent.com/kolwea/GR_Code_Project/master/Images/GR_Records_Gender.PNG "Code Coverage")
GET Birthdate
![GET Birthday](https://raw.githubusercontent.com/kolwea/GR_Code_Project/master/Images/GR_Records_Birthdate.PNG "Code Coverage")
GET Name
![GET Name](https://raw.githubusercontent.com/kolwea/GR_Code_Project/master/Images/GR_Records_Name.PNG "Code Coverage")


## References
- https://spring.io/guides/gs/spring-boot/
- https://phauer.com/2018/best-practices-unit-testing-kotlin/
- https://spin.atomicobject.com/2018/10/07/kotlin-unit-testing/
- https://kotlinlang.org/docs/tutorials/spring-boot-restful.html


