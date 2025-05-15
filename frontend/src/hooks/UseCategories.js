import {useEffect, useState} from "react";
import bookRepository from "../repositories/bookRepository.js";

const useCategories = () => {
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        bookRepository
            .getCategories()
            .then((response) => {
                setCategories(response.data);
            })
            .catch((error) => console.log(error));
    }, []);

    return categories;
};

export default useCategories;
