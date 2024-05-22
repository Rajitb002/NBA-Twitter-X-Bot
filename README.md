*Check out my bot at: "https://x.com/NbabotX"*

Automated bot that tweets statistics and information of NBA players on a daily basis. Done in Spring Boot, deployed through Render!

**I. BUILD**

  **Build using docker:**

 Go to /tweetbot to find Dockerfile, then run the following commands:

    docker build -t imageName:tagName
    docker run -p 10000:10000 -t imageName

  If you'd like to make your own bot, you need to:
  
    1) Make a Twitter developer account 
      1.1) Generate access and api keys & secret keys
    2) Generate authorization API for balldontlie
    3) Create an online postgres database (I used elephantSQL)

You can also make a jar by running "./mvnw package" in /tweetbot and execute using java -jar "jarName"

**II. Contribute**

  If you'd like to contribute:
  
    1) Please look at tweetbot.java and optimize the methods there
    2) On booting up the app, Spring boot will display some easy to fix issues

**III. Miscellaneous**

Some personal pointers:

    1) Remove dependencies and develop more in-house API methods in tweetbot
  
  

