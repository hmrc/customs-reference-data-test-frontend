
# customs-reference-data-test-frontend

This is a service for proxying requests to the protected customs-reference-data service.

## Endpoints

### POST /reference-data-lists
* Download all domains from https://ec.europa.eu/taxation_customs/dds2/rd/rd_download_home.jsp?Lang=en
* Unzip the download, cd into it and run `gzip RD_NCTS-P5.xml`
* Attach this to the request body as a binary
* Response:
  * 200 - Data successfully posted and saved to `customs-reference-data`
  * 400 - Problem with the payload
  * 500 - Issue writing the data to Mongo

### POST /customs-office-lists
* Download the Customs Office List (COL) zip file from https://ec.europa.eu/taxation_customs/dds2/rd/rd_download_home.jsp?Lang=en
* Unzip the download, cd into it and run `gzip COL-Generic-YYYYMMDD.xml` where `YYYYMMDD` is today's date
* Attach this to the request body as a binary
* Response:
  * 200 - Data successfully posted and saved to `customs-reference-data`
  * 400 - Problem with the payload
  * 500 - Issue writing the data to Mongo

### GET /reference-data-list/:listName
* Retrieves data from customs-reference-data for a given list name (list names can be found at https://github.com/hmrc/customs-reference-data/tree/main/conf/resources)
* Response:
  * 200 - Data successfully retrieved
  * 404 - No data found for given list name
  * 500 - Something else went wrong

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
