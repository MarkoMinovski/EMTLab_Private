// NavBar.jsx
import React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Link from '@mui/material/Link';

const NavBar = () => {
    return (
        <Box sx={{ flexGrow: 1, backgroundColor: "#2C2F5B" }}>
            <AppBar>
                <Toolbar>

                    {/* Title */}
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        <Link href="#" color="inherit" underline="none">EMTLabs - MyLibrary</Link>
                    </Typography>

                    {/* Navigation Buttons */}
                    <Button color="inherit">
                        <Link href="#" color="inherit" underline="none">Countries</Link>
                    </Button>
                    <Button color="inherit">
                        <Link href="#" color="inherit" underline="none">Authors</Link>
                    </Button>
                    <Button color="inherit">
                        <Link href="#" color="inherit" underline="none">Books</Link>
                    </Button>

                </Toolbar>
            </AppBar>
        </Box>
    );
};

export default NavBar;
