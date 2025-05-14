import {useCallback, useEffect, useState} from "react";
import countryRepository from "../repositories/countryRepository.js";

const initialState = {
    "countries": [],
    "loading": true,
};


const useCountries = () => {
    const [countries, setCountries] = useState(initialState);

    const fetchCountries = useCallback(() => {
        countryRepository
            .findAll()
            .then((resp) => {
                console.log(resp)
            setCountries({
                "countries": resp.data,
                "loading": false
            });
        }).catch((err) => {
            console.log(err);
        })
    }, []);

    const onAdd = useCallback((data) => {
        countryRepository
            .add(data)
            .then(() => {
                console.log("Successfully added a new product.");
                fetchCountries();
            })
            .catch((error) => console.log(error));
    }, [fetchCountries]);

    const onEdit = useCallback((id, data) => {
        countryRepository
            .edit(id, data)
            .then(() => {
                console.log(`Successfully edited the product with ID ${id}.`);
                fetchCountries();
            })
            .catch((error) => console.log(error));
    }, [fetchCountries]);

    const onDelete = useCallback((id) => {
        countryRepository
            .delete(id)
            .then(() => {
                console.log(`Successfully deleted the product with ID ${id}.`);
                fetchCountries();
            })
            .catch((error) => console.log(error));
    }, [fetchCountries]);

    useEffect(() => {
        fetchCountries();
    }, [fetchCountries]);

    return {...countries, onAdd: onAdd, onEdit: onEdit, onDelete: onDelete};
};

export default useCountries;
