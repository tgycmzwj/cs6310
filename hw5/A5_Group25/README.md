<h2>Project Summary</h2>

Group 25: Xi Chen, Zhipeng Liu, Weijun Sun, Chenyu Yan, Weijia Zhao

We implement <strong>3</strong> features in group project A5: 

<ol>
  <li>Robustness</li>
<li>Time Sensitive Coupons</li>
<li>Solar-powered Drone</li>
</ol>
<h2>Run Project</h2>

Language: Java

Framework: Spring Boot JPA

Database: MySQL

To run the program:

- `Right click on pom.xml - Add as Maven Project`

Instruction to run application in docker:

Step 1: stop any Server which is using port 3306

Step2: Prepare Docker images of DroneDelivery

- `docker build -t gatech/dronedelivery -f Dockerfile ./application`

Step3: Run the application in docker

- `docker-compose -p gatech -f docker-compose.yml up --build -d && docker attach app`

Check information saved in database:

- `Create a connection using MySQL workbench (Port: 3306, Username: root, password: omscs2023)`

Rebuild program in docker:

- `Stop and delete all containers and images, restart docker`
- `Build program in docker by following the instruction above`

To run app.jar:

- `Start MySQL server`
- `cd <app.jar directory>`
- `java -jar app.jar`
- `Create a local connection MySQL workbench (Port: 3306, Username: root, password: omscs2023) to check information saved in database`

<h2>Modifications and Improvement</h2>

<h5>Time and Distance System</h5>

<ol>
  <li>We require the user to input the virtual time at the end of each command, in the format of "command,[parameters],time" and the format of time is "yyyy-MM-dd-hh-mm". Eg: "display_stores,2024-03-01-02-30"
    <ul>
      <li>Drone charge speed and fly speed are expressed in the unit of minutes</li>
    </ul>
  </li>
  <li>To avoid potential confusion in the virtual time system and mimic the reality, we require that users' input time follows the chronological order, i.e. the associated time of a later command cannot must also be later than the associated time of an earlier command. Specifically, we check the timestamp of each command before the actual execution and any timestamp violating the chronological order will cause an error
  <ul>
    <li>We assume that the machine delay (not include the actual time needed to fulfill the requirement such as delivery) to store/process the command is 1 second, so no two consecutive commands can have exactly the same timestamp. (This is very natural as the system only supports a a single type of user and the user is not able to input two lines of commands at exactly the same time)</li>
  </ul>
  </li>
  <li>Location system in the project is implemented in a logical fashion (coordinate system). We keep track of the following locations
    <ul>
      <li>Store location: input parameters associated with command "make_store"</li>
      <li>Order destination: input parameters associated with command "start_order"</li>
      <li>Drone location: current location of the drone and the store location of the drone. Auto-computed variables used to compute time of each delivery.</li>
      <li>The distance between two location is calculated by [(x1-x2)^2+(y1-y2)^2]^0.5</li>
    </ul>
  </li>
  <li>To be consistent with the A3, we force all numerical attributes/variables to be integers, including the calculated distance (involves square root)</li>
</ol>




<h5>Robustness</h5>

<ol>
<li>Add extra validations to ensure the validity of user input</li>
<li>Shift program data from in memory storage to MySQL database, allowing
  <ul>
    <li>Data recovery without loss in the event of a system crash</li>
    <li>Continue execution without the need of repeatly input the same piece of information in the event of a system crash</li>
  </ul>
</li>
<li>"Atom" execution for command involving multiple updates of system data: system will only write the update to the database if the entire block of code related to a command is executed succesfully and any errors in the process will lead to fully rollback of the partial updates</li>
</ol>

<h5>Time Sensitive Coupon</h5>

In the previous phase, we proposed a method for distributing coupons but found it to be cumbersome and potentially confusing for users due to the number of parameters required for manual input and their interwoven relationships (Eg: we required both the “ex-ante” probability of getting a coupon in each round and the “ex-post” frequency of getting a coupon in HW4 submission and these two parameters are obviously closely related.) 

<ol>
  <li>We create a method called "make_coupon" which allows the creation and storage of a coupon in the database using three parameters: couponID, expiration date and discount value. In addition to these attributes, the Coupon class also includes a coupon state (valid or expired) and a list of customers who have been assigned the coupon.</li>
  <li>The "distribute_coupon" method which requires three parameters: coupon ID, base frequency, and high frequency. This method divides customers into two groups based on their rating values and assigning a different frequency to each group. Customers in the top 10 percentile and the bottom 10 percentile receive the high frequency to reward high-value customers and encourage new and inactive customers to continue making purchases. The rest of the customers receive the base frequency. The method randomly assigns coupons to customers based on their frequency assignment.</li>
  <li>At the time of "start_order", the system will first check if the customer has any coupons assigned and assign the first coupon ID to the order if there is one.</li>
  <li>At the time of "purchase_order", the system will check if the order was assigned a coupon and deducted the discount from the total order cost if the coupon is still valid.</li>
  <li>If the order has a coupon attached and it is not delivery before the coupon's expiration, the customer would be refunded 10% of the order cost.</li>
</ol>



<h5>Solar Drone</h5>

<ol>
  <li>Charge speed: We distinguish the charge speed at daytime/night and daytime is defined as [7:00AM,7:00PM)</li>
  <li>As users cannot input two commands with exactly the same time, we expand the "purchase_order" command, allowing the input of list of orders from the same store to enable the drone deliver multiple order in a single trip. "purchase_order,storename,order1,order2,...orderN,timestamp". The system will
    <ul>
      <li>Check the validity of each order: each order must be from the store given by the customer</li>
      <li>Group the orders according to the associated drones (as we allow the possibility that the orders submitted are attached to different drones)</li>
      <li>Each drone flys out and get the orders delivered, update the associated parameters along the way. Regardless of the number of orders delivered in a single round (starts when drone leaves the store and ends when the drone comes back), we increment the pilot experience by 1 and decrement the remaining trips before maintanence by 1</li>
    </ul>
  </li>
  <li>To get the order delivered, we assume
    <ul>
      <li>If the drone has multiple orders to deliver in a single trip, the we prioritize orders with coupons and orders for which the attached coupons are close to expire</li>
      <li>If the distance to the next destination is far away that the amount of fuel remained is not enough, the drone will travel until all fuel used up, then stop and get charged with sunlight and then resume again (with the possibility of multiple rounds of refueling if the distance is extremely far away)</li>
      <li>We track the available time of the drone along the way. If a new order is purchased when the drone is still out, the drone will first get all the previous delivery duties fulfilled and then fly back to the store to pick up the new orders. </li>
    </ul>
  </li>
</ol>








