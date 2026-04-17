import axios from "axios"
import {getToken, removeToken} from "./utils/token-auth";

const instance = axios.create({
		baseURL: "http://localhost:8080/",
		timeout: 5000
	}
)
instance.interceptors.request.use(
	(config) => {
		config.headers.set("Accept", "application/json,text/plain,text/html");
		const token = getToken();
		if(token){
			config.headers.set("Authorization",`Bearer ${token}`);
		}
		return config;
	}, (error) => {
		return Promise.reject(error);
	}
)

instance.interceptors.response.use(
	(response) => {
		return response;
	}, (error) => {
		const code = error?.response?.data?.code;
		if(code === 506){
			removeToken();
			window.location.href = "/login";
		}
		return Promise.reject(error);
	}
)

export default instance;