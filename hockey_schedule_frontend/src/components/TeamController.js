import * as React from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { Container, Paper, Button } from '@mui/material';
import moment from 'moment';

const apiUrl = "https://hockeyschedule.onrender.com";

export function AddTeam() {
    const paperStyle = {padding:"20px 20px", width:600, margin:"20px auto"}
    const [teamName,setTeamName] = React.useState()

    const handleClick = (e) => {
        e.preventDefault()
        const newTeam = {teamName}
        console.log('New team:', newTeam);

        fetch(`${apiUrl}/api/v1/team/create`,{
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(newTeam)
        })
        .then(response => response.json())
        .then(data => {
            console.log('Response from server:', data);
            console.log('New team added successfully');
        })
        .catch(error => {
            console.error('Error while adding new team:', error);
        });
    }
    
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
                <TextField id="outlined-basic" label="Team name" variant="outlined"  
                    value={teamName}
                    onChange={(e)=>setTeamName(e.target.value)}
                />
                <br />
                <Button variant="contained" onClick={handleClick}>
                    SAVE
                </Button>
            </Box>
        </Paper>
    </Container>
  );
}

export function GetTeamById() {
    const paperStyle = { padding: "5px 20px", width: 600, margin: "20px auto" };
    const [teamId, setTeamId] = React.useState("");
    const [team, setTeam] = React.useState(null);

    const findTeamById = (id) => {
        fetch((`${apiUrl}/api/v1/team/` + id), {
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
            setTeam(result);
        })
        .catch(error => {
            setTeam(null);
        });
    };

    const showMatchList = (team) => {
        return(
            <div>
                {team.matchDTOWithArenaList.map(match=>(
            <Paper elevation={6} style={{ margin: "10px", padding: "15px", textAlign: "left" }} key={match.id}>
                <p><b>Id:</b> {match.id}</p>
                <p><b>Date and Time:</b> { match.dateTime ? moment(match.dateTime).format('DD-MM-YYYY HH:mm') : "undefined"}</p>
            </Paper>
            ))}
           </div>
        )
    }

    return (
        <Paper elevation={3} style={paperStyle}>
            <h1>FIND TEAM BY ID:</h1>
            {team && (
                <Paper elevation={6} style={{margin:"10px", padding:"15px", textAlign:"left"}} key={team.id}>
                Id:{team.id}<br/>
                Team name:{team.teamName}<br/>
                Match: {team.matchDTOWithArenaList && team.matchDTOWithArenaList.length > 0 ? showMatchList(team) : "undefined"}<br/>
            </Paper>
            )}
            <TextField id="outlined-basic" label="Enter the team ID" variant="outlined"
                value={teamId}
                onChange={(e) => setTeamId(e.target.value)}
            />
            <br />
            <Button variant="contained" style={{ margin: "20px" }} onClick={() => findTeamById(teamId)}>
                FIND
            </Button>
        </Paper>
    );
}

export function DeleteTeam(){
    const paperStyle = {padding:"5px 20px", width:600, margin:"20px auto"}
    const [teamId,setTeamId] = React.useState([])

    const handleDeleteTeam = (id) => {
        fetch(`${apiUrl}/api/v1/team/delete?teamId=${id}`, {
            method: "DELETE"
        })
        .then(response => {
            if (response.ok) {
                console.log("Team deleted successfully");
            } else {
                console.error("Failed to delete team");
            }
        })
        .catch(error => {
            console.error("Error deleting team:", error);
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
            <h1>DELETE TEAM BY ID</h1>
            <TextField id="outlined-basic" label="Enter the team ID" variant="outlined" 
            value={teamId}
            onChange={(e)=>setTeamId(e.target.value)}
            />
            <br />
            <Button variant="contained" onClick={() => handleDeleteTeam(teamId)}>
                Delete 
            </Button>
        </Box>
        </Paper>
        </Container>
      );
}


export function GetAllTeams(){
    const paperStyle = {padding:"5px 20px", width:600, margin:"20px auto"}
    const [teams,setTeams] = React.useState([])
    
    const refreshListTeams = () => {
        fetch(`${apiUrl}/api/v1/team`, {
            method: "GET"
        })
        .then(res => res.json())
        .then(result => {
            setTeams(result);
        })
        .catch(error => {
            console.error('Error refreshing list of teams:', error);
        });
    };

    const showMatchList = (team) => {
        return(
            <div>
                {team.matchDTOWithArenaList.map(match=>(
            <Paper elevation={6} style={{ margin: "10px", padding: "15px", textAlign: "left" }} key={match.id}>
                <p><b>Id:</b> {match.id}</p>
                <p><b>Date and Time:</b> { match.dateTime ? moment(match.dateTime).format('DD-MM-YYYY HH:mm') : "undefined"}</p>
            </Paper>
            ))}
           </div>
        )
    }

    React.useEffect(() => {
        refreshListTeams();
    }, []);

    return (
        <Paper elevation={3} style={paperStyle}>
            <h1>LIST ALL TEAMS:</h1>
      {teams.map(team=>(
        <Paper elevation={6} style={{margin:"10px", padding:"15px", textAlign:"left"}} key={team.id}>
            Id:{team.id}<br/>
            Team name:{team.teamName}<br/>
            {/* Match: {team.matchDTOWithArenaList && team.matchDTOWithArenaList.length > 0 ? showMatchList(team) : "undefined"}<br/> */}
        </Paper>
      ))}
      <Button variant="outlined" color="primary" style={{margin:"20px"}} onClick={refreshListTeams}>
              Refresh
            </Button>
    </Paper>
    )
}

export function AddMatchInMatchList() {
    const paperStyle = { padding: "5px 20px", width: 600, margin: "20px auto" };
    const [teamId, setTeamId] = React.useState("");
    const [matchId, setMatchId] = React.useState("");
    const [team, setTeam] = React.useState("");

    const addMatchInMatchList = () => {

        fetch(`${apiUrl}/api/v1/team/addMatch?teamId=${teamId}&matchId=${matchId}`, {
            method: "PUT"
        })
        .then(res => {
            if (res.ok) {
                return res.json();
            } else {
                throw new Error("ERROR found");
            }
        })
        .then(result => {
            setTeam(result);
        })
        .catch(error => {
            setTeam(null);
        });
    };


    return (
        <Paper elevation={3} style={paperStyle}>
            <h1>ADD MATCH IN LIST:</h1>
            {team && (
                <Paper elevation={6} style={{ margin: "50px", padding: "15px", textAlign: "left" }} key={team.id}>
                <p><b>Id:</b> {team.id}</p>
                <p><b>Team name:</b> {team.teamName}</p>
                </Paper>
            )}
                <TextField id="outlined-basic" label="Enter team ID(required)" variant="outlined" style={{marginBottom:'10px'}}
                value={teamId}
                onChange={(e) => setTeamId(e.target.value)}
            />
            <br />
            <TextField id="outlined-basic" label="Enter match ID" variant="outlined"
                value={matchId}
                onChange={(e) => setMatchId(e.target.value)}
            />
            <br />
            <Button variant="contained" style={{ margin: "20px" }} onClick={() => addMatchInMatchList()}>
                SAVE
            </Button>
        </Paper>
    );
}

export function DeleteMatchInMatchList() {
    const paperStyle = { padding: "5px 20px", width: 600, margin: "20px auto" };
    const [teamId, setTeamId] = React.useState("");
    const [matchId, setMatchId] = React.useState("");
    const [team, setTeam] = React.useState("");

    const deleteMatchInMatchList = () => {

        fetch(`${apiUrl}/api/v1/team/delMatch?teamId=${teamId}&matchId=${matchId}`, {
            method: "PUT"
        })
        .then(res => {
            if (res.ok) {
                return res.json();
            } else {
                throw new Error("ERROR found");
            }
        })
        .then(result => {
            setTeam(result);
        })
        .catch(error => {
            setTeam(null);
        });
    };


    return (
        <Paper elevation={3} style={paperStyle}>
            <h1>DEL MATCH IN LIST:</h1>
            {team && (
                <Paper elevation={6} style={{ margin: "50px", padding: "15px", textAlign: "left" }} key={team.id}>
                <p><b>Id:</b> {team.id}</p>
                <p><b>Team name:</b> {team.teamName}</p>
                </Paper>
            )}
                <TextField id="outlined-basic" label="Enter team ID(required)" variant="outlined" style={{marginBottom:'10px'}}
                value={teamId}
                onChange={(e) => setTeamId(e.target.value)}
            />
            <br />
            <TextField id="outlined-basic" label="Enter match ID" variant="outlined" 
                value={matchId}
                onChange={(e) => setMatchId(e.target.value)}
            />
            <br />
            <Button variant="contained" style={{ margin: "20px" }} onClick={() => deleteMatchInMatchList()}>
                SAVE
            </Button>
        </Paper>
    );
}


