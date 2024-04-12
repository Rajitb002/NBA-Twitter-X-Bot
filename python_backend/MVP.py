import pandas as pd
import requests
from fastapi import FastAPI, APIRouter

app = FastAPI(title = "Tweetbot")

#Should read from CSV and send a POST request to my bot
def main():
      df = pd.read_csv('test.csv')
      postTweet(df)
#sends the post request

@app.post("postTweet")
def postTweet(df):
      request = requests.post(url="",
            #Top 5 MVP
            data= {df.loc[1,'Player']:df.loc[1,'Predictions']
                  ,df.loc[2,'Player']:df.loc[2,'Predictions']
                  ,df.loc[3,'Player']:df.loc[3,'Predictions']
                  ,df.loc[4,'Player']:df.loc[4,'Predictions']
                  ,df.loc[5,'Player']:df.loc[5,'Predictions']})

      






