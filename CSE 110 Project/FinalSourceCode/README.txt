WINOApp README

LOADING APPLICATION:
The source code for the app is in the FinalSourceCode folder within our TeamArea. To test the App you must import the code(both the WinoApp and FacebookSDK) into eclipse. If you don't have Eclipse refer to Download Eclipse. Make sure both the projects are saved to your workspace. The application can be launched on a phone in debug mode but this method is not fully tested. It is preferable that you test our application on an emulator with the following specifications:

Device: Nexus S
Target: Android 4.2.2 - API level 17
Back Camera: webcam()
SD Card: 8 GB

TEST:
Our tests are located in the TestPlan folder.

DOWNLOAD ECLIPSE:
Eclipse with the ADT Bundle can be downloaded at: http://developer.android.com/sdk/index.html
Please Follow instruction to setup the Android environment.

LAYERING AND MVC:
	MVC: In an Android project all xml files are considered the View since they handle the UI displays. The Activity files contain onClick(), onCreate(), and onClickListener() methods that represent the Controller part of MVC, since they listen for user changes to the UI and tell the Model to update the data. The Model portion is demonstrated by the methods called from the Controller methods. For instance, in the SurpriseActivity.java file, onCreate() calls displayRandomWine(). This method is the Model because it updates the data needed from the database and informs the View that it needs to be updated. 
	LAYERING: the inventory and the wishList is implement using the template pattern which creates a lot of layering. The pairing, login and registration each have action and database handler classes. Other features like the Facebook feature uses fragments to layer there code. Queries to the online data bases are handled by the UserFunctions class in the Library package. Queries to the SQLite database is handled by the DatabaseHandler class in the Library package. The UserFunction class sends POSTs to an online php API (smooth and Wino API), any calls to the online database is handled by the php files. Protection fromSQL injection is also implemented in the php file using Prepared Statements. Some feature were very static like the BAC and the tutorial, these simple feature do not exhibit extensive layering.

TECHNICAL SUPPORT:
Asad Rana		Project Manager		anrana@ucsd.edu		925-997-5045
Daniel Levin	 	Quality Assurance		djlevine@ucsd.edu		858-334-5661

