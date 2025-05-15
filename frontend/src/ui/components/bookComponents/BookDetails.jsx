import React from 'react';
import {useNavigate, useParams} from "react-router";
import {
    Box,
    Button,
    CircularProgress,
    Grid,
    Typography,
    Paper,
    Avatar,
    Breadcrumbs,
    Link, Divider
} from "@mui/material";
import {
    ArrowBack
} from "@mui/icons-material";
import UseBookDetails from "../../../hooks/UseBookDetails.js";

const BookDetails = () => {
    const navigate = useNavigate();
    const {id} = useParams();
    const {book, author, country} = UseBookDetails(id);

    console.log(book)
    console.log(author)

    if (!book || !author || !country) {
        return (
            <Box sx={{display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '60vh'}}>
                <CircularProgress/>
            </Box>
        );
    }

    return (
        <Box>
            <Breadcrumbs aria-label="breadcrumb" sx={{ mb: 3 }}>
                <Link
                    underline="hover"
                    color="inherit"
                    onClick={(e) => {
                        e.preventDefault();
                        navigate("/countries");
                    }}
                >
                    Books
                </Link>
                <Typography color="text.primary">{name}</Typography>
            </Breadcrumbs>

            <Paper elevation={3} sx={{ p: 5, borderRadius: 4 }}>
                <Grid container spacing={5}>
                    <Grid item xs={12} md={4}>
                        <Box
                            sx={{
                                display: 'flex',
                                justifyContent: 'center',
                                alignItems: 'center',
                                p: 3,
                                bgcolor: 'background.paper',
                                borderRadius: 3,
                                boxShadow: 2,
                            }}
                        >
                            <Avatar
                                src={"/placeholder-book.jpg"}
                                variant="rounded"
                                sx={{
                                    width: '100%',
                                    height: 280,
                                    objectFit: 'cover',
                                }}
                            />
                        </Box>
                    </Grid>

                    <Grid item xs={12} md={8}>
                        <Typography variant="h4" sx={{ fontWeight: 700, mb: 2 }}>
                            {book.name}
                        </Typography>

                        <Divider sx={{ mb: 3 }} />

                        <Typography variant="body1" sx={{ mb: 1 }}>
                            <strong>Author:</strong> {author.name} {author.surname}
                        </Typography>

                        <Typography variant="body1" sx={{ mb: 1 }}>
                            <strong>Country of Origin:</strong> {country.name}
                        </Typography>

                        <Typography variant="body1" sx={{ mb: 1 }}>
                            <strong>Category:</strong> {book.category}
                        </Typography>

                        <Typography variant="body1" sx={{ mb: 3 }}>
                            <strong>Available Copies:</strong> {book.availableCopies}
                        </Typography>

                        <Typography variant="body2" color="text.secondary" sx={{ mb: 4 }}>
                            Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                            Animi consequatur culpa doloribus, enim maiores possimus similique totam ut veniam voluptatibus.
                        </Typography>

                        <Button
                            variant="outlined"
                            startIcon={<ArrowBack />}
                            onClick={() => navigate("/countries")}
                        >
                            Back to Countries
                        </Button>
                    </Grid>
                </Grid>
            </Paper>
        </Box>
    );
}

export default BookDetails;