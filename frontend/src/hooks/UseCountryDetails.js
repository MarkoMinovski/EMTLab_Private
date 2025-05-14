import {useEffect, useState} from "react";
import countryRepository from "../repositories/countryRepository.js";

const useCountryDetails = (id) => {
    const [state, setState] = useState({
        "name": null,
        "continent": null
    });

    useEffect(() => {
        countryRepository
            .findById(id).then((resp) => {
                console.log(resp);
                setState(prevState => ({...prevState, "name": resp.data.name,
                    "continent": resp.data.continent}));
        })
    }, [id])

    return state
}

export default useCountryDetails;