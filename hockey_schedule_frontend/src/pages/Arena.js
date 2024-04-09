import * as React from 'react';
import Box from '@mui/material/Box';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { Link } from 'react-router-dom';
import { AddArena, DeleteArena, GetAllArenas, GetArenaByCapacity, GetArenaById, UpdateArena } from '../components/ArenaController';

export function Arena () {
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
                ArenaController
            </Typography>
            <Link style={{ textAlign:'right', color: 'white', textDecoration: 'none', padding: '5px 5px', fontWeight: 'bold' }} to="/">
                    Home
                </Link>
            </Toolbar>
        </AppBar>
        <AddArena />
        <DeleteArena />
        <GetArenaById />
        <UpdateArena />
        <GetArenaByCapacity />
        <GetAllArenas />
    </Box>
    </div>
    );
} 