import axiosInstance from "../axios/axios.js"
import authenticate from "../axios/authenticateScaffold.js";

const authorRepository = {
    findAll: async () => {
        await authenticate();
        return await axiosInstance.get("/authors");
    },
    findById: async (id) => {

        const resp = await axiosInstance.get(`/authors/${id}`);
        console.log(resp);

        return resp;
    },
    add: async (data) => {
        return await axiosInstance.put("/authors", data);
    },
    update: async (id, data) => {
        return await axiosInstance.post(`/authors/update/${id}`, data);
    },
    delete: async (id) => {
        return await axiosInstance.delete(`/authors/delete/${id}`);
    },
}

export default authorRepository;