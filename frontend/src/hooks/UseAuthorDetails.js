import {useEffect, useState} from "react";
import authorRepository from "../repositories/authorRepository.js";
import countryRepository from "../repositories/countryRepository.js";


const useAuthorDetails = (id) => {
    const [state, setState] = useState({
        "author": null,
        "country": null,
    });

    useEffect(() => {
        authorRepository.findById(id).then((authorResp) => {
            console.log(authorResp);
            setState(prevState => ({...prevState, "author": authorResp.data}));
            countryRepository.findById(authorResp.data.country).then((countryResp) => {
                console.log(countryResp);
                setState(prevState => ({...prevState, "country": countryResp.data}));
            })
        })
    }, [id])

    return state;
}

export default useAuthorDetails;