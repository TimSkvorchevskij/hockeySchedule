import * as React from 'react';
import Box from '@mui/material/Box';
import { Link } from 'react-router-dom';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';

export function MainPage () {
    const linkStyle = {
        marginRight: '20px', // Отступ справа между ссылками
        color: 'white', // Цвет текста
        textDecoration: 'none', // Убираем подчеркивание
        padding: '5px 10px', // Отступы вокруг текста
        fontWeight: 'bold', // Более жирный текст   
      };
    return(
    <div>
        <Box sx={{ flexGrow: 1}}>
        <AppBar position="static">
            <Toolbar >
                <Link style={linkStyle} to="/match">Match</Link>
                <Link style={linkStyle} to="/arena">Arena</Link>
                <Link style={linkStyle} to="/team">Team</Link>
            <Typography variant="h6" component="div" sx={{ flexGrow: 1, textAlign: 'center', marginRight: '200px'}}>
                MainPage
            </Typography>
                <Link style={{ textAlign:'right', color: 'white', textDecoration: 'none', padding: '5px 5px', fontWeight: 'bold' }} to="/">
                    Home
                </Link> 
            </Toolbar>
        </AppBar>
        </Box>
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '20vh' }}>
        <div style={{textAlign:'center'}}>
          <h1>HOCKEY SCHEDULE<br/></h1>
          {/* <h1>VOROPAEV</h1> */}
        </div>
    </div>
    </div>
    );
} 

// import * as React from 'react';
// import Box from '@mui/material/Box';
// import TextField from '@mui/material/TextField';
// import { AddMatch, DeleteMatch, GetAllMatches, GetMatchById, SetNewArenaForMatch } from '../components/MatchController';
// import ButtonAppBar from '../components/ButtonAppBar';
// import { Link } from 'react-router-dom';
// import AppBar from '@mui/material/AppBar';
// import Toolbar from '@mui/material/Toolbar';
// import Typography from '@mui/material/Typography';

// export function MainPage () {
//     const linkStyle = {
//         marginRight: '20px', // Отступ справа между ссылками
//         color: 'white', // Цвет текста
//         textDecoration: 'none', // Убираем подчеркивание
//         padding: '5px 10px', // Отступы вокруг текста
//         fontWeight: 'bold', // Более жирный текст   
//       };
//     return(
//     <div>
//         <Box sx={{ flexGrow: 1}}>
//       <AppBar position="static">
//             <Toolbar>
//                 <Link style={linkStyle} to="/match">Match</Link>
//                 <Link style={linkStyle} to="/arena">Arena</Link>
//                 <Link style={linkStyle} to="/team">Team</Link>
//             <Typography variant="h6" component="div" sx={{ flexGrow: 1, textAlign: 'center' }}>
//                 MainPage
//             </Typography>
//             </Toolbar>
//         </AppBar>
//         </Box>
//     <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '20vh' }}>
//         <div>
//           <h1>HOCKEY SCHEDULE</h1>
//         </div>
//     </div>
//     </div>
//     );
// } 