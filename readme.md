# ParkingManager

generate a ParkingManager class that has the below data member:

3 ParkingLot 
 - The Plaza Park (9 parking capacity)
 - City Mall Garage (12 parking capacity)
 - Office Tower Parking (9 parking capacity)
3 ParkingBoy
 - Standard boy (with SequentiallyStrategy)
 - Smart boy (with MaxAvailableStrategy)
 - Super smart boy (with AvailableRateStrategy)

All ParkingBoy should have control over all 3 ParkingLot.

ParkingManager should have the below method:
- park(plateNumber: String, "STANDARD" | "SMART" | "SUPER_SMART") -> ParkingTicket
  - the method should use the correct ParkingBot to park the car with the given plateNumber and return the ticket
- fetch(plateNumber: String) -> List<ParkingLot>
  - the method should retrieve the car with the given plateNumber from the correct ParkingLot and return all the parkingLot after the fetch
- getParkingLot() -> List<ParkingLot>
  - the method should return the list of ParkingLot

# ParkingManagerTest

generate a ParkingManagerTest class that has the below test case:

- should_return_ticket_when_park_given_standard_parking
- should_return_ticket_when_park_given_smart_parking
- should_return_ticket_when_park_given_super_smart_parking
- should_throw_exception_when_park_given_invalid_parking
- should_return_parking_lots_when_getParkingLot
- should_return_parking_lots_when_fetch_given_plate_number
- should_throw_exception_when_fetch_given_invalid_plate_number

All test case should follow the "given ... when ... then ..." format. Use parameterized test if possible.

# ParkingManagerController

generate a ParkingManagerController class that has the below data member:

- ParkingManager

ParkingManagerController should have the below endpoints:

- GET /parkingLots
  - return the list of parking lots

- POST /park
  - request body: { "plateNumber": "string", "parkingType": "Standard" | "Smart" | "Super Smart" }
  - return the parking ticket and park the car

- POST /fetch
  - request body: { "plateNumber": "string" }
  - return the list of parking lots after fetching the car

Every endpoint should start with "/api/v1/parking-manager"

# ParkingManagerControllerTest

generate a ParkingManagerControllerTest class that has the below test case:

- should_return_parking_lots_when_getParkingLot
- should_return_ticket_when_park_given_standard_parking
- should_return_ticket_when_park_given_smart_parking
- should_return_ticket_when_park_given_super_smart_parking
- should_throw_exception_when_park_given_invalid_parking
- should_return_parking_lots_when_fetch_given_plate_number
- should_throw_exception_when_fetch_given_invalid_plate_number

All test case should follow the "given ... when ... then ..." format. Use parameterized test if possible.
All test case must use MockMvc to test the controller. Jackson should be used to parse the response body.

