import './App.css';
import {
  BrowserRouter,
  Routes,
  Route,
} from 'react-router-dom';
import { AddArena, DeleteArena, GetAllArenas, GetArenaByCapacity, GetArenaById, UpdateArena } from './components/ArenaController';
import AppBarButton from './components/ButtonAppBar';
import { AddMatch, DeleteMatch, GetAllMatches, GetMatchById, SetNewArenaForMatch } from './components/MatchController';
import { AddMatchInMatchList, AddTeam, DeleteMatchInMatchList, DeleteTeam, GetAllTeams, GetTeamById } from './components/TeamController';
import { Match } from './pages/Match';
import { MainPage } from './pages/MainPage';



export default function App() {
  return (
    <BrowserRouter>
      <Routes>
          <Route path="/" element={<MainPage />}/>
          <Route path="match" element={<Match />}/>
          <Route path="one" element={<DeleteArena />} />
          <Route path="two" element={<AddMatchInMatchList />} />
      </Routes>
    </BrowserRouter>
  );
    // <div className='App'>
    //   <AppBarButton />
    //   <AddTeam />
    //   <GetTeamById />
    //   <DeleteTeam />
    //   <GetAllTeams />
    //   <AddMatchInMatchList />
    //   <DeleteMatchInMatchList />

    //   <AddMatch />
      {/* <GetMatchById /> */}
      {/* <GetAllMatches /> */}
      {/* <DeleteMatch /> */}
      {/* <SetNewArenaForMatch /> */}

      {/* <UpdateArena /> */}
      {/*<GetArenaById />
      <GetArenaByCapacity />
      
      <DeleteArena />
  <GetAllArenas />*/}
      
    // </div>
  // );
}
