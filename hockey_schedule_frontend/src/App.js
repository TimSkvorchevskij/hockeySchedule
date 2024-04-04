import './App.css';
import {
  BrowserRouter,
  Routes,
  Route,
} from 'react-router-dom';
import { Match } from './pages/Match';
import { MainPage } from './pages/MainPage';
import { Arena } from './pages/Arena';
import { Team } from './pages/Team';



export default function App() {
  return (
    <BrowserRouter>
      <Routes>
          <Route path="/" element={<MainPage />}/>
          <Route path="match" element={<Match />}/>
          <Route path="arena" element={<Arena />} />
          <Route path="team" element={<Team />} />
      </Routes>
    </BrowserRouter>
  );
}
