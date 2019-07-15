![SkillSearchEngine](https://github.com/bluesNbrews/SkillSearchEngine/blob/master/img/readmeTitleImg.png)

# Skill Search Engine - Fully Functional PDF Parser

Creator: Steven Williams

NOTE: This project is no longer supported. 

WARNING: According to CVE-2019-0228, Apache PDFBox 2.0.14 does not properly initialize the XML parser, which allows context-dependent attackers to conduct XML External Entity (XXE) attacks via a crafted XFDF.

Remediation: Upgrade org.apache.pdfbox:pdfbox to version 2.0.15 or later.

## Recommended Environment (to run as shown below)

* `Eclipse IDE for Java Developers version 2018-12 (4.10.0)`
* `PostgreSQL 11`
* `macOS`
* `Simple (text only) .pdf file`

## Setup

This project can be imported using Eclipse. File -> Import -> Git -> Clone URI.
Apache PDFBox 2.0.14 is used and the dependencies can be found here: https://pdfbox.apache.org/download.cgi. 
Include them in your classpath by right-clicking on the project name (package explorer) -> Build Path -> Configure Build Path -> Add External JARs. 

Once you have the project imported the project, Postgres.app is the easiest way to get going for the SQL server: https://postgresapp.com. Please note that this is specifically for macOS. Other OS versions can be found here: https://www.postgresql.org/download/. You will also need the PostgreSQL driver, navigate to the middle of the page or just click here: https://jdbc.postgresql.org/download.html. At the time of writing this, I am using the 'current version' of 42.2.5. Make sure you add this .jar to your classpath as outlined above. You should be all set! Once you compile and run, you can follow the tutorial below. 

If you have any feedback or issues, feel free to contact me at mwilliams10@gwu.edu.

## Tutorial

![TutorialImage1](https://github.com/bluesNbrews/SkillSearchEngine/blob/master/img/userGuide1.png)

1) Click the 'Select' button to search for your .pdf file.

![TutorialImage2](https://github.com/bluesNbrews/SkillSearchEngine/blob/master/img/userGuide2.png)

2) Select your .pdf file and click the 'Open' button. This must be done the first time only.

![TutorialImage3](https://github.com/bluesNbrews/SkillSearchEngine/blob/master/img/userGuide3.png)

3) Enter the skill(s) to search for. Make sure the words are separated with commas and have no spaces. 

![TutorialImage4](https://github.com/bluesNbrews/SkillSearchEngine/blob/master/img/userGuide4.png)

4) Click the 'Search' button. Note: At this point, the entire PDF is parsed and inserted into the database. If the skills are present in the database, they will be displayed on the GUI.

![TutorialImage5](https://github.com/bluesNbrews/SkillSearchEngine/blob/master/img/userGuide5.png)

5) You can search for additional skills the same way as described above without selecting the .pdf file again. 

![TutorialImage6](https://github.com/bluesNbrews/SkillSearchEngine/blob/master/img/userGuide6.png)

6) Again, if the skills are in the database, they will be displayed on the GUI. In this case, the skills 'Java' and 'Ruby' were not stored in the database. Note: There is a function (cleanUpDB) in the DataPersistence Class that will delete and drop the tables when finished. I currently am not using it in this version. 





