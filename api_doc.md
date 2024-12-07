endpoint: /api/v1/parking-manager/parking-lots
method: GET
code: 200
response: 
[
    {
        id: int,
        name: String,
        capacity: int,
        tickets: 
        [
            {
                plateNumber: String,
                position: int,
                parkingLot: int
            },
            ...
        ]
    },
    ...
]

endpoint: /api/v1/parking-manager/park
method: POST
body: 
{
    plateNumber: string,
    parkingType: "Standard" | "Smart" | "Super Smart"
}
code: 200
response:
{
    plateNumber: string,
    position: int,
    parkingLot: int
}

endpoint: /api/v1/parking-manager/fetch
method: POST
body: 
{
    plateNumber: string
}
code: 200
response:
{
    plateNumber: String
}
            