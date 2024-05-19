import * as React from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { Container, Paper, Button } from '@mui/material';

const apiUrl = "https://hockeyschedule.onrender.com";

export function AddArena() {
    const paperStyle = {padding:"20px 20px", width:600, margin:"20px auto"}
    const [city,setCity] = React.useState()
    const [capacity,setCapacity] = React.useState()

    const handleClick = (e) => {
        e.preventDefault()
        const newArena = {city,capacity}
        console.log(newArena)
        fetch(`${apiUrl}/api/v1/arena/create`,{
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(newArena)
        }).then(()=>console.log("New arena Add")
    )}
    
        return (
    <Container>
        <Paper elevation={3} style={paperStyle}>
            <Box
    component="form"
    sx={{
    '& > :not(style)': { m: 1 },
    }}
    noValidate
    autoComplete="off"
    >
    <h1>Create new arena</h1>
    <TextField id="outlined-basic" label="City" variant="outlined"  
    value={city}
    onChange={(e)=>setCity(e.target.value)}
    />
    <TextField id="outlined-basic" label="Capacity" variant="outlined" 
    value={capacity}
    onChange={(e)=>setCapacity(e.target.value)}
    />
    <h1>{city} - {capacity}</h1>
    <Button variant="contained" onClick={handleClick}>
        SAVE
    </Button>
</Box>
</Paper>
    </Container>
  );
}

export function GetArenaById() {
    const paperStyle = { padding: "5px 20px", width: 600, margin: "20px auto" };
    const [arenaId, setArenaId] = React.useState("");
    const [arena, setArena] = React.useState(null);

    const findArenaById = (id) => {
        fetch((`${apiUrl}/api/v1/arena/` + id), {
            method: "GET"
        })
        .then(res => {
            if (res.ok) {
                return res.json();
            } else {
                throw new Error("Arena not found");
            }
        })
        .then(result => {
            setArena(result);
        })
        .catch(error => {
            setArena(null);
        });
    };

    return (
        <Paper elevation={3} style={paperStyle}>
            <h1>FIND ARENA BY ID:</h1>
            {arena && (
                <Paper elevation={6} style={{ margin: "50px", padding: "15px", textAlign: "left" }} key={arena.id}>
                    Id: {arena.id} <br />
                    City: {arena.city} <br />
                    Capacity: {arena.capacity} <br />
                </Paper>
            )}
            <TextField id="outlined-basic" label="Enter the arena ID" variant="outlined"
                value={arenaId}
                onChange={(e) => setArenaId(e.target.value)}
            />
            <br />
            <Button variant="contained" style={{ margin: "20px" }} onClick={() => findArenaById(arenaId)}>
                FIND
            </Button>
        </Paper>
    );
}

export function GetArenaByCapacity() {
    const paperStyle = { padding: "5px 20px", width: 600, margin: "20px auto" };
    const [minCapacity, setMinCapacity] = React.useState("");
    const [maxCapacity, setMaxCapacity] = React.useState("");
    const [arenas, setArenas] = React.useState([]);

    const findArenaByCapacity = (min, max) => {

        fetch(`${apiUrl}/api/v1/arena/search/capacity?moreThan=${min}&lessThan=${max}`, {
            method: "GET"
        })
        .then(res => {
            if (res.ok) {
                return res.json();
            } else {
                throw new Error("Arena not found");
            }
        })
        .then(result => {
            setArenas(result);
        })
        .catch(error => {
            setArenas([]);
        });
    };

    return (
        <Paper elevation={3} style={paperStyle}>
            <h1>FIND ARENA BY CAPACITY:</h1>
            { arenas !== null && (
            <div style={{marginBottom:"35px"}}>
                { arenas.map(arena=> (
                <Paper elevation={6} style={{ margin: "5px", padding: "15px", textAlign: "left" }} key={arena.id}>
                    Id: {arena.id} <br />
                    City: {arena.city} <br />
                    Capacity: {arena.capacity} <br />
                </Paper>
            ))}
            </div>
            )}
                <TextField id="outlined-basic" label="Enter min capacity" variant="outlined" style={{marginRight:'10px'}}
                value={minCapacity}
                onChange={(e) => setMinCapacity(e.target.value)}
            />
            <TextField id="outlined-basic" label="Enter max capacity" variant="outlined" style={{marginLeft:'10px'}}
                value={maxCapacity}
                onChange={(e) => setMaxCapacity(e.target.value)}
            />
            <br />
            <Button variant="contained" style={{ margin: "20px" }} onClick={() => findArenaByCapacity(minCapacity,maxCapacity)}>
                FIND
            </Button>
        </Paper>
    );
}

export function DeleteArena(){
    const paperStyle = {padding:"5px 20px", width:600, margin:"20px auto"}
    const [arenaId,setArenaId] = React.useState([])

    const handleDeleteArena = (id) => {
        fetch(`${apiUrl}/api/v1/arena/delete?id=${id}`, {
            method: "DELETE"
        })
        .then(response => {
            if (response.ok) {
                console.log("Arena deleted successfully");
            } else {
                console.error("Failed to delete arena");
            }
        })
        .catch(error => {
            console.error("Error deleting arena:", error);
        });
    };

    return (
        <Container>
            <Paper elevation={3} style={paperStyle}>
        <Box
          component="form"
          sx={{
            '& > :not(style)': { m: 1 },
          }}
          noValidate
          autoComplete="off"
          >
            <h1>DELETE ARENA BY ID</h1>
            <TextField id="outlined-basic" label="Enter the arena ID" variant="outlined" 
            value={arenaId}
            onChange={(e)=>setArenaId(e.target.value)}
            />
            <br />
            <Button variant="contained" onClick={() => handleDeleteArena(arenaId)}>
                Delete 
            </Button>
        </Box>
        </Paper>
        </Container>
      );
}

export function UpdateArena() {
    const paperStyle = { padding: "5px 20px", width: 600, margin: "20px auto" };
    const [arenaId, setArenaId] = React.useState("");
    const [city, setCity] = React.useState("");
    const [capacity, setCapacity] = React.useState("");
    const [arena, setArena] = React.useState(null);

    const updateArenaParams = (id,city, capacity) => {

        fetch(`${apiUrl}/api/v1/arena/change?arenaId=${id}&city=${city}&capacity=${capacity}`, {
            method: "PUT"
        })
        .then(res => {
            if (res.ok) {
                return res.json();
            } else {
                throw new Error("Arena not found");
            }
        })
        .then(result => {
            setArena(result);
        })
        .catch(error => {
            setArena(null);
        });
    };

    return (
        <Paper elevation={3} style={paperStyle}>
            <h1>UPDATE ARENA BY ID:</h1>
            {arena && (
                <Paper elevation={6} style={{ margin: "50px", padding: "15px", textAlign: "left" }} key={arena.id}>
                    Id: {arena.id} <br />
                    City: {arena.city} <br />
                    Capacity: {arena.capacity} <br />
                </Paper>
            )}
                <TextField id="outlined-basic" label="Enter arena ID(required)" variant="outlined" style={{marginBottom:'10px'}}
                value={arenaId}
                onChange={(e) => setArenaId(e.target.value)}
            />
            <br />
            <TextField id="outlined-basic" label="Enter new city" variant="outlined" style={{marginLeft:'10px'}}
                value={city}
                onChange={(e) => setCity(e.target.value)}
            />
            <TextField id="outlined-basic" label="Enter new capacity" variant="outlined" style={{marginLeft:'10px'}}
                value={capacity}
                onChange={(e) => setCapacity(e.target.value)}
            />
            <br />
            <Button variant="contained" style={{ margin: "20px" }} onClick={() => updateArenaParams(arenaId, city, capacity)}>
                FIND
            </Button>
        </Paper>
    );
}

export function GetAllArenas(){
    const paperStyle = {padding:"5px 20px", width:600, margin:"20px auto"}
    const [arenas,setArenas] = React.useState([])
    
    const refreshListArenas = () => {
        fetch(`${apiUrl}/api/v1/arena`, {
            method: "GET"
        })
        .then(res => res.json())
        .then(result => {
            setArenas(result);
        })
        .catch(error => {
            console.error('Error refreshing list of arenas:', error);
        });
    };

    React.useEffect(() => {
        refreshListArenas();
    }, []);

    return (
        <Paper elevation={3} style={paperStyle}>
            <h1>LIST ALL ARENAS:</h1>
      {arenas.map(arena=>(
        <Paper elevation={6} style={{margin:"10px", padding:"15px", textAlign:"left"}} key={arena.id}>
            Id:{arena.id}<br/>
            City:{arena.city}<br/>
            Capacity:{arena.capacity}<br/>
        </Paper>
      ))}
      <Button variant="outlined" color="primary" style={{margin:"20px"}} onClick={refreshListArenas}>
              Refresh
            </Button>
    </Paper>
    )
}


