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

-Blob Service: to save the profile picture of the user.

-NoSQL Service: with this storage solution MiC stores all the topics, the relative  questions choosen by a single user, and the given answers (a rate from 1 to 5).

-SQL Service : with this service MiC stores in the relational database the anagraphics users ,all tuples needed to keep track of each user's Best Contacts  (user similarity table), and the messages written by a user about a specific topic.

-Memcache Service: Memcache service , allows the application to save some data during normal navigation of a user. Specifically, the data  about the Best Contacts of a logged User, to avoid having to always take them from the classics storage systems. Using this type of memory , the velocity of access to this information is greatly enhanced.

-Task Queue Service: used to record requests for computation of lists of similar . The Consumer for these services is represented by the class TaskQueueHandler, who has the task of read requests , invoke the task of computing associate and delete it from the queue.

The application also takes advantage of the opportunity provided by the CPIM to be deployed to a single Virtual Machine, or be divided into two tiers , splitting the frontend to the backend , consisting of the servlet that performs the calculation of similarity . These two parts will be loaded on two different instances , allowing you to move the computational load of calculating Best Contacts on a dedicated VM.
For a more detailed description of the CPIM library feature and the generic structure of the meta-files used by the cpim library, please refer to the following github repo:

https://github.com/deib-polimi/cpim-library

Here we will just give a compact "how to" in order to configure the architecture and the CPIM meta-files for the deploy of MiC  on each cloud provider supported by the CPIM.
We will assume to use Eclipse Kepler to support the deploy.

Local Deployment on Glassfish
============

First of all, since the CPIM library support the deploy interfacing with Glassfish AS we will speak about how to setting up the local architecture and how to fill the meta-files for MiC deployed on Glassfish. Since the Glassfish extension of the CPIM was though  to support a local deploy using the CPIM, we will focus on this case, but what we will say can be easily generalized.

Setting Up the Environment
============


-provide the java jdk 1.7 installed
-download and install Glassfish 4 AS ( https://glassfish.java.net/download.html )
-download and install MySQL Community Server 5.6.15 (or most recent version) ( http://dev.mysql.com/downloads/mysql/ )
-using MySQL create two database, one for the Blob service and one for the Table service. 
-using the Glassfish admin console, create two JDBC connection toward the two databases created. 
-create a JDBC resource with a JDNI name for the connection reserved for the main database.
-download and run memcached server 1.4.4 at the following link: 

	http://s3.amazonaws.com/downloads.northscale.com/memcached-win64-1.4.4-14.zip

	if you are running under Mac OSX refer to the instruction on how to get a memcached server at the following link:

	http://www.journaldev.com/1/how-to-install-memcached-server-on-mac-oslinux

-configure Glassfish in order to work with the memcached server  following the instruction at the following link (skip the phase of adding a glass fish-web.xml file and of adding spymemcached library to Glassfish modules since it is already imported by the CPIM):

	https://github.com/rickyepoderi/couchbase-manager/wiki/Installation



META-FILES Configuration
============


Refer to the "templates/Glassfish meta-files" folder to find specific templates for the deployment of MiC using Glassfish AS. You just need to provide the following additional information:

-<connection> and <blobconnection> fields in the configuraton.xml file: you have to fill with the name of the two corrispondent database you have create on MySQL;

-persistence.xml: you have to complete with account credential relative to the MySQL user granted of privileges on the databases, the url to the relational database on MySQL sever and  the JNDI name associated to the JDBC resource you have created on Glassfish for the main database;

-<backend> field of the configuration.xml:  if you are using a single instance just specify the local url to the fronted itself. Otherwise if you are running with two instance specify the local url to the backend.

How to Deploy
============
Once your enviroment is configured up and running and the meta-files are configured from eclipse just double click on the application project, "Run as->Run on server" specifying the running Glassifish 4 instance and MiC will be locally deployed.

Deployment on Windows Azure
============

Setting Up the Environment
============

	-Provide the java jdk 1.7 installed
	-Donwload and install the Windows Azure SDK http://www.windowsazure.com/it-it/downloads/
	-If you don'thave already one, create a new Microsoft account.
	-Acquire a Windows Azure subscription http://www.windowsazure.com/it-it/pricing/purchase-options/
	-Install the Windows Azure Eclipse plugin using the instruction at the following link http://msdn.microsoft.com/en-us/library/windowsazure/hh690946.aspx
	-From the Azure Portal login with your Microsoft account credential. You should be prompt the Management Portal.

At this point from the management portal you have to configure all the cloud service you need.
	
-From the Management Portal create a new Cloud Service: New->Cloud Service->Quick Create
	
	-In URL, enter a subdomain name to use in the public URL for accessing your cloud service in production deployments. 
	-In Region/Affinity Group, select the geographic region or affinity group to deploy the cloud service to.
	-Click Create Cloud Service and the Cloud Services area opens, with the new cloud service displayed. When the status changes to Created, cloud service creation has completed successfully.
	
-From the Management Portal create a new Storage account used for store data in the Queue, Blob and Table services: New->Data Service->Storage->Quick Create
	
	-In URL, enter a subdomain name to use in the storage account URL. 
	-In Region/Affinity Group, select a region or affinity group for the storage (better if it is the same of the Cloud Service)
	-In Replication, select the level of replication that you desire for your storage account. By default, replication is set to Geo-Redundant. 
	-Click Create Storage Account.

For more details about the Storage Account configuraton refer to teh following link: http://www.windowsazure.com/en-us/documentation/articles/storage-create-storage-account/

-From the Management Portal create a new SQL Database: New->SQL Database->Custom Create. The Database Settings page appears when you click Custom Create. In this page, you provide basic information that creates an empty database on the server. 

	
	-Choose a name for your Database and use the default settings for edition, max size, and collation.
	-If you dont'have already one to use, choose New SQL Database Server.Selecting a new server adds a second page that we'll use to set the administrator account and region.
	-Fill all the fields with the required information and then click the checkmark at the bottom of the page when you are finished.

Notice that you did not specify a server name. Because the SQL Database server must be accessible worldwide, SQL Database configures the appropriate DNS entries when the server is created. The generated name ensures that there are no name collisions with other DNS entries. You cannot change the name of your SQL Database server.
For more detailed information about this step and for the SQL Server firewall configuration refer to the following link: http://www.windowsazure.com/en-us/documentation/articles/sql-database-get-started/
Note: The SQL Database service is only available with TCP port 1433 used by the TDS protocol, so make sure that the firewall on your network and local computer allows outgoing TCP communication on port 1433. 

-From the Management Portal create a new Memcached Server: New->Data Service->Cache->Quick Create

	-Specify an endpoint for you Memcached Server and a region (it should be the same of the other services for better performance)
	-Just leave the default settings for the Cache Offering and the Cache Memmory (they are enough for our purpose)
	 
	 
	 
META-FILES Configuration
============
Refer to the "templates/Azure meta-files" folder to find specific templates for the deployment of MiC using Windows Azure. You just need to provide some information as specified in the templates. In particular:

-configuration.xml: SQL service and Memcached service need to be configured;

	-go on the Management Portal;
	-on the left scroll panel click on "SQL DATABASE";
	-find your listed SQL database and click on it;
	-click on "View SQL Database connection strings for ADO .Net, ODBC, PHP, and JDBC";
	-copy and pasta the JDBC conncection string in the configuration.xml where specified in the template;
	-specify the address to the memcache server you have created. In order to do this you just need to complete the schema "localhost_<WorkerRoleName>" as specified in the template;

-persistence.xml: just provide the credentials for the storage account as specified in the templates

	-go on the Management Portal;
	-on the left scroll panel click on "STORAGE";
	-find your listed Storage Account and click on it;
	-at the bottom of the page click on "MANAGE ACCESS KEY";
	-copy and paste the STORAGE ACCOUNT NAME and the PRIMARY ACCESS KEY in the persistence.xml where specified in the template;
	
	
	
-queue.xml: this file have no cloud provider specific configuration;


Furthermore deployment on Windows Azure required two more meta-files specific for this deployment solution. These are the servicedefinition.csdef 
and the serviceconfiguration.cscfg. Also for these two additional meta-files we provide a templates in the relative folder missing of the following information:

	-servicedefinition.csdef: specify the endpoint of the Memcached Server you have create on the portal as you specified at the creation time;
	-serviceconfiguration.cscfg: specify the credentials for the Storage Account as you did for the persistence.xml;
	
At this point the application should be correctly configured for the deploy.

How to Deploy
============

Once your environment is configured up and running and the meta-files are correctly configured you need to create a new Windows Azure Deployment Project. 
At this aim, from eclipse just double click on the application project and "Window Azure->Package for Windows Azure" and the Windows Azure deployment Project interface will appear.
Since Windows Azure doesn't have the runtime environment to support the execution of java code, we need to upload to the cloud
also a JDK and a Java Application Server using improperly a IaaS as a PaaS platform. So you need to specify

	-a name for you Windows Azure Deployment Project and click next;
	-a pointer to your local Glassfish 4 installation (or to another supported Application Server) and go to the next tab;
	-a pointer to your JDK (remember that you need JDK 1.7 in order to deploy MiC) and go to the next tab;
	-the war file of the application you want to deploy (mic-frontend.war, it should already been specified) and click next;
	-some additional and optional feature to enable; since MiC use the memecached service just enable the "Caching";
	-click Finish;

At this point eclipse and the Windows Azure plugin for Eclipse will create a new Windows Azure Deployment Project as you can see in the Eclipse Project Explorer.
Finally you just need to double click on the new created project and "Windows Azure->Deploy to Windows Azure Cloud". The deployment process will start. As we explain in the next section, actually there are some problems in the deployment process due to the eclipse plugin for Windows Azure.

You can find a more detailed guide for this specific phase at the following link:
http://msdn.microsoft.com/en-us/library/hh690944.aspx

Known Issues and the Actual Status
============
Actually MiC use two Entity (Topic and UserRatings) managed with the JPA implementation jpa4azure. In this implementation seems to be a mismatch with respect to the JPA specification. Indeed in order to persist the two complex attribute List<String> topicQuestions of the Topic entity and List<String> ratings of UserRatings we have to annotate them with @Embedded annotation, while following the specification it should be used the @ElementCollection annotation. This mismatch cause just an error reported by Eclipse in case of deployment on Azure, but the error appear also at run time in case of deployment on other platform tha use a JPA implementation faithful to the specification. FAt the moment the problem is still unsolved and we just have the @Embedded annotion as a comment. So in order to deploy on Azure we have to uncomment the annotation.

The last attempt to deploy on Azure has detected a problem that we found to be already known. The deployment hangs at 40% and then fail after a long wait. Searching on the web it seems that the problem is common and attributed to a bug in the plugin for eclipse for windows azure. 
Further research will be carried out to solve the problem and deploy MiC on Azure.


