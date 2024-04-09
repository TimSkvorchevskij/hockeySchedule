import * as React from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { Link } from 'react-router-dom';
import { AddMatch, DeleteMatch, GetAllMatches, GetMatchById, SetNewArenaForMatch } from '../components/MatchController';
import { Container } from '@mui/material';

export function Match () {
    const linkStyle = {
        marginRight: '20px', // Отступ справа между ссылками
        color: 'white', // Цвет текста
        textDecoration: 'none', // Убираем подчеркивание
        padding: '5px 10px', // Отступы вокруг текста
        fontWeight: 'bold', // Более жирный текст   
      };
    return(
    <div className='App'>
        <Box sx={{ flexGrow: 1}}>
        <AppBar position="static">
            <Toolbar >
                <Link style={linkStyle} to="/match">Match</Link>
                <Link style={linkStyle} to="/arena">Arena</Link>
                <Link style={linkStyle} to="/team">Team</Link>
            <Typography variant="h6" component="div" sx={{ flexGrow: 1, textAlign: 'center', marginRight: '200px' }}>
                MatchController
            </Typography>
            <Link style={{ textAlign:'right', color: 'white', textDecoration: 'none', padding: '5px 5px', fontWeight: 'bold' }} to="/">
                    Home
                </Link>
            </Toolbar>
        </AppBar>
        <AddMatch />
        <DeleteMatch />
        <SetNewArenaForMatch />
        <GetMatchById />

        <GetAllMatches />
    </Box>
    </div>
    );
} 