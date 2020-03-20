## API Endpoints

### GET /stations
For getting all the stations matching a filtering criteria (if a filter exists). In absence of a filter, return all existing stations.

`http://localhost:8080/stations?zipCode={zip}&latitude={lat}&longitude={lon}&radius={radius}`

`zipCode`, `latitude`, `longitude` and `radius` are query parameters here. 

- If none of them is supplied, all stations are fetched.
- If `zipCode` is provided, all others are ignored if present and all stations in a particular zip code are returned.
- If `zipCode` parameter is not provided, presence of all the remaining parameters is tested. If all three parameters
`latitude`, `longitude`, `radius` are present, all stations within a given radius of a geolocation are returned.

`radius` can be an integer or a decimal value (e.g. 350.85), and the unit is `km`.
### POST /stations
Add a station to list of existing stations.
The POST body takes the form

    {
        "stationId": <user-defined-unique-id>,
        "zipCode": <zipcode>,
        "geolocation": {
            "latitude": <latitude-in-degrees>,
            "longitude": <longitude-in-degrees>
        }
    }

Here `stationId` is user defined and can take any string as value but it has to be unique. The uniqueness is checked while trying to persist a new station.

### GET /stations/{id}

`http://localhost:8080/stations/{id}`

Returns the station whose id matches the given id.