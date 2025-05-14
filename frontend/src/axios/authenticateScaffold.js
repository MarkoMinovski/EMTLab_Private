import axiosInstance from "./axios.js";

const librarianInfo = {
    "username": "admin",
    "password": "abcd"
};

const authenticate = async () => {
    try {
        const response = await axiosInstance.post("/users/login", librarianInfo);
        const token = response.data.token;
        axiosInstance.defaults.headers.common['Authorization'] = 'Bearer ' + token;
        console.log("Token set:", token);
    } catch (error) {
        console.error("Login failed:", error);
    }
};


export default authenticate;