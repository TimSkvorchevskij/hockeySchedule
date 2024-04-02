import './App.css';
import { AddArena, DeleteArena, GetAllArenas, GetArenaByCapacity, GetArenaById, UpdateArena } from './components/ArenaController';
import AppBarButton from './components/ButtonAppBar';
import { AddMatch, DeleteMatch, GetAllMatches, GetMatchById, SetNewArenaForMatch } from './components/MatchController';
import { AddMatchInMatchList, AddTeam, DeleteMatchInMatchList, DeleteTeam, GetAllTeams, GetTeamById } from './components/TeamController';


export default function MyApp() {
  return (
    <div className='App'>
      <AppBarButton />
      <AddTeam />
      <GetTeamById />
      <DeleteTeam />
      <GetAllTeams />
      <AddMatchInMatchList />
      <DeleteMatchInMatchList />

      <AddMatch />
      {/* <GetMatchById /> */}
      {/* <GetAllMatches /> */}
      {/* <DeleteMatch /> */}
      {/* <SetNewArenaForMatch /> */}

      {/* <UpdateArena /> */}
      {/*<GetArenaById />
      <GetArenaByCapacity />
      
      <DeleteArena />
  <GetAllArenas />*/}
      
    </div>
  );
}
