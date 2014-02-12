mic-backend
===========

With the aim to use and test all the services from the CPIM library , it is developed a sample application , called Meeting In The Cloud ( MiC ), a small social network .

MiC Installation Manual
===========
The MiC project is available on github at the following address :

https://github.com/deib-polimi/mic-frontend

MiC exploits the possibilities offered by the CPIM to use a backend to lighten the computational load of the application. The backend is available on github at the following URL:

https://github.com/deib-polimi/mic-backend


Services of the CPIM used by MiC
===========

MiC uses the CPIM to access the following services:

¥ Blob Service: to save the profile picture of the user.

¥ NoSQL Service: in this storage solution the are all the topics, questions and associated answers given by all the users to it.

¥ SQL Service : the relational database stores the anagraphics users , and as well as all tuples needed to keep track of each user's Best Contacts .

¥ Memcache Service: Memcache service , allows the application to save some data during normal navigation of a user. Specifically, the data  about the Best Contacts of a logged User, to avoid having to always take them from the classics storage systems. Using this type of memory , the velocity of access to this information is greatly enhanced.

¥ Task Queue Service: used to record requests for computation of lists of similar . The Consumer for these services is represented by the class TaskQueueHandler, who has the task of read requests , invoke the task of computing associate and delete it from the queue.

The application also takes advantage of the opportunity provided by the CPIM to be deployed to a single Virtual Machine, or be divided into two tiers , splitting the frontend to the backend , consisting of the servlet that performs the calculation of similarity . These two parts will be loaded on two different instances , allowing you to move the computational load of calculating Best Contacts on a dedicated VM.
For the correct configuration of files containing the metadata needed to use the CPIM ( configuration.xml , persistence.xml and queue.xml ) and the setup of the architecture , please refer to the user manual of the CPIM library in the section about the deploy on Glassfish AS. You can find the CPIM library project at the following link on github:

https://github.com/deib-polimi/cpim-library