import React from 'react';
import BookCard from "./BookCard.jsx";
import {Grid} from "@mui/material";

const BookGrid = ({books, onEdit, onDelete}) => {
    return (
        <Grid container spacing={{xs: 2, md: 3}}>
            {books.map((book) => (
                <Grid key={book.id} size={{xs: 12, sm: 6, md: 4, lg: 3}}>
                    <BookCard book={book} onEdit={onEdit} onDelete={onDelete}/>
                </Grid>
            ))}
        </Grid>
    );
};

export default BookGrid;