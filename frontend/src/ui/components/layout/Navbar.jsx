// NavBar.jsx
import React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Link from '@mui/material/Link';
import {Link as ReactRouterLink} from 'react-router-dom';

const NavBar = () => {
    return (
        <Box sx={{ flexGrow: 1, backgroundColor: "#2C2F5B" }}>
            <AppBar>
                <Toolbar>

                    {/* Title */}
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        <ReactRouterLink to="/" style={{ textDecoration: 'none', color: "#FFFFFF" }}>
                            MyLibrary
                        </ReactRouterLink>
                    </Typography>

                    {/* Navigation Buttons */}
                    <Button>
                        <ReactRouterLink to="/countries" style={{ textDecoration: 'none', color: "#FFFFFF" }}>
                            Countries
                        </ReactRouterLink>
                    </Button>
                    <Button>
                        <ReactRouterLink to="/authors" style={{ textDecoration: 'none', color: "#FFFFFF" }}>
                            Authors
                        </ReactRouterLink>
                    </Button>
                    <Button>
                        <ReactRouterLink to="/books" style={{ textDecoration: 'none', color: "#FFFFFF" }}>
                            Books
                        </ReactRouterLink>
                    </Button>

                </Toolbar>
            </AppBar>
        </Box>
    );
};

export default NavBar;
