import axiosInstance from "../axios/axios.js"
import authenticate from "../axios/authenticateScaffold.js";

const bookRepository = {
    findAll: async () => {
        await authenticate();
        return await axiosInstance.get("/books");
    },
    findById: async (id) => {
        const resp = await axiosInstance.get(`/books/${id}`);
        console.log(resp);

        return resp;
    },
    add: async (data) => {
        return await axiosInstance.put("/books", data);
    },
    update: async (id, data) => {
        return await axiosInstance.post(`/books/update/${id}`, data);
    },
    delete: async (id) => {
        return await axiosInstance.delete(`/books/delete/${id}`);
    },

    getCategories: async () => {
        const response =  await axiosInstance.get("/categories");
        console.log(response);
        return response;
    }
}

export default bookRepository;