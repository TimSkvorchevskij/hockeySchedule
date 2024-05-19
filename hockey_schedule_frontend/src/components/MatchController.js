import * as React from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { Container, Paper, Button} from '@mui/material';
import { BasicDatePicker, BasicTimePicker }  from './dataTime';
import moment from 'moment';

const apiUrl = "https://hockeyschedule.onrender.com";

export function AddMatch() {
    const paperStyle = {padding:"20px 20px", width:600, margin:"20px auto"}
    const [selectedDate, setSelectedDate] = React.useState(null); // Состояние для выбранной даты
    const [selectedTime, setSelectedTime] = React.useState(null); // Состояние для выбранного времени
    const [guestTeamId, setGuestTeamId] = React.useState(null); // Состояние для выбранного времени
    const [homeTeamId, setHomeTeamId] = React.useState(null); // Состояние для выбранного времени
    const [homeTeam, setHomeTeam] = React.useState(null); // Состояние для выбранного времени
    const [guestTeam, setGuestTeam] = React.useState(null); // Состояние для выбранного времени
    
    const [arenaId, setArenaId] = React.useState(null); // Состояние для выбранного времени
    const [arena, setArena] = React.useState(null); // Состояние для выбранного времени

  
    const handleDateChange = (newDate) => {
      setSelectedDate(newDate);
    };
    const handleTimeChange = (newTime) => {
        setSelectedTime(newTime);
    };

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

    const findTeamById = (id, teamType) => {
        fetch(`${apiUrl}/api/v1/team/${id}`, {
            method: "GET"
        })
        .then(res => {
            if (res.ok) {
                return res.json();
            } else {
                throw new Error("Team not found");
            }
        })
        .then(result => {
            if (teamType === 'home') {
                setHomeTeam(result);
            }
            else setGuestTeam(result);
        })
        .catch(error => {
            if (teamType === 'home') {
            setHomeTeam(null);
            }
            else setGuestTeam(null);
        });
    };

    const setTeamList = () => {
        console.log (homeTeam, guestTeam)
        if (isNaN(parseInt(homeTeamId)) && isNaN(parseInt(guestTeamId))) {
            console.log ("Return null")
            return null;
        } else if (isNaN(parseInt(guestTeamId))) {
            console.log ("homeId")
            return [{ id: parseInt(homeTeamId) }];
        } else if (isNaN(parseInt(homeTeamId))) {
            console.log ("guestID")
            return [{ id: parseInt(guestTeamId) }];
        } else {
            console.log ("Return homaAndTeam")
            return [{ id: parseInt(homeTeamId) }, { id: parseInt(guestTeamId) }];
        }
    }
    

    const saveMatch = (e) => {
        e.preventDefault();
        const newMatch = {
            dateTime: selectedDate && selectedTime ? selectedDate.format('YYYY-MM-DD') + 'T' + selectedTime.format('HH:mm') : null, // Объединяем дату и время
            teamList: setTeamList(),
            arena: arenaId ? { id: parseInt(arenaId) } : null

        };
        
        console.log('New match:', newMatch);
    
        fetch(`${apiUrl}/api/v1/match/create`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(newMatch)
        })
        .then(response => response.json())
        .then(data => {
            console.log('Response from server:', data);
            console.log('New match added successfully');
        })
        .catch(error => {
            console.error('Error while adding new match:', error);
        });
    };

    return (
        <Container>
            <Paper elevation={3} style={paperStyle}>
                <h1>ADD MATCH</h1>
                <Box
                    component="form"
                    sx={{
                        display: 'flex',
                        flexDirection: 'column',
                        '& > :not(style)': { m: 1 },
                    }}
                    noValidate
                    autoComplete="off"
                >
                    <h3>Date</h3>
                    <div style={{ display: 'flex', flexDirection: 'row', alignItems: 'center' }}>
                        <div style={{ marginRight: '10px', marginLeft: '50px' }}>
                            <BasicDatePicker onDateChange={handleDateChange} />
                        </div>
                        <div style={{ marginRight: '50px', marginLeft: '10px' }}>
                            <BasicTimePicker onTimeChange={handleTimeChange} />
                        </div>
                    </div>
                    <h3>Playing teams</h3>
                    <div style={{ marginTop: '10px' }}>
                        <TextField id="outlined-basic" label="Home team ID" variant="outlined" style={{ width: "150px", marginRight: "10px" }}  
                            value={homeTeamId}
                            onChange={(e)=>{
                                setHomeTeamId(e.target.value)
                                findTeamById(e.target.value, 'home')
                            }}
                        />
                        <TextField id="outlined-basic" label="Guest team ID" variant="outlined" style={{ width: "150px", marginLeft: "10px" }}  
                            value={guestTeamId}
                            onChange={(e)=>{
                                setGuestTeamId(e.target.value)
                                findTeamById(e.target.value, 'guest')
                            }}
                        />
                    </div>
                    <h3>Arena</h3>
                    <div style={{ marginTop: '10px' }}>
                        <TextField id="outlined-basic" label="Arena ID" variant="outlined" style={{ width: "150px" }}  
                            value={arenaId}
                            onChange={(e)=>{
                                setArenaId(e.target.value);
                                findArenaById(e.target.value)
                            }}
                        />
                    </div>
                </Box>
                <div>
                    <h2>Selected parameters:</h2>
                    <p style={{ margin: '0px' }}><b>Date:</b> {selectedDate ? selectedDate.format('DD-MM-YYYY') : ''}</p>
                    <p style={{ margin: '0px' }}><b>Time:</b> {selectedTime ? selectedTime.format('HH:mm') : ''}</p>
                    <p style={{ margin: '0px' }}><b>Home team:</b><br />
                    {homeTeam && (
                        <div>
                        Id: {homeTeam.id} <br />
                        Name: {homeTeam.teamName} <br />
                        </div>
                    )}
                    </p>
                    <p style={{ margin: '0px' }}><b>Guest team:</b><br />
                    {guestTeam && (
                        <div>
                        Id: {guestTeam.id} <br />
                        Name: {guestTeam.teamName} <br />
                        </div>
                    )}
                    </p>
                    <p style={{ margin: '0px' }}><b>Arena:</b><br />
                    {arena && (
                        <div>
                        Id: {arena.id} <br />
                        City: {arena.city} <br />
                        Capacity: {arena.capacity} <br />
                        </div>
                    )}
                    </p>
                </div>
                <Button variant="contained" style={{ margin: "20px" }} onClick={saveMatch}>
                SAVE
            </Button>
            </Paper>
        </Container>
    );
}

export function GetMatchById() {
    const paperStyle = { padding: "5px 20px", width: 600, margin: "20px auto" };
    const [matchId, setMatchId] = React.useState("");
    const [match, setMatch] = React.useState(null);

    const findMatchById = (id) => {
        fetch((`${apiUrl}/api/v1/match/` + id), {
            method: "GET"
        })
        .then(res => {
            if (res.ok) {
                return res.json();
            } else {
                throw new Error("Match not found");
            }
        })
        .then(result => {
            setMatch(result);
        })
        .catch(error => {
            setMatch(null);
        });
    };
    const showTeamList = () => {
        return(
            <div>
                {match.teamDTOList.map(team=>(
                    <Paper elevation={6} style={{margin:"10px", padding:"15px", textAlign:"left"}} key={team.id}>
                        Id:{team.id}<br/>
                        name:{team.teamName}<br/>
                        {/* Capacity:{arena.capacity}<br/> */}
                </Paper>
                ))}
            </div>
        )
    }

    const showArena = () => {
        return(
            <div>
                <Paper elevation={6} style={{margin:"10px", padding:"15px", textAlign:"left"}} key={match.arenaDTO.id}>
                    Id:{match.arenaDTO.id}<br/>
                    City:{match.arenaDTO.city}<br/>
                    Capacity:{match.arenaDTO.capacity}<br/>
                </Paper>
            </div>
        )
    }

    return (
        <Paper elevation={3} style={paperStyle}>
            <h1>FIND MATCH BY ID:</h1>
            {match && (
                <Paper elevation={6} style={{ margin: "50px", padding: "15px", textAlign: "left" }} key={match.id}>
                    <p><b>Id:</b> {match.id}</p>
                    <p><b>Date and Time:</b> { match.dateTime ? moment(match.dateTime).format('DD-MM-YYYY HH:mm') : "undefined"}</p>
                    <p><b>Teams:</b> {match.teamDTOList && match.teamDTOList.length > 0 ? showTeamList() : "undefined"}</p>
                    <p><b>Arena:</b> {match.arenaDTO ? showArena() : "undefined"}</p>
                </Paper>
            )}
            <TextField id="outlined-basic" label="Enter the match ID" variant="outlined"
                value={matchId}
                onChange={(e) => setMatchId(e.target.value)}
            />
            <br />
            <Button variant="contained" style={{ margin: "20px" }} onClick={() => findMatchById(matchId)}>
                FIND
            </Button>
        </Paper>
    );
}


export function GetAllMatches(){
    const paperStyle = {padding:"5px 20px", width:600, margin:"20px auto"}
    const [matches,setMatches] = React.useState([])
    
    const refreshListMatches = () => {
        fetch(`${apiUrl}/api/v1/match`, {
            method: "GET"
        })
        .then(res => res.json())
        .then(result => {
            setMatches(result);
        })
        .catch(error => {
            console.error('Error refreshing list of arenas:', error);
        });
    };


    const showTeamList = (match) => {
        return(
            <div>
                {match.teamDTOList.map(team=>(
                    <Paper elevation={6} style={{margin:"10px", padding:"15px", textAlign:"left"}} key={team.id}>
                        Id:{team.id}<br/>
                        name:{team.teamName}<br/>
                        {/* Capacity:{arena.capacity}<br/> */}
                </Paper>
                ))}
            </div>
        )
    }

    const showArena = (match) => {
        return(
            <div>
                <Paper elevation={6} style={{margin:"10px", padding:"15px", textAlign:"left"}} key={match.arenaDTO.id}>
                    Id:{match.arenaDTO.id}<br/>
                    City:{match.arenaDTO.city}<br/>
                    Capacity:{match.arenaDTO.capacity}<br/>
                </Paper>
            </div>
        )
    }

    React.useEffect(() => {
        refreshListMatches();
    }, []);

    return (
        <Paper elevation={3} style={paperStyle}>
            <h1>LIST ALL MATCHES:</h1>
      {matches.map(match=>(
            <Paper elevation={6} style={{ margin: "50px", padding: "15px", textAlign: "left" }} key={match.id}>
                <p><b>Id:</b> {match.id}</p>
                <p><b>Date and Time:</b> { match.dateTime ? moment(match.dateTime).format('DD-MM-YYYY HH:mm') : "undefined"}</p>
                <p><b>Teams:</b> {match.teamDTOList && match.teamDTOList.length > 0 ? showTeamList(match) : "undefined"}</p>
                <p><b>Arena:</b> {match.arenaDTO ? showArena(match) : "undefined"}</p>
            </Paper>
      ))}
      <Button variant="outlined" color="primary" style={{margin:"20px"}} onClick={refreshListMatches}>
              Refresh
            </Button>
    </Paper>
    )
}


export function DeleteMatch(){
    const paperStyle = {padding:"5px 20px", width:600, margin:"20px auto"}
    const [matchId,setMatchId] = React.useState([])

    const handleDeleteMatch = (id) => {
        console.log(id);
        fetch(`${apiUrl}/api/v1/match/delete?matchId=${id}`, {
            method: "DELETE"
        })
        .then(response => {
            if (response.ok) {
                console.log("Match deleted successfully");
            } else {
                console.error("Failed to delete match");
            }
        })
        .catch(error => {
            console.error("Error deleting match:", error);
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
            <h1>DELETE MATCH BY ID</h1>
            <TextField id="outlined-basic" label="Enter the match ID" variant="outlined" 
            value={matchId}
            onChange={(e)=>setMatchId(e.target.value)}
            />
            <br />
            <Button variant="contained" onClick={() => handleDeleteMatch(matchId)}>
                Delete 
            </Button>
        </Box>
        </Paper>
        </Container>
      );
}


export function SetNewArenaForMatch() {
    const paperStyle = { padding: "5px 20px", width: 600, margin: "20px auto" };
    const [arenaId, setArenaId] = React.useState("");
    const [matchId, setMatchId] = React.useState("");
    const [match, setMatch] = React.useState("");

    const setNewArena = () => {

        fetch(`${apiUrl}api/v1/match/setArena?matchId=${matchId}&newArenaId=${arenaId}`, {
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
            setMatch(result);
        })
        .catch(error => {
            setMatch(null);
        });
    };

    const showTeamList = (match) => {
        return(
            <div>
                {match.teamDTOList.map(team=>(
                    <Paper elevation={6} style={{margin:"10px", padding:"15px", textAlign:"left"}} key={team.id}>
                        Id:{team.id}<br/>
                        name:{team.teamName}<br/>
                    </Paper>
                ))}
            </div>
        )
    }

    const showArena = (match) => {
        return(
            <div>
                <Paper elevation={6} style={{margin:"10px", padding:"15px", textAlign:"left"}} key={match.arenaDTO.id}>
                    Id:{match.arenaDTO.id}<br/>
                    City:{match.arenaDTO.city}<br/>
                    Capacity:{match.arenaDTO.capacity}<br/>
                </Paper>
            </div>
        )
    }


    return (
        <Paper elevation={3} style={paperStyle}>
            <h1>Set arena by ID:</h1>
            {match && (
                <Paper elevation={6} style={{ margin: "50px", padding: "15px", textAlign: "left" }} key={match.id}>
                <p><b>Id:</b> {match.id}</p>
                <p><b>Date and Time:</b> { match.dateTime ? moment(match.dateTime).format('DD-MM-YYYY HH:mm') : "undefined"}</p>
                <p><b>Teams:</b> {match.teamDTOList && match.teamDTOList.length > 0 ? showTeamList(match) : "undefined"}</p>
                <p><b>Arena:</b> {match.arenaDTO ? showArena(match) : "undefined"}</p>
                </Paper>
            )}
                <TextField id="outlined-basic" label="Enter match ID(required)" variant="outlined" style={{marginBottom:'10px'}}
                value={matchId}
                onChange={(e) => setMatchId(e.target.value)}
            />
            <br />
            <TextField id="outlined-basic" label="Enter new arena ID" variant="outlined" style={{}}
                value={arenaId}
                onChange={(e) => setArenaId(e.target.value)}
            />
            <br />
            <Button variant="contained" style={{ margin: "20px" }} onClick={() => setNewArena()}>
                SAVE
            </Button>
        </Paper>
    );
}
