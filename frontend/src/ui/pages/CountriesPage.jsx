import useCountries from "../../hooks/useCountries.js";
import Box from "@mui/material/Box";
import {CircularProgress} from "@mui/material";
import CountryGrid from "../components/countryComponents/CountryGrid.jsx";
import {useState} from "react";
import Button from "@mui/material/Button";
import AddCountryDialog from "../components/countryComponents/AddCountryDialog.jsx";


const CountriesPage = () => {
    const {countries, loading, onAdd, onEdit, onDelete} = useCountries();
    console.log(countries);
    const [addCountryDialog, setAddCountryDialog] = useState(false);
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
                            <Button variant="contained" color="primary" onClick={() => setAddCountryDialog(true)}>
                                Add Country
                            </Button>
                        </Box>
                        <CountryGrid countries={countries} onEdit={onEdit} onDelete={onDelete}/>
                    </>}
            </Box>
            <AddCountryDialog
                open={addCountryDialog}
                onClose={() => setAddCountryDialog(false)}
                onAdd={onAdd}
            />
        </>
    );
}

export default CountriesPage;
