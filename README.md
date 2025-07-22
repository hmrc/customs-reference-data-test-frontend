
# customs-reference-data-test-frontend

This is a service for proxying requests to the protected customs-reference-data service.

## Endpoints

### POST /convert/reference-data-lists
* Download the `NCTS-P5` domain from https://ec.europa.eu/taxation_customs/dds2/rd/rd_download_home.jsp?Lang=en
* Unzip the `RD_NCTS-P5.zip` file and gzip it with `gzip RD_NCTS-P5.xml`
* Attach this to the request body as a binary
* Response:
  * 200 - Data successfully converted to JSON

### POST /convert/reference-data-lists/:listName
* Download the `NCTS-P5` domain from https://ec.europa.eu/taxation_customs/dds2/rd/rd_download_home.jsp?Lang=en
* Unzip the `RD_NCTS-P5.zip` file and gzip it with `gzip RD_NCTS-P5.xml`
* Attach this to the request body as a binary
* Response:
  * 200 - Data for the given list name successfully converted to JSON

### POST /ingest/reference-data-lists
* Retrieve the JSON from the response of the above request, copy it to a file, gzip it and attach this to the request body as a binary
* Response:
  * 202 - Data successfully posted and saved to `customs-reference-data`
  * 400 - Problem with the payload
  * 500 - Issue writing the data to Mongo
  * 503 - This could be caused by a memory issue. Check the logs and revise jvm mx and ms values as appropriate.

### POST /convert/customs-office-lists
* Download the `Customs Office List (COL)` zip file from https://ec.europa.eu/taxation_customs/dds2/rd/rd_download_home.jsp?Lang=en
* Unzip the `COL-Generic-YYYYMMDD.zip` file and gzip it with `gzip COL-Generic-YYYYMMDD.xml` (where `YYYYMMDD` is today's date)
* Attach this to the request body as a binary
* Response:
  * 200 - Data successfully converted to JSON

### POST /convert/customs-office-lists/:listName
* Download the `Customs Office List (COL)` zip file from https://ec.europa.eu/taxation_customs/dds2/rd/rd_download_home.jsp?Lang=en
* Unzip the `COL-Generic-YYYYMMDD.zip` file and gzip it with `gzip COL-Generic-YYYYMMDD.xml` (where `YYYYMMDD` is today's date)
* Attach this to the request body as a binary
* Response:
  * 200 - Data for the given list name successfully converted to JSON

### POST /ingest/customs-office-lists
* Retrieve the JSON from the response of the above request, copy it to a file, gzip it and attach this to the request body as a binary
* Response:
  * 202 - Data successfully posted and saved to `customs-reference-data`
  * 400 - Problem with the payload
  * 500 - Issue writing the data to Mongo
  * 503 - This could be caused by a memory issue. Check the logs and revise jvm mx and ms values as appropriate.

### GET /reference-data-list/:listName
* Retrieves data from customs-reference-data for a given list name (list names can be found at https://github.com/hmrc/customs-reference-data/tree/main/conf/resources)
* Headers:
  * Accept:
    * `application/vnd.hmrc.1.0+json` to retrieve data from customs-reference-data database
    * `application/vnd.hmrc.2.0+json` to retrieve data from crdl-cache database
* Response:
  * 200 - Data successfully retrieved
  * 404 - No data found for given list name
  * 500 - Something else went wrong

### POST /compare/customs-office-lists
* Attach two files as a multipart form:
  * v1 - This is data that is retrieved when calling `GET /reference-data-list/:listName` with Accept header `application/vnd.hmrc.1.0+json`
  * v2 - This is data that is retrieved when calling `GET /reference-data-list/:listName` with Accept header `application/vnd.hmrc.2.0+json`
* Response:
  * 200 - Files have been successfully compared and shows the offices that have differences
  * 400 - Files did not have keys `v1` and `v2` or were improperly formatted

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
