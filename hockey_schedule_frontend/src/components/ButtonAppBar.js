// import * as React from 'react';
// import AppBar from '@mui/material/AppBar';
// import Box from '@mui/material/Box';
// import Toolbar from '@mui/material/Toolbar';
// import Typography from '@mui/material/Typography';
// import IconButton from '@mui/material/IconButton';
// import MenuIcon from '@mui/icons-material/Menu';
// import { Link } from 'react-router-dom'; // Импорт Link из React Router

// export default function ButtonAppBar() {
//   return (
//     <Box sx={{ flexGrow: 1 }}>
//       <AppBar position="static">
//         <Toolbar>
//           {/* Ссылка на Match Arena Team */}
//           <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
//             <Link to="/match-arena-team" style={{ textDecoration: 'none', color: 'inherit' }}>Match Arena Team</Link>
//           </Typography>
//         </Toolbar>
//       </AppBar>
//     </Box>
//   );
// }

import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
// import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';

export default function ButtonAppBar() {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          {// ссылка на Match Arena Team
}
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          Controllers
          </Typography>
        </Toolbar>
      </AppBar>
    </Box>
  );
}
