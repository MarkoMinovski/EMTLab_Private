import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';

import MenuIcon from '@mui/icons-material/Menu';
import AddIcon from '@mui/icons-material/Add';
import SearchIcon from '@mui/icons-material/Search';
import MoreIcon from '@mui/icons-material/MoreVert';
import Typography from "@mui/material/Typography";

const Footer = () => {
    return (
        <Box sx={{ flexGrow: 1 }} color="secondary" >
            <AppBar position="fixed" sx={{ top: 'auto', bottom: 0 }}>
                <Toolbar>
                    <Typography variant="h6">
                        MyLibrary© 2025
                    </Typography>

                    {/* Spacer that pushes the next Typography to the right */}
                    <Box sx={{ flexGrow: 1 }} />

                    <Typography variant="h6">
                        Made with React + Spring 🙂
                    </Typography>
                </Toolbar>
            </AppBar>
        </Box>
    )
}

export default Footer;