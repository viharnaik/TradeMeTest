# README #

This repository was created by Vihar Naik for technical test purpose only.

### What Framework was used? ###

* These tests are created with Citrus Framework with gradle build tool. Request, Response payloads are logged along with detailed count.  

### How to run tests? ###

* Simple './gradlew test' will run all the tests and generate the test report in $ProjectFolder/test-output/citrus-reports

### Improvement ###

* Current tests are expecting hardcoded values which can be verified or fetched from db to ensure API returns correct value back.

* I probably need to invest more time to get better understanding of reporting in order to put the response value in the Citrus Report if required.

* There is option to integrated cucumber/ghekins scripts with Citrus if you'd prefer BDD style of tests. 

### Who do I talk to for queries? ###

* Vihar Naik
