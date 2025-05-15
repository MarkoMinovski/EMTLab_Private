import {useEffect, useState} from "react";
import bookRepository from '../repositories/bookRepository.js'
import authorRepository from "../repositories/authorRepository.js"
import countryRepository from "../repositories/countryRepository.js"

const UseBookDetails = (id) => {
    const [state, setState] = useState({
        "book": null,
        "author": null,
        "country": null
    })

    useEffect(() => {
        bookRepository.findById(id).then((bookResp) => {
            setState(prevState => ({...prevState, "book": bookResp.data}));
            authorRepository.findById(bookResp.data.author).then((authorResp) => {
                setState(prevState => ({...prevState, "author": authorResp.data}));
                countryRepository.findById(authorResp.data.country).then((countryResp) => {
                    setState(prevState => ({...prevState, "country": countryResp.data}));
                })
            })
        })
    }, [id])

    return state;
}

export default UseBookDetails;