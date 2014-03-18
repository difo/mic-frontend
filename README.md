mic-frontend
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
For a more detailed description of the CPIM library feature please refer to the following github repo:

https://github.com/deib-polimi/cpim-library

Here we will just give a compact "how to" in order to configure the architecture and the CPIM meta-files for the deploy of MiC  on each cloud provider supported by the CPIM.

LOCAL DEPLOYMENT ON GLASSFISH
First of all, since the CPIM library support the deploy interfacing with Glassfish AS we will speak about how to setting up the local architecture and how to fill the meta-files for MiC deployed on Glassfish. Since the Glassfish extension of the CPIM was though  to support a local deploy using the CPIM, we will focus on this case, but what we will say can be easily generalized.

SETTING UP THE ARCHITECTURE

-provide the java jdk 1.7 installed
-download and install Glassfish 4 AS
-download and install MySQL 5.6.15 server (or most recent version)
-using MySQL create two database, one for the Blob service and one for the Table service
-using the glassfish admin console create two JDBC connection toward the two databases created
-create a JDBC resource with a JDNI name for the connection reserved for the Table service database
-download and run memcached server 1.4.4 at the following link: 

http://s3.amazonaws.com/downloads.northscale.com/memcached-win64-1.4.4-14.zip

if you are running under Mac OSX refer to the instruction on how to get a memcached server at the following link:

http://www.journaldev.com/1/how-to-install-memcached-server-on-mac-oslinux

-configure Glassfish in order to work with the memcached server  following the instruction at the following link (skip the phase of adding a glass fish-web.xml file and of adding spymemcached library to Glassfish modules):

https://github.com/rickyepoderi/couchbase-manager/wiki/Installation


META-FILES CONFIGURATION

Refer to the "templates"->"Glassfish meta-files" folder to find specific templates for the deployment of MiC using Glassfish AS. You just need to provide the following additional information:

-<connection> and <blobconnection> fields in the configuraton.xml file: you have to fill with the connection strings given by Glassfish at the moment you create the two JDBC connection pool

-persistence.xml: you have to complete with account credential relative to the MySQL user  granted of privileges on the Table database, the url to the Table database on MySQL sever and  the JNDI name associated to the JDBC resource you have create on Glassfish

-<backend> field of the configuration.xml: you have to fill  with the local url to the backend. If you are using a single instance just specify the local url to the fronted itself. Otherwise if you are running with two instance specify the local url to the backend.

