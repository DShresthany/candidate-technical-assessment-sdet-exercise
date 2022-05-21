# Getting Started
Start by being able to either run the code or download and compile the code. 

Then review the model and API endpoint info.

Finally, complete the test assessment at the bottom. You may use whatever integration testing platform you see fit.

### To run the code
1. Download the repository into your local drive.
2. Ensure at least OpenJDK Java 8 is installed
3. Find the jar file in the root directory. You can run the jar file by entering
` java -jar sdet-assessment-1.0.0.jar`
4. This will run a localized tomcat server on port 8080. You can access
the site at http://localhost:8080/schools

Note: the file version number may have changed, match the file name in the root directory.

### To compile and run the code
1. Download the repository into your favorite IDE.
2. Ensure maven (3+) is installed (tested with Apache Maven 3.6.3)
3. Compile and execute the unit tests using  
` mvn clean verify `
4. Run the application server by running   
` mvn spring-boot:run`

### Object model
The project consists of a single School object. It has five fields:
* id - in integer id of the school
* name - the name of the school. Initial data is just one word.
* student count - the number of students in the school
* email address - an email address. Starting data is principal@<school name>.edu

The object when returning or submitting, should look like this:
```json
{
  "schoolId":9,
  "schoolName":"Grant",
  "studentCount":3000,
  "emailAddress":"principal@grant.edu"
}
```

### API description
There are four endpoints to test in School Controller

| URL                             | HTTP Method | Description                                                                                                                                                                                                 |
|---------------------------------|-------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| http://localhost:8080/schools   | GET         | Get all the schools in memory. This will return an array of schools objects.                                                                                                                                |
| http://localhost:8080/schools/4 | GET         | Get as specific school from memory (using the id). The schools in memory have ids 0 through 10. You may use any integer you wish. If the id is not found, the application will throw a NotFoundException.   |
| http://localhost:8080/schools/  | PUT         | Add a school to memory. It will add the new school with the id you give in the object. Submit the school object in the body of the PUT.                                                                     |
| http://localhost:8080/schools/4 | POST        | Update a specific school in memory. The schools in memory have ids 0 through 10. Submit the school object in the body of the POST.  If the id is not found, the application will throw a NotFoundException. |


### SDET Assessment
There is at least one issue with this code, please find it. Write an automated test that demonstrates the issue and be 
prepared to discuss your process. You may use whatever integration testing platform you see fit.
