# objectgenerator
A basic GUI app for creating Java objects

# Initial commit (25/05/2020)
Hello there folks. It has been a really long time since my last commit on GitHub (some 8 months, I believe). I have been working away on this object generator since then, but I have made a lot less progress than one would imagine. I attribute this largely to work eating into my free time heavily especially since February, when we have started a major project at my company. I also find that I am more stressed in general due to this, so I am not as productive as I would like to be.

What I have built is a very basic Java object (class) generator app. It allows users to add variables and method stubs to their objects. The resulting objects are stored as metadata and an actual Java file is also produced. The system includes a self-growing aspects because the objects you create also become available as data types.

Now that I am typing this out, I think that it sounds pretty good. There are some major flaws with in the app though. Some of these are listed below, although they are just a small subset of the problems I have with the implementation:
-Objects are not associated with a program or even a package. The app just spits them out, and users then need to integrate them into their apps manually
-The app does not handle anything beyond basic Java classes. No interfaces, enums, abstract classes, etc. can be produced with it
-You cannot specify a default value for your variables
-You cannot add parameters to your methods
-You cannot add collection variables (lists, arrays, maps, sets, etc.) to your objects
-As usual, the GUI has way too many pages users need to navigate through
-The app does not have a coherent error-handling strategy in place

The most pressing concern from my point of view is definitely the fact that the app largely lives in its own little world. Ideally I would like users to be able to create objects and start using them in their own apps immediately. Unfortunately, this will require some serious design changes.

The crux of the matter is that I will have to drop the custom metadata-based design, because it will not work effectively alongside a program and package-focused perspective. Simply put, I believe that it is easier to use the folders and files in a program directly than to attempt to replicate (and maintain) them and their structure using custom metadata. The latter option also sounds a little pointless to me, because I would be duplicating information that is readily available on the hard drive.

This is a little unfortunate, because my primary driver for getting into this project was the prospect of learning more about metadata-based solutions. On the other hand, this turn of events is extremely intriguing to me, and I believe that I can learn a lot by seeing this through. I am especially curious to find out whether I am able to create an effective solution for recommending packages and data types (objects in the packages) to users (I imagine a feature that is somewhat similar to what IntelliJ IDEA does, but I feel like I may end up in over my head). As such, I will be continuing work on the app in the foreseeable future. I cannot make any promises regarding further release dates due to the immense deadline pressures I am faced with in my work currently, however.
