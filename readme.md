# LogtoVis

LogtoVis scrapes log data, allowing data to be visualized in elasticsearch application.
Log files contain a wide variety of data about the software. 

In the project development and maintenance processes, analyzes and inferences are made over these logs. Searching within a single file can be easy. However, when we have 200 or 1000 log files, how should we process this data? How can we make the data meaningful?

LogtoVis is coded to eliminate this problem. It can quickly parse the data and transfer it to the elasticsearch application.

Built with Spring Boot.

## Building
Maven version: 3.8.3
Java version: 17

```
git clone https://github.com/OrionInnovationTRTech/elastovis.git
cd elastovis
mvn clean package
```

## Before Running

There are some issues that should be known before the project is run.

You should download the Elasticsearch and Kibana projects. Then you should run these applications via bin/elasticsearch.bat and bin/kibana.bat files.

You may need to make changes in the `savePm` method in DataLoader.java according to your log data. If you are going to make changes, it is recommended to search for the org.json dependency.

The user should write the folder he wants to scrape into the `DIR_PATH_FOLDER` variable in the `application.properties` file.

Kibana auth process was carried out within the project. If you want to cancel this, you can comment the ".withBasicAuth(username, password)" code line in the Config.java file. You can follow the [link](https://www.elastic.co/guide/en/kibana/current/using-kibana-with-security.html)  for the auth process.

## Kibana Transaction

Kibana is running [localhost:5601](localhost:5601) link. In order to visualize and analyze the data, the scraped data must first be recognized by kibana.For this, follow the steps below.

> Step 1: Open the menu tab on the left. Click on the Stack Management option under the Management menu at the bottom.

> Step 2: Click Index Patterns under the Kibana menu.

> Step 3: Click Create Index Pattern  

> Step 4:  The source list will be displayed on the right side of the screen. From here, we need to enter the name of our relevant resource in the "Name" field. If there is timestamp information in the data, select it from the "timestamp field" menu at the bottom.Confirm the creation with the Create Index Pattern option.

> Step 5: You can see your data in this area by logging in to the Dashboard link from the menu on the left. 