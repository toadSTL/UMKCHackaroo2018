# UMKCHackaroo2018 #

## Finance AI ##

###NB:### Please do not consider this submission too thoroughly, I am merely submitting as a demonstration of my teams effort to produce an application which serves the use case.  Our results are negligible. 

### Authers ###

Greg Brown

DJ Yhun

Pravalhika Kampally

Muhammad Ali

## Instructions ##

Run mongo.js from the restAPI subdirectory of the directory into which you clone this repository.

Then open home.html in a browser and submit any date between 2008-08-08 and 2016-07-01 (YYYY-MM-DD).

The page should show if we predict based on the news stories in the Reddit New top 25 stories for that day that the DJIA will go up or down.

The results are 54.36% accurate (and are mostly "up", incidentally predicting that it will go "up" 100% of the time is 54.16% accurate over the same timespan)

The method we used to generate these resuts was performing sentiment analysis using Spark and the stanfordCoreNLP sentiment analysis.

Ideally we would have done more characterization of the headlines before performing sentiment analysis and would have trained a neural network using the sentiment data and the stock history (provided in the Reddit News dataset) to have a set of rules to predict the stock.  We the could have leveraged that neural network to make predictions based on current reddit headlines.  However we are not familiar with neural network training techniques, so we instead simply took running averages of the sentiments and determined what threshold value for that dataset would yeild correct predictions the highest percentage of the time when using the DJIA data provided.  No such method produced a substantially high percentage successful predictions.

