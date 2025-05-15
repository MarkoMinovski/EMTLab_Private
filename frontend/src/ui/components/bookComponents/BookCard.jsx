import React, {useState} from 'react';
import InfoIcon from '@mui/icons-material/Info';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import {Box, Button, Card, CardActions, CardContent, Typography} from "@mui/material";
import {useNavigate} from "react-router";
import EditBookDialog from "./EditBookDialog.jsx";
import DeleteBookDialog from "./DeleteBookDialog.jsx";


const BookCard = ({book, onEdit, onDelete}) => {
    const navigate = useNavigate();
    const [EditBookDialogOpen, setEditBookDialogOpen] = useState(false);
    const [DeleteBookDialogOpen, setDeleteBookDialogOpen] = useState(false);

    return (
        <>
            <Card sx={{boxShadow: 3, borderRadius: 2, p: 1}}>
                <CardContent>
                    <Typography variant="h5">{book.name}</Typography>
                    <Typography variant="h6">{book.category}</Typography>
                    <Typography variant="subtitle2">
                        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab aperiam assumenda blanditiis cum
                        ducimus enim modi natus odit quibusdam veritatis.
                    </Typography>
                </CardContent>
                <CardActions sx={{justifyContent: "space-between"}}>
                    <Button size="small" color="info" startIcon={<InfoIcon/>}
                            onClick={() => navigate(`/books/${book.id}`)}>
                        Info
                    </Button>
                    <Box>
                        <Button
                            size="small"
                            color="warning"
                            startIcon={<EditIcon/>}
                            sx={{mr: "0.25rem"}}
                            onClick={() => setEditBookDialogOpen(true)}
                        >
                            Edit
                        </Button>
                        <Button
                            size="small"
                            color="error"
                            startIcon={<DeleteIcon/>}
                            onClick={() => setDeleteBookDialogOpen(true)}
                        >
                            Delete
                        </Button>
                    </Box>
                </CardActions>
            </Card>
            <EditBookDialog
                open={EditBookDialogOpen}
                onClose={() => setEditBookDialogOpen(false)}
                book={book}
                onEdit={onEdit}
            />
            <DeleteBookDialog
                open={DeleteBookDialogOpen}
                onClose={() => setDeleteBookDialogOpen(false)}
                book={book}
                onDelete={onDelete}
            />
        </>
    );

}

export default BookCard;