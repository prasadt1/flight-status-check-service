A Sample FlightStatus Service
-------------------
**Introduction**

This application is a proof of concept for the FlightStatus service which uses [FLIGHTSTATS]*(https://www.flightstats.com) a third party free flight status provider and can be used as a microservice.

**Build & Run**
1. Clone the app 
2. Build the project
3. Run the app without any profile [_for example_: `mvn spring-boot:run`]
4. The app will be running on port `:8080`
5. The flight status route is: `/flight-status`
6. Allowed parameters are: 
  - `flightPrefix`: the short code used by airlines [_for example:_ `EW` for `EuroWings`]
  - `flightNo`: the numeric flight number provided [_for example:_ `2462`]
  - `travelDate`: the date of travel [_date format_: `YYYY-MM-DD` - _for example:_ `2020-06-03`]
   
Note: To test the service, `flightPrefix`, `flightNo` and `travelDate` parameters are set by default values to EW, 2462 and 2020-06-03. So you can easily check the service by heating `http://localhost:8080/flight-status` which automatically returns the JSON of the flight `EW 2462` in `2020-06-03`. However, you can input your desired flight data to check its status.


**Business Events**

To show some business events, Elastiksearch and Kibana are deployed so the basic event data is gathered from the service and demonstrated on Kibana. To be able to check the process prlease follow the steps blow:
1. Run `docker-compose up -d` (Install and Run ELK Stack: `Elasticsearch`, `Logstash`, `Kibana`)
2. Run `mvn spring-boot:run`
3. Then hit the exposed route (`/flight-status?flightPrefix=EW&flightNo=2462&travelDate=2020-06-03`) as described above
4. Then pointing `kibana` at `http://localhost:5601` which will show the logs

**Screenshots**
![Flight Status by a Given flight Data](https://i.ibb.co/dDkHbSR/Flight-Status-Default-Values.png)
The FlightStatus Service check by deafult values: EW 2462 & 2020-06-03

![Flight Status by Default Values](https://i.ibb.co/BcGj60J/A-Desired-Flight-Status-Check.png)
The FlightStatus Service check by the given flight data: EW 9445 & 2020-06-03

![Flight Status that shows another flight service provider information](https://i.ibb.co/c3dssm4/LH-Example.png)
The FlightStatus Service check by another airline flight data: LH 498 & 2020-06-03

![A Simple Kibana Dashboard Showing FlightStatus Service Logs](https://i.ibb.co/tsmnRGR/2020-06-07-23.png)
A Simple Designed Dashboard that shows the situation of Search Results and the Number of Searches

   
**Licence**

This project is published under [Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt) licence.