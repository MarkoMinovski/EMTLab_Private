import React, {useState} from 'react';
import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle, FormControl, InputLabel, MenuItem, Select,
    TextField
} from "@mui/material";
import useAuthors from "../../../hooks/useAuthors.js";
import useCategories from "../../../hooks/UseCategories.js";

const initialFormData = {
    "name": "",
    "category": "",
    "author": null,
    "availableCopies": 0,
};


const AddBookDialog = ({open, onClose, onAdd}) => {
    const [formData, setFormData] = useState(initialFormData);
    const {authors} = useAuthors();
    const categories = useCategories();

    const handleChange = (event) => {
        const {name, value} = event.target;
        setFormData({...formData, [name]: value});
    };

    const handleSubmit = () => {
        console.log(formData);
        onAdd(formData);
        setFormData(initialFormData);
        onClose();
    };

    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle>Add Book</DialogTitle>
            <DialogContent>
                <TextField
                    margin="dense"
                    label="Name"
                    name="name"
                    value={formData.name}
                    onChange={handleChange}
                    fullWidth
                />

                <TextField
                    margin="dense"
                    label="Available Copies"
                    name="availableCopies"
                    type="number"
                    value={formData.availableCopies}
                    onChange={handleChange}
                    fullWidth
                />
                <FormControl fullWidth margin="dense">
                    <InputLabel>Category</InputLabel>
                    <Select
                        name="category"
                        value={formData.category}
                        onChange={handleChange}
                        label="Category"
                        variant="outlined">
                        {categories.map((category) => (
                            <MenuItem key={category.id} value={category}>{category}</MenuItem>
                        ))}
                    </Select>
                </FormControl>

                <FormControl fullWidth margin="dense">
                    <InputLabel>Author</InputLabel>
                    <Select
                        name="author"
                        value={formData.author}
                        onChange={handleChange}
                        label="Author"
                        variant="outlined">
                        {authors.map((author) => (
                            <MenuItem key={author.id} value={author.id}>{author.name} {author.surname}</MenuItem>
                        ))}
                    </Select>
                </FormControl>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancel</Button>
                <Button onClick={handleSubmit} variant="contained" color="primary">Add</Button>
            </DialogActions>
        </Dialog>
    );
}

export default AddBookDialog;