import axiosInstance from "../axios/axios.js";
import authenticate from "../axios/authenticateScaffold.js"

const countryRepository = {
    findAll: async () => {
        await authenticate();
        const resp = axiosInstance.get("/country");
        console.log(resp);
        return resp;
    },
    findById: async (id) => {
        return await axiosInstance.get(`/country/${id}`);
    },
    add: async (data) => {
        await authenticate();
        return await axiosInstance.put("/country/add", data);
    },
    edit: async (id, data) => {
        await authenticate();
        return await axiosInstance.post(`/country/update/${id}`, data);
    },
    delete: async (id) => {
        await authenticate();
        return await axiosInstance.delete(`/country/delete/${id}`);
    },

};

export default countryRepository;
