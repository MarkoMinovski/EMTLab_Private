import Box from "@mui/material/Box";
import {CircularProgress} from "@mui/material";
import AuthorGrid from "../components/authorComponents/AuthorGrid.jsx";
import {useState} from "react";
import Button from "@mui/material/Button";
import useAuthors from "../../hooks/useAuthors.js";
import AddAuthorDialog from "../components/authorComponents/AddAuthorDialog.jsx";

const AuthorPage = () => {
    const {authors, loading, onAdd, onEdit, onDelete} = useAuthors();
    const [addAuthorDialogOpen, setAddAuthorDialogOpen] = useState(false);
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
                                setAddAuthorDialogOpen(true)}>
                                Add Author
                            </Button>
                        </Box>
                        <AuthorGrid authors={authors} onEdit={onEdit} onDelete={onDelete}/>
                    </>}
            </Box>
            <AddAuthorDialog
                open={addAuthorDialogOpen}
                onClose={() => setAddAuthorDialogOpen(false)}
                onAdd={onAdd}
            />
        </>
    );
}

export default AuthorPage;