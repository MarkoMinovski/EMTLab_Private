import React from 'react';
import {Box, Container} from "@mui/material";
import {Outlet} from "react-router";
import Footer from "./layout/Footer.jsx";
import NavBar from "./layout/Navbar.jsx";

const Layout = () => {
    return (
        <Box>
            <NavBar/>
            <Container className="outlet-container" sx={{my: 2, mt: '80px'}} maxWidth="xl">
                <Outlet/>
            </Container>
            <Footer></Footer>
        </Box>
    );
};

export default Layout;