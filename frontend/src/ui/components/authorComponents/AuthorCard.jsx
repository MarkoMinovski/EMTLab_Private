import React, {useState} from 'react';
import InfoIcon from '@mui/icons-material/Info';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import {Box, Button, Card, CardActions, CardContent, Typography} from "@mui/material";
import {useNavigate} from "react-router";
import EditAuthorDialog from "./EditAuthorDialog.jsx";
import DeleteAuthorDialog from "./DeleteAuthorDialog.jsx";

const AuthorCard = ({author, onEdit, onDelete}) => {
    const navigate = useNavigate();
    const [EditAuthorDialogOpen, setEditAuthorDialogOpen] = useState(false);
    const [deleteAuthorDialogOpen, setDeleteAuthorDialogOpen] = useState(false);

    return (
        <>
            <Card sx={{boxShadow: 3, borderRadius: 2, p: 1}}>
                <CardContent>
                    <Typography variant="h5">{author.name} {author.surname}</Typography>
                    <Typography variant="subtitle2">
                        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab aperiam assumenda blanditiis cum
                        ducimus enim modi natus odit quibusdam veritatis.
                    </Typography>
                </CardContent>
                <CardActions sx={{justifyContent: "space-between"}}>
                    <Button size="small" color="info" startIcon={<InfoIcon/>}
                            onClick={() => navigate(`/authors/${author.id}`)}>
                        Info
                    </Button>
                    <Box>
                        <Button
                            size="small"
                            color="warning"
                            startIcon={<EditIcon/>}
                            sx={{mr: "0.25rem"}}
                            onClick={() => setEditAuthorDialogOpen(true)}
                        >
                            Edit
                        </Button>
                        <Button
                            size="small"
                            color="error"
                            startIcon={<DeleteIcon/>}
                            onClick={() => setDeleteAuthorDialogOpen(true)}
                        >
                            Delete
                        </Button>
                    </Box>
                </CardActions>
            </Card>
            <EditAuthorDialog
                open={EditAuthorDialogOpen}
                onClose={() => setEditAuthorDialogOpen(false)}
                author={author}
                onEdit={onEdit}
            />
            <DeleteAuthorDialog
                open={deleteAuthorDialogOpen}
                onClose={() => setDeleteAuthorDialogOpen(false)}
                author={author}
                onDelete={onDelete}
            />
        </>
    );
}

export default AuthorCard;