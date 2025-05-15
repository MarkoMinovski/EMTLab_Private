import Box from "@mui/material/Box";
import {CircularProgress} from "@mui/material";
import {useState} from "react";
import Button from "@mui/material/Button";
import useBooks from "../../hooks/useBooks.js";
import AddBookDialog from "../components/bookComponents/AddBookDialog.jsx";
import BookGrid from "../components/bookComponents/BookGrid.jsx";

const BooksPage = () => {
    const {books, loading, onAdd, onEdit, onDelete} = useBooks();
    const [addBookDialogOpen, setAddBookDialogOpen] = useState(false);

    return (
        <>
            <Box className="products-box">
                {loading && (
                    <Box className="progress-box">
                        <CircularProgress/>
                    </Box>
                )}
                {!loading &&
                    <>
                        <Box sx={{display: "flex", justifyContent: "flex-end", mb: 2}}>
                            <Button variant="contained" color="primary" onClick={() =>
                                setAddBookDialogOpen(true)}>
                                Add Book
                            </Button>
                        </Box>
                        <BookGrid books={books} onEdit={onEdit} onDelete={onDelete}/>
                    </>}
            </Box>
            <AddBookDialog
                open={addBookDialogOpen}
                onClose={() => setAddBookDialogOpen(false)}
                onAdd={onAdd}
            />
        </>
    );
}

export default BooksPage;